package edu.school21.services;

import edu.school21.Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceImplTest {
        private static final User EXPECTED_FIND_BY_ID_LOGIN =  new User(1L, "arzzezzan", "15061999", false);
        private static final User EXPECTED_UPDATE_USER = new User(1L, "arzzezzan", "19082003", false);
        private EmbeddedDatabase dataSource;
        private UsersServiceImpl usersService;

        @BeforeEach
        public void init() throws SQLException {
            dataSource = new EmbeddedDatabaseBuilder().
                    addScript("schema1.sql").
                    addScript("data1.sql").
                    build();
            usersService = new UsersServiceImpl(dataSource);
        }
        @Test
        void checkConnection() throws SQLException {
                try (Connection connection = dataSource.getConnection()) {
                        assertNotNull(connection);
                }
        }
        @Test
        public void findLoginTest() {
                User user = usersService.findByLogin("arzzezzan");
                assertEquals(EXPECTED_FIND_BY_ID_LOGIN, user);
        }
        @Test
        public void updateTest() {
                usersService.update(new User(1L, "arzzezzan", "19082003", false));
                assertEquals(EXPECTED_UPDATE_USER, usersService.findByLogin("arzzezzan"));
        }
        @Test
        public void authenticateTest() {
                User user = new User(1L, "arzzezzan", "15061999", false);
                assertTrue(usersService.authenticate(user.getLogin(), user.getPassword()));
                assertThrows(AlreadyAuthenticatedException.class, () -> {
                        usersService.authenticate(user.getLogin(), user.getPassword());
                });
        }

        @AfterEach
        void close() {
                dataSource.shutdown();
        }
}
