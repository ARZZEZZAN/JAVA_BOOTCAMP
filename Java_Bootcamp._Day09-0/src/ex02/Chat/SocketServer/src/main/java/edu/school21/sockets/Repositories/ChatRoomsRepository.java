package edu.school21.sockets.Repositories;

import edu.school21.sockets.Model.ChatRoom;
import edu.school21.sockets.Model.User;

import java.util.Optional;

public interface ChatRoomsRepository extends CrudRepository<ChatRoom> {
    Optional<ChatRoom> findByName(String name);
}
