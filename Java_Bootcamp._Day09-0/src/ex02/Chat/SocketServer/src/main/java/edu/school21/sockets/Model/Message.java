package edu.school21.sockets.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class Message {
    private Long id;
    private Long ownerId;
    private Long chatId;
    private String messageText;
    private LocalDateTime dateTime;

    public Message(String messageText, Long ownerId, Long chatId) {
        this.messageText = messageText;
        this.ownerId = ownerId;
        this.chatId = chatId;
    }
    public Message() {

    }
}
