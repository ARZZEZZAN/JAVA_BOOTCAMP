package edu.school21.repositories;

import edu.school21.Model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ProductsRepositoryJdbcImplTest {
    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "Meat", 350L),
            new Product(2L, "Milk", 70L),
            new Product(3L, "Spaghetti", 130L)

    );
    private static final Product EXPECTED_FIND_BY_ID_PRODUCT =  new Product(2L, "Milk", 70L);
    private static final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "Milk", 75L);
    private static final Product EXPECTED_SAVE_PRODUCT = new Product(4L, "Water", 55L);
    private EmbeddedDatabase dataSource;
    private ProductsRepositoryJdbcImpl productsRepositoryJdbc;

    @BeforeEach
    public void init() throws SQLException {
        dataSource = new EmbeddedDatabaseBuilder().
                addScript("schema.sql").
                addScript("data.sql").
                build();
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void testForAll() {
        List<Product> actual = productsRepositoryJdbc.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, actual);
    }

    @Test
    public void testForId() {
        assertEquals(productsRepositoryJdbc.findById(2L).get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    public void updateTest() {
        productsRepositoryJdbc.update(new Product(2L, "Milk", 75L));
        assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepositoryJdbc.findById(2L).get());
    }
    @Test
    public void saveTest() {
        productsRepositoryJdbc.save(new Product(4L, "Water", 55L));
        assertEquals(EXPECTED_SAVE_PRODUCT, productsRepositoryJdbc.findById(4L).get());
    }
    @Test
    public void deleteTest() {
        productsRepositoryJdbc.delete(1L);
        assertFalse(productsRepositoryJdbc.findById(1L).isPresent());
    }

    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}
