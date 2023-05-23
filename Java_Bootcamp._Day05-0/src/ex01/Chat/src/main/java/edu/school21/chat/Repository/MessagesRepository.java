package edu.school21.chat.Repository;

import edu.school21.chat.Chat.Message;

import java.sql.SQLException;
import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(Long id) throws SQLException;
}
