package edu.school21.sockets.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ChatRoom {
    private Long id;
    private String chatName;
    private Long chatOwner;
    private String password;

    public ChatRoom(String chatName, Long chatOwner) {
        this.chatName = chatName;
        this.chatOwner = chatOwner;
    }
    public ChatRoom(String chatName, Long chatOwner, String password) {
       this(chatName, chatOwner);
        this.password = password;
    }
    public ChatRoom() {

    }
}
