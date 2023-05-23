package edu.school21.chat.Repository;

import edu.school21.chat.Chat.Message;
import edu.school21.chat.Exception.NotSavedSubEntityException;

import java.sql.SQLException;
import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(Long id) throws SQLException;
    void saveMessage(Message message) throws NotSavedSubEntityException;
    void updateMessage(Message message);
}
