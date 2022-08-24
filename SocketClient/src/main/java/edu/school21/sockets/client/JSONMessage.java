package edu.school21.sockets.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSONMessage {
    private String message;
    private String users;
    private LocalDateTime time;

    private static  final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public JSONMessage() {
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
