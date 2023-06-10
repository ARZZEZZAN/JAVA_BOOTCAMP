package edu.school21.sockets.Server;

import edu.school21.sockets.Model.User;
import edu.school21.sockets.Service.UserService;

import java.io.*;
import java.net.Socket;

public class ThreadConnection extends Thread {
    private Socket socket;
    private UserService userService;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private boolean isFinished;

    public ThreadConnection(Socket socket, UserService userService) throws IOException {
        this.socket = socket;
        this.userService = userService;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void run() {
        System.out.println("Client connected");
        try {
            while(!isFinished) {
                sendMessageClient("Hello from Server!");
                getClientAction();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }
    private void getClientAction() throws IOException {
        String clientWord = bufferedReader.readLine();
        if(clientWord.equalsIgnoreCase("signUp")) {
            String username;
            String password;
            sendMessageClient("Enter username:");
            username = bufferedReader.readLine();
            sendMessageClient("Enter password:");
            password = bufferedReader.readLine();
            User user = new User();
            user.setPassword(password);
            user.setLogin(username);
            userService.signUp(user);
            sendMessageClient("Successful!");
            refuseConnection();
        }
    }
    private void sendMessageClient(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    public void refuseConnection() {
        try {
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
       isFinished = true;
    }
}
