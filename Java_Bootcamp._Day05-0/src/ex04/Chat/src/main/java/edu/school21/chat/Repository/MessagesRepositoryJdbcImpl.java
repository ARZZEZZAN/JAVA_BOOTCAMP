package edu.school21.chat.Repository;

import edu.school21.chat.Chat.ChatRoom;
import edu.school21.chat.Chat.Message;
import edu.school21.chat.Chat.User;
import edu.school21.chat.Exception.NotSavedSubEntityException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private static Connection connection;
    private final String queryUser = "SELECT * FROM chat.user WHERE userid = ";
    private final String queryRoom = "SELECT * FROM chat.chatroom WHERE chatroomid = ";
    private static final String SQL_UPDATE = "UPDATE chat.message SET messageauthor=?, messageroom=?, messagetext=?, messagedate=? WHERE messageid=?";

    public MessagesRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        MessagesRepositoryJdbcImpl.connection = dataSource.getConnection();
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        String queryMessage = "SELECT * FROM chat.message WHERE messageid = ";
        try(PreparedStatement userStatement = connection.prepareStatement(queryMessage + id);
            ResultSet resultSet = userStatement.executeQuery()) {
            resultSet.next();
            LocalDateTime date = null;
            if(resultSet.getTimestamp(5) != null) {
                date = resultSet.getTimestamp(5).toLocalDateTime();
            }
            System.out.println("Message : {\n" +
                    "  id=" + resultSet.getInt(1) + ",\n" +
                    "  " + findUserById(resultSet.getLong(2)) + ",\n" +
                    "  " + findRoomById(resultSet.getLong(3)) + ",\n" +
                    "  text=\"" + resultSet.getString(4) + "\",\n" +
                    "  dateTime=" + date + "\n" +
                    "}");
            return Optional.of(new Message(resultSet.getLong(1), findUserById(resultSet.getLong(2)), findRoomById(resultSet.getLong(3)),
                    resultSet.getString(4),  date));
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return Optional.empty();
    }
    private User findUserById(Long id) throws SQLException {
        try(PreparedStatement userStatement = connection.prepareStatement(queryUser + id);
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
        try(PreparedStatement chatRoomStatement = connection.prepareStatement(queryRoom + id);
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
            ResultSet  resultSet = chatRoomStatement.executeQuery("INSERT INTO chat.message(messageauthor, messageroom, messagetext, messagedate) VALUES (" +
                    userId + ", " + roomId + ", '" + message.getMessageText() + "', " + localDateTime + ") RETURNING messageid");
            resultSet.next();
            message.setId(resultSet.getLong(1));
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
    @Override
    public void updateMessage(Message message) {
        Long messageId = message.getId();
        checkMassage(message);
        Long userId = message.getMessageOwner().getId();
        Long roomId = message.getChatRoom().getId();
        Timestamp localDateTime = Timestamp.valueOf(message.getDateTime());
        String messageText = message.getMessageText();
        try (PreparedStatement chatRoomStatement = connection.prepareStatement(SQL_UPDATE)) {
            chatRoomStatement.setLong(1, userId);
            chatRoomStatement.setLong(2, roomId);
            chatRoomStatement.setString(3, messageText);
            chatRoomStatement.setTimestamp(4, localDateTime);
            chatRoomStatement.setLong(5, messageId);
            chatRoomStatement.executeUpdate();
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
        try (Statement chatRoomStatement = connection.createStatement()) {
            Long userId = message.getMessageOwner().getId();
            Long roomId = message.getChatRoom().getId();
            ResultSet resultSet = chatRoomStatement.executeQuery(queryUser + userId);
            if(!resultSet.next()) {
                throw new NotSavedSubEntityException("User id doesn't exist");
            }
            resultSet = chatRoomStatement.executeQuery(queryRoom + roomId);
            if(!resultSet.next()) {
                throw new NotSavedSubEntityException("User id doesn't exist");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }
}