package edu.school21.sockets.GUI;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModeWithGui {
    private ViewGuiClient manage;

    public ModeWithGui(Scanner reader, PrintWriter writer) {
        manage = new ViewGuiClient(reader, writer);
    }

    public void startProgram(String msg) {
        manage.init(msg);
    }

    public void registration() {
        manage.getNameAndPassword();
    }

    public void chooseCommand(String message) {
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


    public void confirmDeletion() {
        manage.confirmDeletionGUI();
    }
}
