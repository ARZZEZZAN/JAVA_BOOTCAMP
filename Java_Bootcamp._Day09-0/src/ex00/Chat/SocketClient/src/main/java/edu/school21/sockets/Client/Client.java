package edu.school21.sockets.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private BufferedReader reader;
    private Socket socket;
    public void start(int port) {
        try {
            socket = new Socket("localhost", port);
            try {
                reader = new BufferedReader(new InputStreamReader(System.in));
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                while(true) {
                    getMessageFromServer();
                    sendMessageToServer();
                }
            } finally {
                socket.close();
                reader.close();
                bufferedReader.close();
                bufferedWriter.close();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }
    private void sendMessageToServer() throws IOException {
        String message = reader.readLine();
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    private void getMessageFromServer() throws IOException {
        String serverMessage = bufferedReader.readLine();
        System.out.println(serverMessage);
        if(serverMessage.equals("Successful!")) {
            refuseConnection();
        }
        System.out.print("> ");
    }
    private void refuseConnection() {
        try {
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
}
