package edu.school21.sockets.server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSONMessage {
    private String messageJSON;
    private String usersJSON;
    private LocalDateTime timeJSON;
    private String code;
    private static  final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public JSONMessage() {
    }

    public String getCODE() {
        return code;
    }

    public void setCODE(String code) {
        this.code = code;
    }



    public String getUsersJSON() {
        return usersJSON;
    }

    public void setUsersJSON(String usersJSON) {
        this.usersJSON = usersJSON;
    }

    public String getMessageJSON() {
        return messageJSON;
    }

    public void setMessageJSON(String messageJSON) {
        this.messageJSON = messageJSON;
    }

    public LocalDateTime getTimeJSON() {
        return timeJSON;
    }

    public void setTimeJSON(LocalDateTime timeJSON) {
        this.timeJSON = timeJSON;
    }
}
