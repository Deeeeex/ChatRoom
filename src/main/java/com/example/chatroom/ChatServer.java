package com.example.chatroom;

import com.example.chatroom.thread.ChatThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    public static Map<String, Socket> socketMap = new HashMap<>();
    public static void main(String[] args){
        try {
            ServerSocket server = new ServerSocket(1002);
            while(true){
                System.out.println("Server init");
                Socket socket = server.accept();
                System.out.println("Connection start");

                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String username = br.readLine();
                System.out.println("username:" + username);
                socketMap.put(username, socket);
                System.out.println("socketMap:"+socketMap);
                OutputStream os_self = socket.getOutputStream();
                PrintWriter pw_self = new PrintWriter(os_self);
                pw_self.println("Online:"+socketMap.keySet());
                pw_self.flush();
                new ChatThread(username,socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
