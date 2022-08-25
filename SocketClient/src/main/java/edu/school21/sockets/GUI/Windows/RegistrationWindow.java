package edu.school21.sockets.GUI.Windows;

import edu.school21.sockets.GUI.Windows.Models.BoxLayoutUtils;
import edu.school21.sockets.GUI.Windows.Models.GUITools;

import javax.swing.*;
import java.awt.*;

public class RegistrationWindow extends AWindow {

    private JTextField tfLogin;
    private JPasswordField tfPassword;
    private JButton    btnOk, btnCancel;

    public RegistrationWindow(JFrame frame, int width, int height) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init());
        frame.setVisible(true);
    }

    private JPanel init() {
        JPanel panel = BoxLayoutUtils.createVerticalPanel();
        panel.setBorder (BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel name = BoxLayoutUtils.createHorizontalPanel();
        JLabel labelUserName = new JLabel("Enter username: ");
        name.add(labelUserName);
        name.add(Box.createHorizontalStrut(12));

        tfLogin = new JTextField(15);
        name.add(tfLogin);

        JPanel panelPassword = BoxLayoutUtils.createHorizontalPanel();
        JLabel labelPassword = new JLabel("Enter password: ");
        panelPassword.add(labelPassword);
        panelPassword.add(Box.createHorizontalStrut(12));

        tfPassword = new JPasswordField(15);
        tfPassword.setEchoChar('*');
        panelPassword.add(tfPassword);

        JPanel flow = new JPanel( new FlowLayout( FlowLayout.RIGHT, 0, 0) );
        JPanel grid = new JPanel( new GridLayout( 1,2,5,0) );

        btnOk = new JButton("Ok");
        btnCancel = new JButton("Back");
        grid.add(btnOk);
        grid.add(btnCancel);
        flow.add(grid);
        BoxLayoutUtils.setGroupAlignmentX(new JComponent[] { name, panelPassword, panel, flow },
                Component.LEFT_ALIGNMENT);
        BoxLayoutUtils.setGroupAlignmentY(new JComponent[] { tfLogin, tfPassword, labelUserName, labelPassword},
                Component.CENTER_ALIGNMENT);
        GUITools.makeSameSize(new JComponent[] { labelUserName, labelPassword } );
        GUITools.fixTextFieldSize(tfLogin   );
        GUITools.fixTextFieldSize(tfPassword);
        panel.add(name);
        panel.add(Box.createVerticalStrut(12));
        panel.add(panelPassword);
        panel.add(Box.createVerticalStrut(17));
        panel.add(flow);

        return panel;
    }

    public JTextField getTfLogin() {
        return tfLogin;
    }

    public JPasswordField getTfPassword() {
        return tfPassword;
    }

    public JButton getBtnOk() {
        return btnOk;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }
}
