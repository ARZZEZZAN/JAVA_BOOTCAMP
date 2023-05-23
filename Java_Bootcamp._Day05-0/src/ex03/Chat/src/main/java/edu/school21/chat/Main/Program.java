package edu.school21.chat.Main;;

import edu.school21.chat.Chat.Message;
import edu.school21.chat.Chat.User;
import edu.school21.chat.Exception.NotSavedSubEntityException;
import edu.school21.chat.Repository.MessagesRepository;
import edu.school21.chat.Repository.MessagesRepositoryJdbcImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        // test 1
        try {
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(DataSource.getDataSource());
            Optional<Message> messageOptional = messagesRepository.findById(1L);
            if (messageOptional.isPresent()) {
                Message message = messageOptional.get();
                message.setMessageText("Vayaaaaaaa");
                message.setDateTime(LocalDateTime.now());
                message.setMessageOwner(new User(19L, "", "", null, null));
                messagesRepository.updateMessage(message);
            }
        } catch (NotSavedSubEntityException notSavedSubEntityException) {
            System.out.println(notSavedSubEntityException);
        }
        // test 2
        try {
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(DataSource.getDataSource());
            Optional<Message> messageOptional = messagesRepository.findById(1L);
            if (messageOptional.isPresent()) {
                Message message = messageOptional.get();
                message.setMessageText("Здравствуйте, я продаю кузю лакомки!");
                message.setDateTime(LocalDateTime.now());
                messagesRepository.updateMessage(message);
            }
        } catch (NotSavedSubEntityException notSavedSubEntityException) {
            System.out.println(notSavedSubEntityException);
        }
        // test 3
        try {
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(DataSource.getDataSource());
            Optional<Message> messageOptional = messagesRepository.findById(2L);
            if (messageOptional.isPresent()) {
                Message message = messageOptional.get();
                message.setMessageText("Я перекупщик!");
                message.setDateTime(LocalDateTime.now());
                messagesRepository.updateMessage(message);
            }
        } catch (NotSavedSubEntityException notSavedSubEntityException) {
            System.out.println(notSavedSubEntityException);
        }
    }
}