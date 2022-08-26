package edu.school21.sockets.client;

import edu.school21.sockets.GUI.ModeWithGui;
import edu.school21.sockets.client.json.JSONConverter;
import edu.school21.sockets.client.json.JSONMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerReader extends Thread {
    private Scanner reader;
    private Socket socket;
    private  PrintWriter writer;
    private ModeWithGui mode;

    public ServerReader(Scanner reader, PrintWriter writer, Socket socket) {
        this.reader = reader;
        this.writer = writer;
        this.socket = socket;
        mode = new ModeWithGui(reader, writer);
    }

    @Override
    public void run() {
        try {
            receiveMessage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage() throws IOException, InterruptedException {
        while (reader.hasNextLine()) {
            String getJSON = reader.nextLine();
            JSONMessage jsonMessage = JSONConverter.parseToObject(getJSON);
            String message = jsonMessage.getMessage();
            String code = jsonMessage.getCode();
            String users = jsonMessage.getUsers();

            if ("start".equalsIgnoreCase(code)) {
                mode.startProgram(message);
            } else if ("enterUserName".equalsIgnoreCase(code)) {
                mode.registration();
            } else if ("choose command".equalsIgnoreCase(code)) {
                mode.chooseCommand(message);
            } else if ("enterTitleRoom".equalsIgnoreCase(code)) {
                mode.creatingRoom();
            } else if ("showAllRooms".equalsIgnoreCase(code)) {
                mode.listRooms(message);
            } else if ("sure".equalsIgnoreCase(code)) {
                mode.confirmDeletion();
            }else if ("history messages".equalsIgnoreCase(code)) {
                mode.startTalk(message, users);
            } else if ("EXIT".equals(code)) {
                Client.close(writer, reader, socket);
            }
        }
        mode.makeInvisibleFrames();
    }

}
