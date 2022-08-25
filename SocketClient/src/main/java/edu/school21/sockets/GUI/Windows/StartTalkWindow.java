package edu.school21.sockets.GUI.Windows;

import edu.school21.sockets.GUI.Windows.Models.BoxLayoutUtils;

import javax.swing.*;
import java.awt.*;

public class StartTalkWindow extends AWindow{
    private JFrame frame;



    private JTextArea messagesWindow, usersWindow;
    private JTextField textFieldForTalk;
    private JButton buttonToSend, buttonToLeave;

    public StartTalkWindow(JFrame frame, int width, int height, String msg, String userList) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init(msg, userList));
        frame.setVisible(true);
    }

    private JPanel init(String msg, String userList) {
        JPanel main = BoxLayoutUtils.createVerticalPanel();

        JPanel panelForInfo = new JPanel();
        messagesWindow = new JTextArea(30, 70);
        usersWindow = new JTextArea(30, 25);
        panelForInfo.add(new JScrollPane(messagesWindow), BorderLayout.CENTER);
        panelForInfo.add(new JScrollPane(usersWindow), BorderLayout.EAST);

        JPanel panelForCommunicate = BoxLayoutUtils.createHorizontalPanel();
        JPanel panelForInput = BoxLayoutUtils.createVerticalPanel();
        textFieldForTalk = new JTextField(15);
        panelForInput.add(Box.createVerticalStrut(10));
        panelForInput.add(textFieldForTalk);
        panelForInput.add(Box.createVerticalStrut(10));

        buttonToSend = new JButton("Send");
        buttonToLeave = new JButton("Leave");
        panelForCommunicate.add(Box.createHorizontalStrut(12));
        panelForCommunicate.add(panelForInput);
        panelForCommunicate.add(Box.createHorizontalStrut(5));
        panelForCommunicate.add(buttonToSend);
        panelForCommunicate.add(buttonToLeave);
        panelForCommunicate.add(Box.createHorizontalStrut(10));


        messagesWindow.setText(msg + "\n");
        usersWindow.setText(userList);

        main.add(panelForInfo);
        main.add(panelForCommunicate);
        return main;
    }

    public JTextArea getMessagesWindow() {
        return messagesWindow;
    }

    public JTextArea getUsersWindow() {
        return usersWindow;
    }

    public JTextField getTextFieldForTalk() {
        return textFieldForTalk;
    }

    public JButton getButtonToSend() {
        return buttonToSend;
    }

    public JButton getButtonToLeave() {
        return buttonToLeave;
    }


}
