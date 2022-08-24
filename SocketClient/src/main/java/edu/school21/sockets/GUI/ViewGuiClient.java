package edu.school21.sockets.GUI;

import edu.school21.sockets.client.JSONConverter;
import edu.school21.sockets.client.JSONMessage;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.*;

import static javax.swing.GroupLayout.Alignment.LEADING;

public class ViewGuiClient {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private static final int WIDTH_TEXT_AREA = 395;
    private static final int WIDTH_USER_AREA = 200;
    private static final int HEIGHT_AREA = 250;
    private static final int WIDTH_BUTTON = 90;
    private static final int HEIGHT_BUTTON = 20;
    private static JFrame frame;
    private static JFrame frameForTalk;
    private static JTextArea messagesWindow;
    private static JTextArea usersWindow;
    private static JTextField textFieldForTalk;
    private static JPanel panelForTalk;
    private static JButton buttonToSend;
    private static JButton buttonToLeave;

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
        String[] str = sp.toString().split("\n");
        JComboBox box = new JComboBox(str);
        box.setEditable(true);
        mainPanel.add(box);
        JButton buttonOk = new JButton("Ok");
        JButton buttonBack = new JButton("Back");
        buttonOk.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = String.valueOf(box.getSelectedIndex() + 1);
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                frame.setVisible(false);
            }
        });
        buttonBack.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = String.valueOf(str.length + 1);
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                frame.setVisible(false);
            }
        });
        mainPanel.add(buttonOk);
        mainPanel.add(buttonBack);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    public void init(String msg){
        frame = new JFrame("Chat");
        initMainWindow(frame);
        initWelcomePanel(msg, "1. SignUp", "2.SignIn", "3.Exit");
        clickButtons();
    }

    public void getNameAndPassword() {
        JFrame frameName = new JFrame(nameFrame);
        initMainWindow(frameName);
        addComponentsToPane(frameName);
    }

    private void addComponentsToPane(JFrame frameName) {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel panelUsername = new JPanel();
        panelUsername.setLayout(new BoxLayout(panelUsername, BoxLayout.X_AXIS));
        JLabel labelUserName = new JLabel("Enter username: ");
        textField = new JTextField(30);
        panelUsername.add(labelUserName);
        panelUsername.add(textField);

        JPanel panelPassword = new JPanel();
        panelPassword.setLayout(new BoxLayout(panelPassword, BoxLayout.X_AXIS));
        JLabel labelPassword = new JLabel("Enter password: ");
        panelPassword.add(labelPassword);
        password = new JPasswordField(30);
        password.setEchoChar('*');
        panelPassword.add(password);

        JButton sendButton = new JButton("Ok");
        contentPane.add(panelUsername);
        contentPane.add(panelPassword);
        contentPane.add(sendButton);
        frameName.add(contentPane);
        frameName.setVisible(true);
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
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendUsername);
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                messageJSON = JSONConverter.makeJSONObject(toSendPassword);
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

    public void startTalking(String msg, String userList) {
        frameForTalk = new JFrame("Chat");
        initMainWindow(frameForTalk);
        messagesWindow = new JTextArea(30, 20);
        usersWindow = new JTextArea(30, 15);
        textFieldForTalk = new JTextField(30);
        frameForTalk.add(new JScrollPane(messagesWindow), BorderLayout.CENTER);
        frameForTalk.add(new JScrollPane(usersWindow), BorderLayout.EAST);
        messagesWindow.setText(msg + "\n");
        usersWindow.setText(userList);
        panelForTalk = new JPanel();
        panelForTalk.add(textFieldForTalk);
        buttonToSend = new JButton("Send");
        buttonToLeave = new JButton("Leave");
        panelForTalk.add(buttonToSend);
        panelForTalk.add(buttonToLeave);
        frameForTalk.add(panelForTalk, BorderLayout.SOUTH);
        frameForTalk.setVisible(true);
    }

    public void talking() {
        while(frameForTalk.isVisible()) {
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
                    JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                    assert messageJSON != null;
                    messageToServer = messageJSON.toJSONString();
                    textFieldForTalk.setText(null);
                    writer.println(messageToServer);
                }
            });
            buttonToLeave.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String toSendMessage = "exitChat";
                    JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
                    assert messageJSON != null;
                    messageToServer = messageJSON.toJSONString();
                    textFieldForTalk.setText(null);
                    writer.println(messageToServer);
                    frameForTalk.setVisible(false);
                }
            });
        }
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
                nameFrame = "Authorization";
                frame.setVisible(false);
            }
        });

        buttonExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = "exitProgram";
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
        frameTemp.setLocationRelativeTo(null);
    }

    private JButton createButtons(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(x, y));
        return button;
    }
}
