package edu.school21.sockets.GUI.Windows;

import javax.swing.*;
import java.awt.*;

public class ChooseCmdWindow extends AWindow {
    private JFrame frame;
    private JButton buttonCreate;
    private JButton buttonChoose;
    private JButton buttonExit;



    private JButton buttonDelete;

    public ChooseCmdWindow(JFrame frame, int width, int height, String msg) {
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
        mainPanel.setLayout(new GridLayout(5,1));
        buttonCreate = new JButton("Create room");
        buttonChoose = new JButton("Choose room");
        buttonDelete = new JButton("Delete profile");
        buttonExit = new JButton("Logout");
        mainPanel.add(buttonCreate);
        mainPanel.add(buttonChoose);
        mainPanel.add(buttonDelete);
        mainPanel.add(buttonExit);
        return mainPanel;
    }

    public JButton getButtonCreate() {
        return buttonCreate;
    }

    public JButton getButtonChoose() {
        return buttonChoose;
    }

    public JButton getButtonExit() {
        return buttonExit;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }
}
