package edu.school21.sockets.Serialization;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageJSON {
    private String messageText;
    private LocalDateTime dateTime;

    public MessageJSON(String messageText) {
        this.messageText = messageText;
        dateTime = LocalDateTime.now();
    }
    public MessageJSON() {

    }
}
