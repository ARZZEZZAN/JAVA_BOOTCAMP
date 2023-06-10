package edu.school21.sockets.Service;

import edu.school21.sockets.Model.Message;
import edu.school21.sockets.Model.User;

public interface UserService {
    boolean signUp(String username, String password);
    Long signIn(String username, String password);
    void saveMessage(String message, Long userId);
}
