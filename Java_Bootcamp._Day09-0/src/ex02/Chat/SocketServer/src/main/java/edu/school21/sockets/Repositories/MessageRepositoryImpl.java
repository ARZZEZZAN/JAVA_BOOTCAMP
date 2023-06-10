package edu.school21.sockets.Repositories;

import edu.school21.sockets.Model.Message;
import edu.school21.sockets.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component
public class MessageRepositoryImpl implements MessageRepository {

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        createTable();
    }

    private void createTable() {
        jdbcTemplate.execute("CREATE TABLE if not exists Chat.Message (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    ownerId INT NOT NULL,\n" +
                "    chatId INT NOT NULL,\n" +
                "    messageText text NOT NULL,\n" +
                "    dateTime timestamp default CURRENT_TIMESTAMP,\n" +
                "    foreign key (ownerId) references Chat.User(id),\n" +
                "    foreign key (chatId) references Chat.ChatRoom(id)\n" +
                ");");
    }

    @Override
    public Message findById(Long id) {
        Message message = jdbcTemplate.query("SELECT * FROM chat.message WHERE id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Message.class)).stream().findAny().orElse(null);
        return message;
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = jdbcTemplate.query("SELECT * FROM chat.message",
                new BeanPropertyRowMapper<>(Message.class));
        return messages;
    }

    @Override
    public void save(Message entity) {
        int result = jdbcTemplate.update("INSERT INTO chat.message(ownerId, messageText, chatId) VALUES(?, ?, ?)",
                entity.getOwnerId(),
                entity.getMessageText(),
                entity.getChatId());
        if(result == 0) {
            System.err.println("Entity hasn't saved");
        }
    }

    @Override
    public void update(Message entity) {
        if(entity == null || entity.getId() == null) {
            System.err.println("Entity doesn't exist for these parameters");
        }

        int result = jdbcTemplate.update("UPDATE chat.message SET ownerId = ?, messageText = ?, dateTime = ?, chatId = ? WHERE id = ?",
                entity.getOwnerId(),
                entity.getMessageText(),
                entity.getDateTime(),
                entity.getChatId(),
                entity.getId());

        if(result == 0) {
            System.err.println("Entity hasn't downloaded");
        }
    }

    @Override
    public void delete(Long id) {
        int result = jdbcTemplate.update("DELETE FROM chat.message WHERE id = ?", id);
        if(result == 0) {
            System.err.println("Entity hasn't downloaded");
        }
    }

    @Override
    public List<Message> loadMessages(Long chatId) {
        List<Message> messages = jdbcTemplate.query("SELECT * FROM chat.message WHERE chatId = ? LIMIT 30",
                new Object[]{chatId},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Message.class));
        return messages;
    }
}
