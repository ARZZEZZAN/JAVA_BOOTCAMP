package edu.school21.chat.Chat;

import java.util.ArrayList;
import java.util.Objects;

public class  ChatRoom {
    private Long id;
    private String chatName;
    private User chatOwner;
    private ArrayList<Message> messages;

    public ChatRoom(Long id, String chatName, User chatOwner, ArrayList<Message> messages) {
        this.id = id;
        this.chatName = chatName;
        this.chatOwner = chatOwner;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public User getChatOwner() {
        return chatOwner;
    }

    public void setChatOwner(User chatOwner) {
        this.chatOwner = chatOwner;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(id, chatRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", chatName='" + chatName + '\'' +
                ", chatOwnerId='" + chatOwner.getId() + '\'' +
                ", messages=" + messages +
                '}';
    }
}
