package edu.school21.sockets.Repositories;

import edu.school21.sockets.Model.ChatRoom;
import edu.school21.sockets.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class ChatRoomsRepositoryImpl implements ChatRoomsRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ChatRoomsRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        createTable();
    }
    private void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Chat.ChatRoom (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    chatName varchar(50) NOT NULL UNIQUE,\n" +
                "    chatOwner INT NOT NULL,\n" +
                "    password varchar(100) default null,\n" +
                "    foreign key (chatOwner) references Chat.User(id)\n" +
                ");");
    }
    @Override
    public ChatRoom findById(Long id) {
        ChatRoom chatRoom = jdbcTemplate.query("SELECT * FROM chat.chatroom WHERE id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(ChatRoom.class)).stream().findAny().orElse(null);
        return chatRoom;
    }

    @Override
    public List<ChatRoom> findAll() {
        List<ChatRoom> chatRooms = jdbcTemplate.query("SELECT * FROM chat.chatroom",
                new BeanPropertyRowMapper<>(ChatRoom.class));
        return chatRooms;
    }

    @Override
    public void save(ChatRoom entity) {
        int result = jdbcTemplate.update("INSERT INTO chat.chatroom(chatName, chatOwner, password) VALUES(?, ?, ?)",
                entity.getChatName(),
                entity.getChatOwner(),
                entity.getPassword());
        if(result == 0) {
            System.err.println("Entity hasn't saved");
        }
    }

    @Override
    public void update(ChatRoom entity) {
        if(entity == null || entity.getId() == null) {
            System.err.println("Entity doesn't exist for these parameters");
        }

        int result = jdbcTemplate.update("UPDATE chat.chatroom SET chatName = ?, chatOwner = ?, password = ? WHERE id = ?",
                entity.getChatName(),
                entity.getChatOwner(),
                entity.getPassword(),
                entity.getId());

        if(result == 0) {
            System.err.println("Entity hasn't downloaded");
        }
    }

    @Override
    public void delete(Long id) {
        int result = jdbcTemplate.update("DELETE FROM chat.chatroom WHERE id = ?", id);
        if(result == 0) {
            System.err.println("Entity hasn't downloaded");
        }
    }

    @Override
    public Optional<ChatRoom> findByName(String name) {
        if(name == null || name.isEmpty()) {
            return Optional.empty();
        }
        Optional<ChatRoom> chatRoom = Optional.ofNullable(jdbcTemplate.query("SELECT * FROM chat.chatroom WHERE chatname=?",
                new Object[]{name},
                new int[]{Types.VARCHAR},
                new BeanPropertyRowMapper<>(ChatRoom.class)).stream().findAny().orElse(null));
        return chatRoom;
    }
}
