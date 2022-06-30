package com.example.chatroom;

import java.io.*;
import com.example.chatroom.model.User;
import com.example.chatroom.tools.JDBCTool;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class LoginServer {
    public static void main(String[] args){
        try {
            ServerSocket server = new ServerSocket(1001);
            System.out.println("Server init");
            while(true){
                Socket socket = server.accept();
                System.out.println("Connection start");

                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String username = br.readLine();
                String password = br.readLine();

                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);

                String sql = "select * from user where name=? and password=?";
                User user = JDBCTool.getUser(sql, username, password);
                if(user==null){
                    pw.println("Connection failed");
                    pw.flush();
                    continue;
                }
                pw.println("Connected");
                pw.flush();
                System.out.println(username+" connected to server");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
