package edu.school21.sockets.GUI.Windows;

import edu.school21.sockets.GUI.Windows.Models.BoxLayoutUtils;

import javax.swing.*;
import java.awt.*;

public class StartWindow extends AWindow {
    private JFrame frame;
    private JButton buttonSignUp;

    private JButton buttonSignIn;
    private JButton buttonExit;


    public StartWindow(JFrame frame, int width, int height, String msg) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init(msg));
        frame.setVisible(true);
    }

    private JPanel init(String msg) {
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel(msg);
        mainPanel.add(label);
        label.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.setLayout(new GridLayout(4,1));
        buttonSignUp = new JButton("Sign Up");
        buttonSignIn = new JButton("Sign In");
        buttonExit = new JButton("Exit");
        mainPanel.add(buttonSignUp);
        mainPanel.add(buttonSignIn);
        mainPanel.add(buttonExit);
        return mainPanel;
    }

    public JButton getButtonSignUp() {
        return buttonSignUp;
    }

    public JButton getButtonSignIn() {
        return buttonSignIn;
    }

    public JButton getButtonExit() {
        return buttonExit;
    }
}
