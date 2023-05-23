package edu.school21.chat.Main;;

import edu.school21.chat.Chat.ChatRoom;
import edu.school21.chat.Chat.Message;
import edu.school21.chat.Chat.User;
import edu.school21.chat.Exception.NotSavedSubEntityException;
import edu.school21.chat.Repository.MessagesRepository;
import edu.school21.chat.Repository.MessagesRepositoryJdbcImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(DataSource.getDataSource());
        try {
        // 1 test
            User creator = new User(1L, "Armen", "15061999", new ArrayList(), new ArrayList());
            User author = creator;
            ChatRoom room = new ChatRoom(1L, "1", creator, new ArrayList());
            Message message = new Message(null, author, room,  "Hello!", LocalDateTime.now());
            messagesRepository.saveMessage(message);
            System.out.println(message.getId()); // ex. id == 7
        }  catch (NotSavedSubEntityException nsse) {
            System.out.println(nsse);
        }
        // 2 test
        try {
            User author1 = new User(2L, "Temirkan", "LevaGeiSoglasen", new ArrayList(), new ArrayList());
            Message message1 = new Message(null, author1, null, "Hello!", LocalDateTime.now());
            messagesRepository.saveMessage(message1);
            System.out.println(message1.getId()); // exception
        }  catch (NotSavedSubEntityException nsse) {
            System.out.println(nsse);
        }
        // 3 test
        try {
            User creator2 = new User(1L, "Armen", "15061999", new ArrayList(), new ArrayList());
            User author2 = creator2;
            ChatRoom room2 = new ChatRoom(1L, "1", creator2, new ArrayList());
            Message message2 = new Message(null, author2, room2,  "Hello! I am Armen", LocalDateTime.now());
            messagesRepository.saveMessage(message2);
            System.out.println(message2.getId()); // ex. id == 9
        }  catch (NotSavedSubEntityException nsse) {
            System.out.println(nsse);
        }
        // 4 test
        try {
            User creator2 = new User(10L, "Armen", "15061999", new ArrayList(), new ArrayList());
            User author2 = creator2;
            ChatRoom room2 = new ChatRoom(1L, "1", creator2, new ArrayList());
            Message message2 = new Message(null, author2, room2,  "Hello! I am Armen", LocalDateTime.now());
            messagesRepository.saveMessage(message2);
            System.out.println(message2.getId()); // ex. id == 9
        }  catch (NotSavedSubEntityException nsse) {
            System.out.println(nsse);
        }
    }
}