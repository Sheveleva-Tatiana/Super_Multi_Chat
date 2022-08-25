package edu.school21.sockets.app;

import edu.school21.sockets.GUI.ModeWithGui;
import edu.school21.sockets.GUI.ViewGuiClient;
import edu.school21.sockets.client.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int port;
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--server-port=")) {
            System.err.println("Specify the server port using --server-port=");
            System.exit(-1);
        }
        try {
            port = Integer.parseInt(args[0].substring(14));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        toConnect();
    }
    public static void toConnect() {
        while (true) {
            newConnection();
            ViewGuiClient.viewReconnect();
        }
    }

    public static void newConnection() {
        try {
            Client client = new Client("127.0.0.1", port);
            client.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}