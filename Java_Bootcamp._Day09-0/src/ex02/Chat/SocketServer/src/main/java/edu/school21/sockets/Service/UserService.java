package edu.school21.sockets.Service;

import edu.school21.sockets.Model.Message;
import edu.school21.sockets.Model.User;

import java.util.List;

public interface UserService {
    boolean signUp(String username, String password);
    Long signIn(String username, String password);
    void saveMessage(String message, Long userId, Long chatId);
    List<Message> loadMessages(Long chatId);
    String getUsername(Long userId);
}
