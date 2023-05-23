package edu.school21.chat.Models.Main;

import edu.school21.chat.Models.Chat.User;

import java.sql.*;
public class Program {
    private static final String DB_URL = Application.get("db.url");
    private static final String DB_LOGIN = Application.get("db.login");
    private static final String DB_PASSWORD = Application.get("db.password");
    static {
        loadDriver();
    }
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD)) {
            PreparedStatement userStatement = connection.prepareStatement("SELECT userid, login, password, chatroomname FROM Chat.User " +
                    "JOIN chat.chatroom ON chatroomowner = Chat.user.userid");

            ResultSet resultSet = userStatement.executeQuery();
            while(resultSet.next()) {
                System.out.println(resultSet.getString("userid") + " " + resultSet.getString("login") + " " + resultSet.getString("chatroomname"));
            }
            resultSet.close();
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
}