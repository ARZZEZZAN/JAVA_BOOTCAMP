package edu.school21.chat.Repository;

import edu.school21.chat.Chat.ChatRoom;
import edu.school21.chat.Chat.Message;
import edu.school21.chat.Chat.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private static Connection connection;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        MessagesRepositoryJdbcImpl.connection = dataSource.getConnection();
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        try(PreparedStatement userStatement = connection.prepareStatement("SELECT * FROM chat.message WHERE messageid = " + id);
            ResultSet resultSet = userStatement.executeQuery()) {
            resultSet.next();
            String date = resultSet.getTimestamp(5).toString().split("\\.")[0];
            System.out.println("Message : {\n" +
                    "  id=" + resultSet.getInt(1) + ",\n" +
                    "  " + findUserById(resultSet.getLong(2)) + ",\n" +
                    "  " + findRoomById(resultSet.getLong(3)) + ",\n" +
                    "  text=\"" + resultSet.getString(4) + "\",\n" +
                    "  dateTime=" + date + "\n" +
                    "}");
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return Optional.empty();
    }

    private User findUserById(Long id) throws SQLException {
        try(PreparedStatement userStatement = connection.prepareStatement("SELECT * FROM chat.user WHERE userid = " + id);
            ResultSet resultSet = userStatement.executeQuery()) {
            resultSet.next();
           User user = new User(resultSet.getLong(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   new ArrayList<>(),
                   new ArrayList<>()
                   );
           return user;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }
    private ChatRoom findRoomById(Long id) throws SQLException {
        try(PreparedStatement chatRoomStatement = connection.prepareStatement("SELECT * FROM chat.chatroom WHERE chatroomid = " + id);
            ResultSet resultSet = chatRoomStatement.executeQuery()) {
            resultSet.next();
            ChatRoom chatRoom = new ChatRoom(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    new ArrayList<>()
            );
            return chatRoom;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }
}
