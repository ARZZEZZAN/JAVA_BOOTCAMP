package edu.school21.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedDataSourceTest {
    private DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.HSQL).
                addScript("schema.sql").
                addScript("data.sql").
                build();
    }

    @Test
    void checkConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
        }
    }
}
