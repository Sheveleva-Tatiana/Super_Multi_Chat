package edu.school21.sockets.GUI;

import edu.school21.sockets.GUI.Windows.*;
import edu.school21.sockets.client.json.JSONConverter;
import edu.school21.sockets.client.json.JSONMessage;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.*;

public class ViewGuiClient {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private String nameFrame;
    private String messageToServer;
    private String room = "";
    private JPasswordField password = null;
    private JTextField textField = null;

    public static int newPort = 8000;
    public static String newHost = "127.0.0.1";

    private StartWindow startWindow;
    private RegistrationWindow registrationWindow;
    private ChooseCmdWindow chooseCmdWindow;
    private CreateNewRoomWindow createNewRoomWindow;
    private ShowListRoomsWindow showListRoomsWindow;
    private StartTalkWindow startTalkWindow;
    private SureWindow sureWindow;

    private final Scanner reader;
    private final PrintWriter writer;

    public ViewGuiClient(Scanner reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void init(String msg){
        startWindow = new StartWindow(new JFrame("Chat"), WIDTH, HEIGHT, msg);
        JButton buttonSignUp = startWindow.getButtonSignUp();
        JButton buttonSignIn = startWindow.getButtonSignIn();
        JButton buttonExit = startWindow.getButtonExit();

        buttonSignUp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("signUP", "answer1");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                nameFrame = "Registration";
                startWindow.setVisible(false);
            }
        });

        buttonSignIn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("signIn", "answer2");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                nameFrame = "Authorization";
                startWindow.setVisible(false);
            }
        });

        buttonExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("finishProgram", "finishProgram");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                startWindow.setVisible(false);
            }
        });
    }

    public void getNameAndPassword() {
        registrationWindow = new RegistrationWindow(new JFrame(nameFrame), WIDTH, 200);
        JButton sendButton = registrationWindow.getBtnOk();
        JButton backButton = registrationWindow.getBtnCancel();
        textField = registrationWindow.getTfLogin();
        password = registrationWindow.getTfPassword();

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
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendUsername + ":" + toSendPassword, "username");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                registrationWindow.setVisible(false);
            }
        });
        backButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("back", "back");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                registrationWindow.setVisible(false);
            }
        });
    }

    public void choosingCommand(String msg) {
        chooseCmdWindow = new ChooseCmdWindow(new JFrame("Chat"), WIDTH, HEIGHT, msg);
        JButton buttonCreate = chooseCmdWindow.getButtonCreate();
        JButton buttonChoose = chooseCmdWindow.getButtonChoose();
        JButton buttonExitd = chooseCmdWindow.getButtonExit();
        JButton buttonDelete = chooseCmdWindow.getButtonDelete();

        buttonCreate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("createRoom", "answer1");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                chooseCmdWindow.setVisible(false);
            }
        });

        buttonChoose.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("chooseRoom", "answer2");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                chooseCmdWindow.setVisible(false);
            }
        });
        buttonDelete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("delete", "answer3");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                chooseCmdWindow.setVisible(false);
            }
        });


        buttonExitd.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("finishProgram", "finishProgram");
                assert messageJSON != null;
                String message = messageJSON.toJSONString();
                writer.println(message);
                chooseCmdWindow.setVisible(false);
            }
        });
    }

    public void createNewRoom() {
        createNewRoomWindow = new CreateNewRoomWindow(new JFrame("Creating a new room"), WIDTH, HEIGHT);

        JButton sendButton = createNewRoomWindow.getSendButton();
        JButton backButton = createNewRoomWindow.getBackButton();
        JTextField titleField = createNewRoomWindow.getTitleField();

        sendButton.setEnabled(false);
        titleField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!titleField.getText().equals("")) {
                    sendButton.setEnabled(true);
                }
            }
        });
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendTitle = titleField.getText();
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendTitle, "title");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                createNewRoomWindow.setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("back", "back");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                createNewRoomWindow.setVisible(false);
            }
        });
    }

    public void showListRooms(String msg) {
        showListRoomsWindow = new ShowListRoomsWindow(new JFrame("Chat"), WIDTH, HEIGHT, msg);
        JComboBox box = showListRoomsWindow.getBox();
        JButton buttonOk = showListRoomsWindow.getButtonOk();
        JButton buttonBack = showListRoomsWindow.getButtonBack();

        buttonOk.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = String.valueOf(box.getSelectedIndex() + 1);
                room = String.valueOf(box.getSelectedItem());
                JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage, "ok");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                showListRoomsWindow.setVisible(false);
            }
        });
        buttonBack.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("0", "back");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                writer.println(messageToServer);
                showListRoomsWindow.setVisible(false);
            }
        });
    }

    public void startTalking(String msg, String userList) {
        startTalkWindow = new StartTalkWindow(new JFrame("Chat"), WIDTH * 2, HEIGHT * 2, msg, userList, room);
        JTextArea messagesWindow = startTalkWindow.getMessagesWindow();
        JTextArea usersWindow = startTalkWindow.getUsersWindow();
        JTextField textFieldForTalk = startTalkWindow.getTextFieldForTalk();
        JButton buttonToSend = startTalkWindow.getButtonToSend();
        JButton buttonToLeave = startTalkWindow.getButtonToLeave();

        buttonToSend.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String toSendMessage = textFieldForTalk.getText();
                if (!toSendMessage.equals("")) {
                    JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage, "msg");
                    assert messageJSON != null;
                    messageToServer = messageJSON.toJSONString();
                    textFieldForTalk.setText(null);
                    writer.println(messageToServer);
                }
            }
        });
        buttonToLeave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("0", "back");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                textFieldForTalk.setText(null);
                startTalkWindow.setVisible(false);
                room = "";
                writer.println(messageToServer);
            }
        });
        while(startTalkWindow.isVisible()) {
            try {
                String getJSON = reader.nextLine();
                JSONMessage jsonMessage = JSONConverter.parseToObject(getJSON);
                String message = jsonMessage.getMessage();
                String users = jsonMessage.getUsers();
                messagesWindow.append(message + "\n");
                usersWindow.setText(users);
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

    public void confirmDeletionGUI() {
        sureWindow = new SureWindow(new JFrame("Confirm the decision"), 300, 150);
        JButton buttonYes = sureWindow.getButtonYes();
        JButton buttonNo = sureWindow.getButtonNo();

        buttonYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("yes", "yes");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                sureWindow.setVisible(false);
                writer.println(messageToServer);
            }
        });
        buttonNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject messageJSON = JSONConverter.makeJSONObject("no", "no");
                assert messageJSON != null;
                messageToServer = messageJSON.toJSONString();
                sureWindow.setVisible(false);
                writer.println(messageToServer);
            }
        });

    }

    public void makeInvisibleFrames() {
        if (startWindow != null)
            startWindow.setVisible(false);

        if (registrationWindow != null)
            registrationWindow.setVisible(false);

        if (chooseCmdWindow != null)
            chooseCmdWindow.setVisible(false);

        if (createNewRoomWindow != null)
            createNewRoomWindow.setVisible(false);

        if (showListRoomsWindow != null)
            showListRoomsWindow.setVisible(false);

        if (startTalkWindow != null)
            startTalkWindow.setVisible(false);
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                                  STATIC METHODS FOR RECONNECT FROM MAIN
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static void viewReconnect() {
        ReconnectWindow reconnectWindow = new ReconnectWindow(new JFrame("Connection is lost"), WIDTH, HEIGHT);
        JButton buttonStart = reconnectWindow.getButtonStart();
        JButton buttonExit = reconnectWindow.getButtonExit();
        JButton buttonEdit = reconnectWindow.getButtonEdit();

        while (reconnectWindow.isVisible()) {
            buttonStart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    reconnectWindow.setVisible(false);
                }
            });

            buttonEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    reconnectWindow.setEdit(true);
                    reconnectWindow.setVisible(false);
                }
            });

            buttonExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            });
        }
        if (reconnectWindow.isEdit()) {
            createConfiguration();
        }
    }

    private static void createConfiguration() {
        EditConfigurationWindow edit = new EditConfigurationWindow(new JFrame("Configuration"), 500, 250);
        JTextField textHost = edit.getTextHost();
        JTextField textPort = edit.getTextPort();
        JButton buttonOk = edit.getButtonOk();

        while(edit.isVisible()) {
            buttonOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        newPort = Integer.parseInt(textPort.getText());
                        newHost = textHost.getText();
                    } catch (Exception e) {
                        newPort = 8000;
                        newHost = "\"127.0.0.1\"";
                    }
                    edit.setVisible(false);
                }
            });
        }
    }



}
