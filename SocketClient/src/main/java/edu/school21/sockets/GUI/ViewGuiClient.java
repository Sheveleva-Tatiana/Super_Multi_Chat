package edu.school21.sockets.GUI;

import edu.school21.sockets.client.JSONConverter;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.*;

import static javax.swing.GroupLayout.Alignment.LEADING;

public class ViewGuiClient {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private static final int WIDTH_BUTTON = 90;
    private static final int HEIGHT_BUTTON = 20;
    private static JFrame frame;
    private static JButton buttonSignIn;
    private static JButton buttonSignUp;
    private static JButton buttonExit;
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

    public void showListRooms(String msg) {
        frame = new JFrame("Chat");
        JPanel mainPanel = new JPanel();
        initMainWindow(frame);
        StringBuilder sp = new StringBuilder(msg);
        JLabel label = new JLabel(sp.substring(0, 8));
        mainPanel.add(label);
        sp.delete(0, 8);
        JComboBox box = new JComboBox(sp.toString().split("\n"));
        box.setEditable(true);

        mainPanel.add(box);


        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    public void init(String msg){
        frame = new JFrame("Chat");
        initMainWindow(frame);
        initWelcomePanel(msg, "1. SignUp", "2.SignIn", "3.Exit");
        clickButtons();
    }

    public void getNameAndPassword(String labelName) {
        JFrame frameName = new JFrame(nameFrame);
        initMainWindow(frameName);
        addComponentsToPane(frameName.getContentPane(), labelName, frameName);
    }

    private void addComponentsToPane(Container contentPane, String labelName, JFrame frameName) {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setSize(20, WIDTH);
        JLabel label = new JLabel(labelName);
        contentPane.add(label);

        if (labelName.equals("Enter username: ")) {
            textField = new JTextField(60);
            textField.setPreferredSize(new Dimension(60, 15));
            contentPane.add(textField);
        } else {
            password = new JPasswordField(12);
            password.setEchoChar('*');
            contentPane.add(password);
        }
        JButton sendButton = new JButton("Ok");
        contentPane.add(sendButton);
        frameName.setVisible(true);
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = null;
                if (password != null) {
                    toSendMessage = String.valueOf(password.getPassword());
                    password = null;
                }
                if (textField != null) {
                    toSendMessage = textField.getText();
                    textField = null;
                }
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                frameName.setVisible(false);
            }
        });
    }

    public void choosingRoom() {
        frame = new JFrame("Chat");
        initMainWindow(frame);
        initWelcomePanel("Choose command: ", "1. Create room", "2. Choose room", "3.Logout");
        clickButtons();
    }

    private void clickButtons() {
        buttonSignUp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toSendMessage = "1";
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                nameFrame = "Registration";
                frame.setVisible(false);
            }
        });

        buttonSignIn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = "2";
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                nameFrame = "Authorisation";
                frame.setVisible(false);
            }
        });

        buttonExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = "exit";
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                frame.setVisible(false);
            }
        });

    }

    private void initWelcomePanel(String msg, String button1, String button2, String button3) {
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel(msg);
        mainPanel.add(label);
        label.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.setLayout(new GridLayout(4,1));
        buttonSignUp = createButtons(button1, WIDTH_BUTTON, HEIGHT_BUTTON);
        buttonSignIn = createButtons(button2, WIDTH_BUTTON, HEIGHT_BUTTON);
        buttonExit = createButtons(button3, WIDTH_BUTTON, HEIGHT_BUTTON);
        mainPanel.add(buttonSignUp);
        mainPanel.add(buttonSignIn);
        mainPanel.add(buttonExit);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }


    private void initMainWindow(JFrame frameTemp) {
        frameTemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTemp.setSize(WIDTH,HEIGHT);
    }

    private JButton createButtons(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(x, y));
        return button;
    }
}
