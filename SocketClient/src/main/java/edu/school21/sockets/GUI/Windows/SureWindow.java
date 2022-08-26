package edu.school21.sockets.GUI.Windows;

import javax.swing.*;
import java.awt.*;

public class SureWindow extends AWindow {
    private JButton buttonNo, buttonYes;



    public SureWindow(JFrame frame, int width, int height) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init());
        frame.setVisible(true);
    }

    private JPanel init() {
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel("Are you sure you want to delete your profile?");
        mainPanel.add(label);
        label.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.setLayout(new GridLayout(5,1));
        buttonYes = new JButton("Yes");
        buttonNo = new JButton("No");
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(label);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(buttonYes);
        mainPanel.add(buttonNo);
        return mainPanel;
    }

    public JButton getButtonNo() {
        return buttonNo;
    }

    public JButton getButtonYes() {
        return buttonYes;
    }
}
