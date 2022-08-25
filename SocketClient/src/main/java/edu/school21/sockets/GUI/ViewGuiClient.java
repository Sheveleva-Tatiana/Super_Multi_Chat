package edu.school21.sockets.GUI;

import edu.school21.sockets.GUI.Windows.*;
import edu.school21.sockets.client.JSONConverter;
import edu.school21.sockets.client.JSONMessage;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.*;

public class ViewGuiClient {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private static String nameFrame;
    private static String messageToServer;
    private static JPasswordField password = null;
    private static JTextField textField = null;

    private Scanner reader;
    private PrintWriter writer;

    public ViewGuiClient(Scanner reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void init(String msg){
        StartWindow startWindow = new StartWindow(new JFrame("Chat"), WIDTH, HEIGHT, msg);
        JButton buttonSignUp = startWindow.getButtonSignUp();
        JButton buttonSignIn = startWindow.getButtonSignIn();
        JButton buttonExit = startWindow.getButtonExit();

        buttonSignUp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("signUP", "answer1");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                nameFrame = "Registration";
                startWindow.setVisible(false);
            }
        });

        buttonSignIn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("signIn", "answer2");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                nameFrame = "Authorization";
                startWindow.setVisible(false);
            }
        });

        buttonExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("finishProgram", "finishProgram");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                startWindow.setVisible(false);
            }
        });
    }

    public void getNameAndPassword() {
        RegistrationWindow registrationWindow = new RegistrationWindow(new JFrame(nameFrame), WIDTH, 200);
        JButton sendButton = registrationWindow.getBtnOk();
        JButton backButton = registrationWindow.getBtnCancel();
        textField = registrationWindow.getTfLogin();
        password = registrationWindow.getTfPassword();

        sendButton.setEnabled(false);
        textField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!textField.getText().equals("") && !String.valueOf(password.getPassword()).equals("")) {
                    sendButton.setEnabled(true);
                }
            }
        });
        password.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!textField.getText().equals("") && !String.valueOf(password.getPassword()).equals("")) {
                    sendButton.setEnabled(true);
                }
            }
        });

        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendUsername = textField.getText();
                textField = null;
                String toSendPassword = String.valueOf(password.getPassword());
                password = null;
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendUsername + ":" + toSendPassword, "username");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                registrationWindow.setVisible(false);
            }
        });
        backButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("back", "back");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                registrationWindow.setVisible(false);
            }
        });
    }

    public void choosingCommand(String msg) {
        ChooseCmdWindow chooseCmdWindow = new ChooseCmdWindow(new JFrame("Chat"), WIDTH, HEIGHT, msg);
        JButton buttonCreate = chooseCmdWindow.getButtonCreate();
        JButton buttonChoose = chooseCmdWindow.getButtonChoose();
        JButton buttonExitd = chooseCmdWindow.getButtonExit();

        buttonCreate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("signUP", "answer1");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                chooseCmdWindow.setVisible(false);
            }
        });

        buttonChoose.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("signIn", "answer2");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                chooseCmdWindow.setVisible(false);
            }
        });

        buttonExitd.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("finishProgram", "finishProgram");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                chooseCmdWindow.setVisible(false);
            }
        });
    }

    public void createNewRoom() {
        CreateNewRoomWindow createNewRoomWindow = new CreateNewRoomWindow(new JFrame("Creating a new room"), WIDTH, HEIGHT);

        JButton sendButton = createNewRoomWindow.getSendButton();
        JButton backButton = createNewRoomWindow.getBackButton();
        JTextField titleField = createNewRoomWindow.getTitleField();

        sendButton.setEnabled(false);
        titleField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!titleField.getText().equals("")) {
                    sendButton.setEnabled(true);
                }
            }
        });
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendTitle = titleField.getText();
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendTitle, "title");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                createNewRoomWindow.setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("back", "back");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                createNewRoomWindow.setVisible(false);
            }
        });
    }

    public void showListRooms(String msg) {
        ShowListRoomsWindow showListRoomsWindow = new ShowListRoomsWindow(new JFrame("Chat"), WIDTH, HEIGHT, msg);
        JComboBox box = showListRoomsWindow.getBox();
        JButton buttonOk = showListRoomsWindow.getButtonOk();
        JButton buttonBack = showListRoomsWindow.getButtonBack();

        buttonOk.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = String.valueOf(box.getSelectedIndex() + 1);
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage, "ok");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                showListRoomsWindow.setVisible(false);
            }
        });
        buttonBack.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("back", "back");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                showListRoomsWindow.setVisible(false);
            }
        });
    }

    public void startTalking(String msg, String userList) {
        StartTalkWindow startTalkWindow = new StartTalkWindow(new JFrame("Chat"), WIDTH * 2, HEIGHT * 2, msg, userList);
        JTextArea messagesWindow = startTalkWindow.getMessagesWindow();
        JTextArea usersWindow = startTalkWindow.getUsersWindow();
        JTextField textFieldForTalk = startTalkWindow.getTextFieldForTalk();
        JButton buttonToSend = startTalkWindow.getButtonToSend();
        JButton buttonToLeave = startTalkWindow.getButtonToLeave();

        while(startTalkWindow.isVisible()) {
            try {
                String getJSON = reader.nextLine();
                JSONMessage jsonMessage = JSONConverter.parseToObject(getJSON);
                String message = jsonMessage.getMessage();
                String users = jsonMessage.getUsers();
                messagesWindow.append(message + "\n");
                usersWindow.setText(users);
                buttonToSend.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String toSendMessage = textFieldForTalk.getText();
                        JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage, "msg");
                        assert messageJSON != null;
                        messageToServer = messageJSON.toJSONString();
                        textFieldForTalk.setText(null);
                        writer.println(messageToServer);
                    }
                });
                buttonToLeave.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JSONObject messageJSON = JSONConverter.makeJSONObject("0", "back");
                        assert messageJSON != null;
                        messageToServer = messageJSON.toJSONString();
                        textFieldForTalk.setText(null);
                        startTalkWindow.setVisible(false);
                        writer.println(messageToServer);
                    }
                });
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

    public static void viewReconnect() {
        ReconnectWindow reconnectWindow = new ReconnectWindow(new JFrame("Connection is lost"), WIDTH, HEIGHT);
        JButton buttonStart = reconnectWindow.getButtonStart();
        JButton buttonExit = reconnectWindow.getButtonExit();

        while (reconnectWindow.isVisible()) {
            buttonStart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    reconnectWindow.setVisible(false);
                }
            });

            buttonExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            });
        }
    }

    public void makeInvisibleFrames() {
//        if (framePassword != null)
//            framePassword.setVisible(false);
//        if (frame != null)
//            frame.setVisible(false);
//        if (frameForTalk != null)
//            frameForTalk.setVisible(false);
    }
}
