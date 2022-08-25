package edu.school21.sockets.client;

import edu.school21.sockets.GUI.ModeWithGui;
import edu.school21.sockets.GUI.ViewGuiClient;
import edu.school21.sockets.app.Main;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ServerReader extends Thread {
    private Scanner reader;
    private Socket socket;
    private  PrintWriter writer;
    private ModeWithGui mode;
    ServerWriter serverWriter;

    public ServerReader(Scanner reader, PrintWriter writer, Socket socket, ServerWriter serverWriter) {
        this.reader = reader;
        this.serverWriter = serverWriter;
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
                mode.chooseRoom(message);
            } else if ("enterTitleRoom".equalsIgnoreCase(code)) {
                mode.creatingRoom();
            } else if ("showAllRooms".equalsIgnoreCase(code)) {
                mode.listRooms(message);
            } else if ("history messages".equalsIgnoreCase(code)) {
                mode.startTalk(message, users);
            } else if ("EXIT".equals(code)) {
                Client.close(writer, reader, socket, 0);
            }
        }
        mode.makeInvisibleFrames();
    }

}
