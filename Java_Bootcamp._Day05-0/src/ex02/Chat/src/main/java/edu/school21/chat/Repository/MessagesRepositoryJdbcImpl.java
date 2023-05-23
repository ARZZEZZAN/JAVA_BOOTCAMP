package edu.school21.chat.Repository;

import edu.school21.chat.Chat.ChatRoom;
import edu.school21.chat.Chat.Message;
import edu.school21.chat.Chat.User;
import edu.school21.chat.Exception.NotSavedSubEntityException;

import javax.sql.DataSource;
import java.sql.*;
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
                    null,
                    new ArrayList<>()
            );
            return chatRoom;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }
    @Override
    public void saveMessage(Message message) throws NotSavedSubEntityException {
        checkMassage(message);
        String localDateTime = "'" + Timestamp.valueOf(message.getDateTime()) + "'";
        try (Statement chatRoomStatement = connection.createStatement()) {
            Long userId = message.getMessageOwner().getId();
            Long roomId = message.getChatRoom().getId();
            ResultSet resultSet = chatRoomStatement.executeQuery("SELECT userid FROM chat.user WHERE userid = " + userId);
            if(!resultSet.next()) {
                throw new NotSavedSubEntityException("User id doesn't exist");
            }
            resultSet = chatRoomStatement.executeQuery("SELECT chatroomid FROM chat.chatroom WHERE chatroomid = " + roomId);
            if(!resultSet.next()) {
                throw new NotSavedSubEntityException("User id doesn't exist");
            }
            resultSet = chatRoomStatement.executeQuery("INSERT INTO chat.message(messageauthor, messageroom, messagetext, messagedate) VALUES (" +
                    userId + ", " + roomId + ", '" + message.getMessageText() + "', " + localDateTime + ") RETURNING messageid");
            resultSet.next();
            message.setId(resultSet.getLong(1));
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
    private void checkMassage(Message message) {
        if(message.getMessageOwner() == null) {
            throw new NotSavedSubEntityException("Owner hasn't supported");
        }
        if(message.getChatRoom() == null) {
            throw new NotSavedSubEntityException("ChatRoom hasn't supported");
        }
        if(message.getMessageText() == null || message.getMessageText().length() < 1) {
            throw new NotSavedSubEntityException("Message text hasn't supported");
        }
        if(message.getDateTime() == null) {
            throw new NotSavedSubEntityException("Date time hasn't supported");
        }
    }
}