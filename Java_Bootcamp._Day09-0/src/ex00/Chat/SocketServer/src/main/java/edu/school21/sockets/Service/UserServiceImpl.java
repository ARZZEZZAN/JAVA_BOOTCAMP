package edu.school21.sockets.Service;

import edu.school21.sockets.Model.User;
import edu.school21.sockets.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
}
