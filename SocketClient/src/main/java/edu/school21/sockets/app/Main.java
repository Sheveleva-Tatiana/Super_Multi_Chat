package edu.school21.sockets.app;

import edu.school21.sockets.GUI.ViewGuiClient;
import edu.school21.sockets.client.Client;

public class Main {
    public static void main(String[] args) {
        while (true) {
            newConnection();
            ViewGuiClient.viewReconnect();
        }
    }

    public static void newConnection() {
        try {
            Client client = new Client(ViewGuiClient.newHost, ViewGuiClient.newPort);
            client.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}