package edu.school21.sockets.GUI;

import edu.school21.sockets.client.ServerReader;
import edu.school21.sockets.client.ServerWriter;

import java.io.PrintWriter;
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

    public Scanner getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void startProgram(String msg) {
        manage.init(msg);
    }

    public void registration(String msg) {
        manage.getNameAndPassword(msg);
    }

    public void chooseRoom() {
        manage.choosingRoom();
    }

    public void listRooms(String msg) {
        manage.showListRooms(msg);
    }


}
