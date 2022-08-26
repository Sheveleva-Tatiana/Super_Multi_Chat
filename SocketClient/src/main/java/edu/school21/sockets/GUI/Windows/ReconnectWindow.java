package edu.school21.sockets.GUI.Windows;

import edu.school21.sockets.GUI.Windows.Models.BoxLayoutUtils;

import javax.swing.*;
import java.awt.*;

public class ReconnectWindow extends AWindow{

    private JButton buttonStart;
    private JButton buttonExit;
    private JButton buttonEdit;
    private JFrame frame;
    private boolean isEdit = false;



    public ReconnectWindow(JFrame frame, int width, int height) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init());
        frame.setVisible(true);
    }

    private JPanel init() {
        JPanel main = BoxLayoutUtils.createVerticalPanel();
        main.setBorder (BorderFactory.createEmptyBorder(12,12,12,12));


        JPanel panelForText = BoxLayoutUtils.createVerticalPanel();
        JPanel label1 = new JPanel();
        JLabel iconLab = new JLabel(createIcon("/icon.png"));
        label1.add(iconLab);
        panelForText.add(Box.createVerticalStrut(50));
        label1.add(createLabel("The connection to the server has been lost.\n", new Font("Verdana", Font.PLAIN, 16)));
        JPanel label2 = new JPanel();
        label2.add(createLabel( "To reconnect, press \"start\".", new Font("Verdana", Font.PLAIN, 16)));
        panelForText.add(label1);
        panelForText.add(label2);
        panelForText.add(Box.createVerticalStrut(40));

        JPanel panelForButton = new JPanel();
        panelForButton.setLayout(new GridLayout(1, 2));
        buttonStart = new JButton("Start");
        buttonEdit = new JButton("Edit");
        buttonExit = new JButton("Exit");
        panelForButton.add(buttonStart);
        panelForButton.add(buttonEdit);
        panelForButton.add(buttonExit);

        main.add(panelForText);
        main.add(panelForButton);
        main.add(Box.createVerticalStrut(5));

        return main;
    }


    public JButton getButtonStart() {
        return buttonStart;
    }

    public JButton getButtonExit() {
        return buttonExit;
    }

    public JButton getButtonEdit() {
        return buttonEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isEdit() {
        return isEdit;
    }
}
