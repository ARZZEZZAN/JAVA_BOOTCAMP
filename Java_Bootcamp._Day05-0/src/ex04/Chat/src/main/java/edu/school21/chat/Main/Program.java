package edu.school21.chat.Main;;


import edu.school21.chat.Chat.User;
import edu.school21.chat.Repository.UsersRepositoryJdbcImpl;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(DataSource.getDataSource());
        List<User> users = usersRepositoryJdbc.findAll(0,10);
        for (var user : users) {
            System.out.println(user);
            System.out.println();
        }
    }
}