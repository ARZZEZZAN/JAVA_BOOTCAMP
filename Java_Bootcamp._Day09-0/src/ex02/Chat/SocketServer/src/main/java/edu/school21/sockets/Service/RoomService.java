package edu.school21.sockets.Service;

import edu.school21.sockets.Model.ChatRoom;

import java.util.List;

public interface RoomService {
    boolean saveRoom(String name, Long ownerId);
    boolean saveRoom(String name, String password, Long ownerId);
    boolean signIn(Long id, String password);
    List<ChatRoom> findAll();
}
