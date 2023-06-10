package edu.school21.sockets.Repositories;

import edu.school21.sockets.Model.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message> {
    List<Message> loadMessages(Long chatId);
}
