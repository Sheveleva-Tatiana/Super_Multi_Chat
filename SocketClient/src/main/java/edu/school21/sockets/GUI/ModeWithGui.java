package edu.school21.sockets.GUI;

import edu.school21.sockets.client.ServerReader;
import edu.school21.sockets.client.ServerWriter;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ModeWithGui {
    private Scanner reader;
    private PrintWriter writer;
    private ViewGuiClient manage;

    public ModeWithGui(Scanner reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
        manage = new ViewGuiClient(reader, writer);
    }

    public void startProgram(String msg) {
        manage.init(msg);
    }

    public void registration() {
        manage.getNameAndPassword();
    }

    public void chooseRoom(String message) {
        manage.choosingCommand(message);
    }

    public void creatingRoom() {
        manage.createNewRoom();
    }

    public void listRooms(String msg) {
        manage.showListRooms(msg);
    }

    public void startTalk(String msg, String users) {
        manage.startTalking(msg, users);
    }

    public void makeInvisibleFrames() {manage.makeInvisibleFrames();}


}
