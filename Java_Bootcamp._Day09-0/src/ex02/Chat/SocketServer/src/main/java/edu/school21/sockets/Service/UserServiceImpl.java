package edu.school21.sockets.Service;

import edu.school21.sockets.Model.ChatRoom;
import edu.school21.sockets.Model.Message;
import edu.school21.sockets.Model.User;
import edu.school21.sockets.Repositories.ChatRoomsRepository;
import edu.school21.sockets.Repositories.MessageRepository;
import edu.school21.sockets.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private MessageRepository messageRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository, MessageRepository messageRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageRepository = messageRepository;
    }
    @Override
    public boolean signUp(String username, String password) {
        User user = new User();
        user.setPassword(password);
        user.setLogin(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(!usersRepository.findByLogin(user.getLogin()).isEmpty()) {
            return false;
        }
        usersRepository.save(user);
        return true;
    }

    @Override
    public Long signIn(String username, String password) {
        Optional<User> user = usersRepository.findByLogin(username);
        if(user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get().getId();
        }
        return null;
    }

    @Override
    public void saveMessage(String messageText, Long userId, Long chatId) {
        Message message = new Message(messageText, userId, chatId);
        messageRepository.save(message);
    }

    @Override
    public List<Message> loadMessages(Long chatId) {
        List<Message> messages = messageRepository.loadMessages(chatId);
        return messages;
    }

    @Override
    public String getUsername(Long userId) {
        User user = usersRepository.findById(userId);
        return user.getLogin();
    }


}
