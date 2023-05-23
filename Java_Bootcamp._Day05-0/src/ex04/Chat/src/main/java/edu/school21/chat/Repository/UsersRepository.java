package edu.school21.chat.Repository;

import edu.school21.chat.Chat.User;

import java.sql.SQLException;
import java.util.List;

public interface UsersRepository {
    List<User> findAll(int page, int size) throws SQLException;
}
