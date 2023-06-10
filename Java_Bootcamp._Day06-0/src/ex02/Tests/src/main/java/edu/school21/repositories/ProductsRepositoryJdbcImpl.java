package edu.school21.repositories;

import edu.school21.Model.Product;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private static final String SQL_ALL = "SELECT * FROM shop.product";
    private static final String SQL_BYID = "SELECT * FROM shop.product WHERE product.id=";
    private static final String SQL_UPDATE = "UPDATE shop.product SET name = ?, price = ? WHERE id = ?";
    private static final String SQL_SAVE = "INSERT INTO shop.product(NAME, PRICE) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM shop.product WHERE id = ";
    private Connection connection;
    public ProductsRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public List<Product> findAll() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_ALL)) {
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_BYID + id)) {
            Product product;
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return Optional.empty();
            }
            product = new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getLong("price"));
            return Optional.of(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE + id)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
