package edu.school21.chat.Main;;

import edu.school21.chat.Repository.MessagesRepository;
import edu.school21.chat.Repository.MessagesRepositoryJdbcImpl;

import java.sql.*;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ConnectionDB.connectToDb());
        String str = "";
        while(true) {
            System.out.println("Enter a message ID");
            System.out.print("-> ");
            str = scanner.nextLine();
            if(str.equals("exit")) {
                System.out.println("Bye, bye!!!!");
                System.exit(-1);
            }
            long id = Long.parseLong(str);
            messagesRepository.findById(id);
        }
    }
}