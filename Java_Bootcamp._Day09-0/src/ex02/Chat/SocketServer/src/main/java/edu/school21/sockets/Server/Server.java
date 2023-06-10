package edu.school21.sockets.Server;

import edu.school21.sockets.Service.RoomService;
import edu.school21.sockets.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class Server {
    private final UserService userService;
    private final RoomService roomService;
    private ServerSocket serverSocket;
    private Socket socket;
    private static List<ThreadConnection> connectionList = new LinkedList<>();


    @Autowired
    public Server(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Thread consoleThread = new Thread(this::readConsoleInput);
        consoleThread.start();
        try {
            while (true) {
                socket = serverSocket.accept();
                ThreadConnection connection =  new ThreadConnection(socket, userService, roomService);
                connectionList.add(connection);
                connection.start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }

    private void readConsoleInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String input = reader.readLine();
                if (input != null && input.equalsIgnoreCase("exit")) {
                    refuseAll();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private synchronized void refuseAll() {
        try {
            for (ThreadConnection connection : connectionList) {
                connection.refuseConnection();
            }
            serverSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
    static void sendMessageToChat(String message, String username, ThreadConnection connectionCurrent) throws IOException {
        Long chatId = connectionCurrent.getChatId();
        for(ThreadConnection connection : connectionList) {
            if(!connection.equals(connectionCurrent) &&
                    Objects.equals(connection.getChatId(), chatId)) {
                connection.sendMessageClient(username + ": " + message);
            }
        }
    }
    static void removeConnection(ThreadConnection connection) {
        connectionList.remove(connection);
    }
}
