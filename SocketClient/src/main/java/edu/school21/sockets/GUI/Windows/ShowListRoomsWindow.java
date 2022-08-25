package edu.school21.sockets.GUI.Windows;

import edu.school21.sockets.GUI.Windows.Models.BoxLayoutUtils;

import javax.swing.*;

public class ShowListRoomsWindow extends AWindow {
    JComboBox box;
    JButton buttonOk, buttonBack;

    public ShowListRoomsWindow(JFrame frame, int width, int height, String msg) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init(msg));
        frame.setVisible(true);
    }

    private JPanel init(String msg) {
        JPanel mainPanel = BoxLayoutUtils.createVerticalPanel();
        mainPanel.add(Box.createVerticalStrut(140));
        JPanel content = BoxLayoutUtils.createHorizontalPanel();
        JLabel label = new JLabel("Rooms: ");
        content.add(label);

        String[] str = msg.split("\n");
        box = new JComboBox(str);
        box.setEditable(true);
        content.add(box);

        buttonOk = new JButton("Ok");
        buttonBack = new JButton("Back");
        content.add(buttonOk);
        content.add(buttonBack);
        mainPanel.add(content);
        mainPanel.add(Box.createVerticalStrut(140));

        return mainPanel;
    }

    public JComboBox getBox() {
        return box;
    }

    public JButton getButtonOk() {
        return buttonOk;
    }

    public JButton getButtonBack() {
        return buttonBack;
    }
}
