package edu.school21.chat.Chat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User messageOwner;
    private ChatRoom chatRoom;
    private String messageText;
    private LocalDateTime dateTime;

    public Message(Long id, User messageOwner, ChatRoom chatRoom, String messageText, LocalDateTime dateTime) {
        this.id = id;
        this.messageOwner = messageOwner;
        this.chatRoom = chatRoom;
        this.messageText = messageText;
        this.dateTime = dateTime;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMessageOwner() {
        return messageOwner;
    }

    public void setMessageOwner(User messageOwner) {
        this.messageOwner = messageOwner;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageOwner='" + messageOwner + '\'' +
                ", chatRoom=" + chatRoom +
                ", messageText='" + messageText + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
