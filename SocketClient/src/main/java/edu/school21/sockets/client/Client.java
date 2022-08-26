package edu.school21.sockets.client;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;

    public Client(String ip, int port) throws IOException {
        socket = SocketFactory.getDefault().createSocket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() throws InterruptedException {
        ServerReader serverReader = new ServerReader(reader, writer, socket);
        serverReader.start();
        serverReader.join();
    }

    public synchronized static void close(PrintWriter writer,  Scanner reader, Socket socket) throws IOException {
        reader.close();
        writer.close();
        socket.close();
        System.exit(0);
    }
}