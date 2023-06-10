package edu.school21.sockets.Server;

import edu.school21.sockets.Model.ChatRoom;
import edu.school21.sockets.Model.Message;
import edu.school21.sockets.Serialization.JSONConverter;
import edu.school21.sockets.Service.RoomService;
import edu.school21.sockets.Service.UserService;
import lombok.EqualsAndHashCode;

import java.io.*;
import java.net.Socket;
import java.util.List;

@EqualsAndHashCode
public class ThreadConnection extends Thread {
    private Socket socket;
    private UserService userService;
    private RoomService roomService;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private boolean isFinished;
    private Long chatId;
    private Long userId;


    public ThreadConnection(Socket socket, UserService userService, RoomService roomService) throws IOException {
        this.socket = socket;
        this.userService = userService;
        this.roomService = roomService;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void run() {
        System.out.println("Client connected");
        try {
            while(!isFinished) {
                serverMenu();
                getClientAction();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }

    private void serverMenu() throws IOException {
        sendMessageClient("Hello from Server! Make a suggestion for further action:");
        sendMessageClient("1. SignIn");
        sendMessageClient("2. SignUp");
        sendMessageClient("3. Exit");
    }

    private void getClientAction() throws IOException {
        String clientWord = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
        if (clientWord != null) {
            if (clientWord.equals("2")) {
                signUp();
            } else if (clientWord.equals("1")) {
                signIn();
            } else if (clientWord.equals("3")) {
                refuseConnection();
            }
        }
    }

    private void startMessaging() {
        try {
            sendMessageClient("Start messaging:");

            List<Message> messages = userService.loadMessages(chatId);
            for(Message message : messages) {
                String username = userService.getUsername(message.getOwnerId());
                sendMessageClient(username + ": " + message.getMessageText());
            }
            String message;
            while (!(message = JSONConverter.parseStringToObject(
                    bufferedReader.readLine()).getMessageText())
                    .equalsIgnoreCase("exit")) {
                userService.saveMessage(message, userId, chatId);
                Server.sendMessageToChat(message, username, this);
            }
            sendMessageClient("You have left the chat!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void signUp() {
        try {
            sendMessageClient("Enter username:");
            username = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
            sendMessageClient("Enter password:");
            String password = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if((username == null || username.isEmpty()) || (password == null || password.isEmpty())) {
                sendMessageClient("Wrong data for signUp");
            } else {
                if(!userService.signUp(username, password)) {
                    sendMessageClient("Failed! User with this login already exists");
                } else {
                    sendMessageClient("Successful!");
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void signIn() {
        try {
            sendMessageClient("Enter username:");
            username = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
            sendMessageClient("Enter password:");
            String password = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if((userId = userService.signIn(username, password)) != null) {
                sendMessageClient("Successful!");
                roomMenu();
            } else {
                sendMessageClient("Failed!");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void roomMenu() throws IOException {
        sendMessageClient("1. Create room");
        sendMessageClient("2. Choose room");
        sendMessageClient("3. Back");
        String clientWord = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
        if (clientWord != null) {
            if (clientWord.equals("1")) {
                createRoom();
            } else if (clientWord.equals("2")) {
                chooseRoomMenu();
            } else if (clientWord.equals("3")) {
                sendMessageClient("You have left the chat menu!");
            }
        }
    }

    private void createRoom() {
        try {
            sendMessageClient("Enter room name:");
            String roomName = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if (roomName == null || roomName.isEmpty()) {
                sendMessageClient("Wrong data for creating room");
                return;
            }
            sendMessageClient("Do you want to create a password for your room? Yes(y)/No(n)?");
            String clientWord = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if(clientWord.equalsIgnoreCase("Yes") || clientWord.equalsIgnoreCase("y")) {
                sendMessageClient("Enter room password:");
                String password = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
                if (password == null) {
                    sendMessageClient("Wrong password!");
                } else {
                    if(roomService.saveRoom(roomName, password, userId)) {
                        sendMessageClient("Successful!");
                        chooseRoomMenu();
                    } else {
                        sendMessageClient("Failed! Chat with this name already exists");
                    }
                }
            } else {
                if(roomService.saveRoom(roomName, userId)) {
                    sendMessageClient("Successful!");
                    chooseRoomMenu();
                } else {
                    sendMessageClient("Failed! Chat with this name already exists");
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    private void chooseRoomMenu() {
        try {
            int count = 0;
            List<ChatRoom> chatRoomList = roomService.findAll();
            if (chatRoomList == null) {
                sendMessageClient("There are no any rooms!");
            } else {
                sendMessageClient("Rooms:");
                for(ChatRoom room : chatRoomList) {
                    sendMessageClient(++count + ". " + room.getChatName());
                }
                sendMessageClient(++count + ". Back");
                int room = chooseRoom();
                if(room < 0 || room > count) {
                    sendMessageClient("Choose correct room");
                } else {
                    if(room == count) {
                        sendMessageClient("You have left the chat menu!");
                    } else {
                        ChatRoom chatRoom = chatRoomList.get(room - 1);
                        if(chatRoom.getPassword() != null) {
                            sendMessageClient("Enter password for chat:");
                            String password = JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText();
                            if(roomService.signIn(chatRoom.getId(), password)) {
                                sendMessageClient("Successful!");
                                startChatRoom((long) room, chatRoom);
                            } else {
                                sendMessageClient("Failed!");
                            }
                        } else {
                            startChatRoom((long) room, chatRoom);
                        }

                    }
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    private void startChatRoom(long room, ChatRoom chatRoom) throws IOException {
        chatId = chatRoom.getId();
        sendMessageClient(chatRoom.getChatName() + " --- ");
        startMessaging();
    }

    private int chooseRoom() {
        try {
            try {
                int clientWord = Integer.parseInt(JSONConverter.parseStringToObject(bufferedReader.readLine()).getMessageText());
                return clientWord;
            } catch (NumberFormatException exception) {
                System.out.println(exception.getMessage());
            }
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
        return -1;
    }

    void sendMessageClient(String message) throws IOException {
        try {
            String jsonMessage = JSONConverter.makeJSON(message);
            bufferedWriter.write(jsonMessage);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refuseConnection() {
        try {
            Server.removeConnection(this);
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
        isFinished = true;
    }
    public Long getChatId() {
        return chatId;
    }
}
