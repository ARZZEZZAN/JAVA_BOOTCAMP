package edu.school21.services;

import edu.school21.Model.User;
import edu.school21.repositories.ProductsRepositoryJdbcImpl;
import edu.school21.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class EntityNotFoundException extends IllegalArgumentException {}
class AlreadyAuthenticatedException extends RuntimeException {}

public class UsersServiceImpl implements UsersRepository {
    private static final String SQL_FIND = "SELECT * FROM service.user WHERE login=?";
    private static final String SQL_UPDATE = "UPDATE service.user SET login=?, password=?, authenticated=? WHERE id=?";
    private Connection connection;

    public UsersServiceImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }
    @Override
    public User findByLogin(String login) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                throw new EntityNotFoundException();
            }
            User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("authenticated")
            );
            return user;
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }

    @Override
    public void update(User user) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isAuthentication());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.execute();
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
    public boolean authenticate(String login, String password) {
        User user = findByLogin(login);
        if(user.getPassword().equals(password)) {
            if(user.isAuthentication()) {
                throw new AlreadyAuthenticatedException();
            }
            user.setAuthentication(true);
            update(user);
            return user.isAuthentication();
        }
        return false;
    }
}
