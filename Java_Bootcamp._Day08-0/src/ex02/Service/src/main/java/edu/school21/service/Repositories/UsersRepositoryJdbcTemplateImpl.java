package edu.school21.service.Repositories;

import edu.school21.service.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("jdbcTemplateRepository")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private NamedParameterJdbcTemplate dataSource;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.dataSource = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        User user = dataSource.query("SELECT * FROM email.user WHERE id=:id",
                        new MapSqlParameterSource().addValue("id", id),
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        return user;
    }

    @Override
    public List findAll() {
        List<User> users = dataSource.query("SELECT * FROM email.user",
                new BeanPropertyRowMapper<>(User.class));
        return users.isEmpty() ? null : users;
    }

    @Override
    public void save(User entity) {
        dataSource.update("INSERT INTO email.user(id, email, password) VALUES(:id, :email, :password)",
                new MapSqlParameterSource()
                        .addValue("id", entity.getId())
                        .addValue("email", entity.getEmail())
                        .addValue("password", entity.getPassword()));
    }

    @Override
    public void update(User entity) {
        dataSource.update("UPDATE email.user SET email=:email, password=:password WHERE id=:id",
                new MapSqlParameterSource()
                        .addValue("email", entity.getEmail())
                        .addValue("id", entity.getId())
                        .addValue("password", entity.getPassword()));
    }

    @Override
    public void delete(Long id) {
        dataSource.update("DELETE FROM email.user WHERE id=:id",
                new MapSqlParameterSource()
                        .addValue("id", id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dataSource.query("SELECT * FROM email.user WHERE email=:email",
                        new MapSqlParameterSource().addValue("email", email),
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny();
    }
}
