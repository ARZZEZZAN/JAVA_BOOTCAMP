package edu.school21.chat.Chat;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private ArrayList<ChatRoom> createdRooms;
    private ArrayList<ChatRoom> socializeRooms;


    public User(Long id, String login, String password, ArrayList<ChatRoom> createdRooms, ArrayList<ChatRoom> socializeRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.socializeRooms = socializeRooms;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<ChatRoom> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(ArrayList<ChatRoom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public ArrayList<ChatRoom> getSocializeRooms() {
        return socializeRooms;
    }

    public void setSocializeRooms(ArrayList<ChatRoom> socializeRooms) {
        this.socializeRooms = socializeRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms=" + createdRooms +
                ", socializeRooms=" + socializeRooms +
                '}';
    }
}
