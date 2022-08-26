package edu.school21.sockets.GUI.Windows;

import edu.school21.sockets.GUI.Windows.Models.BoxLayoutUtils;
import edu.school21.sockets.GUI.Windows.Models.GUITools;

import javax.swing.*;
import java.awt.*;

public class EditConfigurationWindow extends AWindow{
    private JButton buttonOk;
    private JTextField textPort, textHost;

    public JTextField getTextPort() {
        return textPort;
    }

    public JTextField getTextHost() {
        return textHost;
    }

    public EditConfigurationWindow(JFrame frame, int width, int height) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init());
        frame.setVisible(true);
    }

    private JPanel init() {
        JPanel main = BoxLayoutUtils.createVerticalPanel();

        JPanel panelForHost = BoxLayoutUtils.createHorizontalPanel();
        JLabel labelHost = new JLabel("Host: ");
        textHost = new JTextField("127.0.0.1");
        panelForHost.add(Box.createHorizontalStrut(10));
        panelForHost.add(labelHost);
        panelForHost.add(textHost);
        panelForHost.add(Box.createHorizontalStrut(10));

        JPanel panelForPort = BoxLayoutUtils.createHorizontalPanel();
        JLabel labelPort = new JLabel("Port: ");
        textPort = new JTextField("8000");
        panelForPort.add(Box.createHorizontalStrut(10));
        panelForPort.add(labelPort);
        panelForPort.add(textPort);
        panelForPort.add(Box.createHorizontalStrut(10));


        JPanel panelForButton = new JPanel();
        panelForButton.setLayout(new GridLayout(1,2));
        buttonOk = new JButton("Ok");
        panelForButton.add(Box.createHorizontalStrut(300));
        panelForButton.add(buttonOk);

        GUITools.makeSameSize(new JComponent[] { labelPort, labelHost } );

        main.add(Box.createVerticalStrut(10));
        main.add(panelForHost);
        main.add(Box.createVerticalStrut(10));
        main.add(panelForPort);
        main.add(Box.createVerticalStrut(10));
        main.add(panelForButton);
        main.add(Box.createVerticalStrut(10));

        return main;
    }

    public JButton getButtonOk() {
        return buttonOk;
    }
}
