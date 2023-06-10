package edu.school21.service.Repositories;

import edu.school21.service.Models.User;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("jdbcRepository")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM email.user WHERE id=" + id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return null;
            }
            return new User(resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List findAll() {
        try(Connection connection = dataSource.getConnection()) {
            List<User> users = new ArrayList<>();
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM email.user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                users.add(new User(resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")));
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void save(User entity) {
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("INSERT INTO email.user(id, email, password) VALUES (" +
                    entity.getId() + ", '" +
                    entity.getEmail() + "', '" +
                    entity.getPassword() + "')");
            if(result == 0) {
                throw new SQLException("Entity hasn't saved successfully");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void update(User entity) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE email.user SET email = ?, password = ? WHERE id = ?");
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setLong(3, entity.getId());
            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                throw new SQLException("Entity hasn't updated successfully");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void delete(Long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM email.user WHERE id = " + id);
            int result = preparedStatement.executeUpdate();
            if(result == 0) {
                throw new SQLException("Entity hasn't deleted successfully");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM email.user WHERE email='"
                    + email +  "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(new User(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }
}
