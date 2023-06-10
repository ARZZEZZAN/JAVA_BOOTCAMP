package edu.school21.sockets.Repositories;

import edu.school21.sockets.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        createTable();
    }

    private void createTable() {
//        "drop schema if exists Chat cascade;\n" +
        jdbcTemplate.execute(
                "create schema if not exists Chat;\n" +
                "\n" +
                "CREATE TABLE if not exists Chat.User (\n" +
                "    Id SERIAL PRIMARY KEY,\n" +
                "    Login varchar(50) NOT NULL UNIQUE,\n" +
                "    Password VARCHAR(100) NOT NULL\n" +
                ");");
    }

    @Override
    public User findById(Long id) {
        User user = jdbcTemplate.query("SELECT * FROM chat.user WHERE id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM chat.user",
                new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public void save(User entity) {
        int result = jdbcTemplate.update("INSERT INTO chat.user(login, password) VALUES(?, ?)",
                entity.getLogin(),
                entity.getPassword());
        if(result == 0) {
            System.err.println("Entity hasn't saved");
        }
    }

    @Override
    public void update(User entity) {
        if(entity == null || entity.getId() == null) {
            System.err.println("Entity doesn't exist for these parameters");
        }

        int result = jdbcTemplate.update("UPDATE chat.user SET login = ?, password = ? WHERE id = ?",
                entity.getLogin(),
                entity.getPassword(),
                entity.getId());

        if(result == 0) {
            System.err.println("Entity hasn't downloaded");
        }
    }

    @Override
    public void delete(Long id) {
        int result = jdbcTemplate.update("DELETE FROM chat.user WHERE id = ?", id);
        if(result == 0) {
            System.err.println("Entity hasn't downloaded");
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        if(login == null || login.isEmpty()) {
            return Optional.empty();
        }
        Optional<User> user = Optional.ofNullable(jdbcTemplate.query("SELECT * FROM chat.user WHERE login=?",
                new Object[]{login},
                new int[]{Types.VARCHAR},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null));
        return user;
    }
}
