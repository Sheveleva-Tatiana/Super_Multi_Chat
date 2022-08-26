package edu.school21.sockets.server;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.RoomsService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Server {
    private UsersService usersService;
    private RoomsService roomsService;
    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    private int num = 1;

    @Autowired
    public Server(UsersService usersService, RoomsService roomsService) {
        this.usersService = usersService;
        this.roomsService = roomsService;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);

            WriterThread writerThread = new WriterThread();
            writerThread.start();
            while(true) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                clients.add(client);
                System.out.println("New client connected! Number of clients: " + num++);
                client.start();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            stop();
        }
    }

    private class WriterThread extends Thread {
        Scanner scanner = new Scanner(System.in);

        @Override
        public void run() {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("exit"))
                {
                    Server.this.stop();
                }
            }
        }
    }

    private synchronized void sendMessageToRoom(String message, String roomTitles, String senderName, boolean saveMessage) {
        String messageToSend;

        if (saveMessage) {
            usersService.createMessage(message, senderName, roomTitles);
            messageToSend = "[" + LocalDateTime.now().format(FORMATTER) + "] " + senderName + ": "
                    + message;
        } else {
            messageToSend = message;
        }

        clients.stream().filter(title -> Objects.equals(title.roomTitle, roomTitles)).
                forEach(c -> c.sendMsgToClientWithRoom(messageToSend, "talking", roomTitles));
    }

    private void removeClient(Client client) {
        clients.remove(client);
        num--;
        System.out.println("The user " + client.username + " has left the chat.");
    }

    private void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                                               CLASS CLIENTS
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    private class Client extends Thread {
        private PrintWriter writer;
        private Scanner reader;
        private Socket socket;
        private String username = "guest";
        private String roomTitle;
        private String password;

        Client(Socket socket) {
            try {
                this.socket = socket;
                reader = new Scanner(socket.getInputStream());
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void run() {
            sendMsgToClient("Hello from Server!", "start");

            while (true) {
                try {
                    if (reader.hasNextLine()) {
                        String code = JSONConverter.parseToObject(reader.nextLine().trim()).getCODE();

                        if ("answer1".equalsIgnoreCase(code)) {
                            signUpUser();
                        } else if ("answer2".equalsIgnoreCase(code)) {
                            if (signInUser()) {
                                chooseOrCreateRoom();
                            }
                        } else if ("finishProgram".equalsIgnoreCase(code)) {
                            exitChat();
                            return;
                        }
                    } else {
                        exitChat();
                        return;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e);
                }
            }
        }

        private boolean getUserPass() {
            try {
                sendMsgToClient("Enter username: ", "enterUserName");
                JSONMessage messageJson = JSONConverter.parseToObject(reader.nextLine().trim());
                String temp = messageJson.getMessageJSON();
                String code = messageJson.getCODE();
                if ("back".equals(code))
                    return false;
                String[] arrTemp = temp.split(":");
                username = arrTemp[0];
                password = arrTemp[1];

                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }

        private void signUpUser() {
            if (!getUserPass()) {
                sendMsgToClient("Hello from Server!", "start");
                return;
            }
            try {
                usersService.signUp(new User(username, password));
                System.out.println("User '" + username + "' was created.");
                sendMsgToClient("User: " + username + " created!", "start");
                username = "guest";
            } catch (Exception e) {
                sendMsgToClient(e.getMessage(), "start");
            }
        }

        private boolean signInUser() {
            if (!getUserPass()) {
                sendMsgToClient("Hello from Server!", "start");
                return false;
            }
            if (usersService.signIn(username, password)) {
                sendMsgToClient("Authorization successful!", "choose command");
                System.out.println("Authorization successful for user: " + username);
                return true;
            } else {
                sendMsgToClient("Authorization failed!", "start");
                System.out.println("Authorization failed for user: " + username);
                return false;
            }
        }

        private String takeUsers(String roomTitleCurrent) {
            List<Client> users = clients.stream().filter(s -> s.roomTitle.equals(roomTitleCurrent)).collect(Collectors.toList());
            StringJoiner str = new StringJoiner("\n");
            users.forEach(s -> str.add(s.username));
            return str.toString();
        }

        private void sendMsgToClientWithRoom(String text, String code, String needRoomTitle) {
            String users = null;
            if (roomTitle != null)
                users = takeUsers(needRoomTitle);
            writer.println(Objects.requireNonNull(JSONConverter.makeJSONObject(text, users, code)).toJSONString());
        }

        private void sendMsgToClient(String text, String code) {
            writer.println(Objects.requireNonNull(JSONConverter.makeJSONObject(text, "users", code)).toJSONString());
        }

        private void chooseOrCreateRoom() {
            while(true) {
                try {
                    if (reader.hasNextLine()) {
                        String code = JSONConverter.parseToObject(reader.nextLine().trim()).getCODE();
                        if ("answer1".equalsIgnoreCase(code)) {
                            createRoom();
                        } else if ("answer2".equalsIgnoreCase(code)) {
                            chooseRoom();
                        } else if ("answer3".equalsIgnoreCase(code)) {
                            if(deleteUser())
                                return;
                        } else if ("finishProgram".equals(code)) {
                            System.out.println("User: '" + username + "' logged out");
                            sendMsgToClient("Logout for " + username, "start");
                            return;
                        }
                    } else {
                        break;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e);
                }
            }
        }

    private boolean deleteUser() {
        sendMsgToClient("Sure?", "sure");
        String code = JSONConverter.parseToObject(reader.nextLine().trim()).getCODE();

        if ("yes".equals(code)) {
            usersService.deleteUser(username);
            sendMsgToClient("Profile for user: " + username + " successfully deleted", "start");
            System.out.println("User: '" + username + "' was deleted");
            return true;
        } else {
            sendMsgToClient("Choose command: ", "choose command");
        }
        return false;
    }

    private void createRoom() {
            try {
                sendMsgToClient("Enter Title for a new room", "enterTitleRoom");
                JSONMessage messageJson = JSONConverter.parseToObject(reader.nextLine().trim());
                roomTitle = messageJson.getMessageJSON();
                String code = messageJson.getCODE();
                if ("back".equals(code)) {
                    sendMsgToClient("Choose command: ", "choose command");
                    return;
                }
                roomsService.createRoom(new Chatroom(roomTitle, username));
                System.out.println("User: '" + username + "' created a room: " + roomTitle);
                sendMsgToClient("Room '" + roomTitle + "' created!", "choose command");
            } catch (Exception e) {
                sendMsgToClient("Room '" + roomTitle + "' already exist!", "choose command");
                return;
            }
        }

        private void chooseRoom() {
            int numberRoom;
            List<Chatroom> allRooms = roomsService.showAllRooms();
            if (allRooms.isEmpty()) {
                sendMsgToClient("No room created!", "choose command");
                return;
            } else {
                StringJoiner sb = new StringJoiner("\n");
                for (int i = 0; i < allRooms.size(); i++) {
                    sb.add(allRooms.get(i).getTitle());
                }
                sendMsgToClient(sb.toString(), "showAllRooms");
            }
            try {
                JSONMessage messageJson = JSONConverter.parseToObject(reader.nextLine().trim());
                numberRoom = Integer.parseInt(messageJson.getMessageJSON());
                String code = messageJson.getCODE();
                if ("back".equals(code)) {
                   sendMsgToClient("Choose command: ", "choose command");
                   return;
                }
                roomTitle = allRooms.get(numberRoom - 1).getTitle();
                System.out.println("User '" + username + "' in room: '" + roomTitle + "'");
                talk();
                sendMsgToClient("You have left the room" , "choose command");
            } catch (NoSuchElementException e) {
                return;
            } catch (Exception e) {
                sendMsgToClient("Something went wrong", "ERROR");
            }
        }

        private void showLastThirtyMessage(String title) {
            List<Message> allMessage = usersService.getAllMessageByTitle(title);
            StringJoiner strToSend = new StringJoiner("\n");
            if (!allMessage.isEmpty()) {
                for (Message msg : allMessage) {
                    strToSend.add("[" + msg.getTime().format(FORMATTER) + "] " + msg.getAuthor() + ": " + msg.getMessage());
                }
                sendMsgToClientWithRoom(strToSend.toString(), "history messages", title);
            } else {
                sendMsgToClientWithRoom("Please, write the first message in the room!", "history messages", title);
            }
        }

        private void talk() {
            showLastThirtyMessage(roomTitle);
            sendMessageToRoom(username + " joined the chat", roomTitle, username, false);
            while (true) {
                JSONMessage messageJson = JSONConverter.parseToObject(reader.nextLine().trim());
                String message = messageJson.getMessageJSON();
                String code = messageJson.getCODE();
                if ("back".equals(code)) {
                    String saveTitle = roomTitle;
                    roomTitle = "";
                    System.out.println(username + " left room: " + saveTitle);
                    sendMessageToRoom(username + " have left the room", saveTitle, username, false);
                    sendMsgToClient("You have left the room: " +  saveTitle, "choose command");
                    return;
                }
                sendMessageToRoom(message, roomTitle, username, true);
            }
        }

        private void exitChat() {
            try {
                sendMsgToClient("Finish program", "EXIT");
                removeClient(this);
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
