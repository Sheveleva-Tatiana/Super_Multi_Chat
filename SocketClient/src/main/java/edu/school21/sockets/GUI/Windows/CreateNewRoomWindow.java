package edu.school21.sockets.GUI.Windows;

import edu.school21.sockets.GUI.Windows.Models.BoxLayoutUtils;

import javax.swing.*;
import java.awt.*;

public class CreateNewRoomWindow extends AWindow {
    private JTextField titleField;
    private JButton sendButton, backButton;

    public CreateNewRoomWindow(JFrame frame, int width, int height) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init());
        frame.setVisible(true);
    }

    private JPanel init() {
        JPanel contentPane = BoxLayoutUtils.createVerticalPanel();

        contentPane.add(Box.createVerticalStrut(50));

        JPanel content = BoxLayoutUtils.createHorizontalPanel();
        JLabel title = new JLabel("Enter title: ");
        titleField = new JTextField(30);
        content.add(Box.createHorizontalStrut(10));
        content.add(title);
        content.add(titleField);
        content.add(Box.createHorizontalStrut(10));


        contentPane.add(content);
        JPanel grid = new JPanel( new GridLayout( 1,4,5,0) );

        sendButton = new JButton("Ok");
        backButton = new JButton("Back");
        grid.add(Box.createHorizontalStrut(10));
        grid.add(Box.createHorizontalStrut(10));
        grid.add(sendButton);
        grid.add(backButton);

        contentPane.add(Box.createVerticalStrut(10));

        contentPane.add(grid);
        contentPane.add(Box.createVerticalStrut(50));


        return contentPane;
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
