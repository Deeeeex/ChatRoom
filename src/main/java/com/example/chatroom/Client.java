package com.example.chatroom;

import com.example.chatroom.thread.ReadThread;
import com.example.chatroom.thread.WriteThread;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost",1001);
            Scanner sc = new Scanner(System.in);

            System.out.print("Your username:");
            String username = sc.nextLine();
            System.out.print("Your password:");
            String password = sc.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.println(username);
            pw.println(password);
            pw.flush();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String message = br.readLine();
            System.out.println("message:" + message);

            if(message.equals("Connected")){
                chat(username);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void chat(String username) {
        try {
            Socket socket = new Socket("localhost",1002);
            Scanner sc = new Scanner(System.in);


            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.println(username);
            pw.flush();

            //get online users
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String message = br.readLine();
            System.out.println(message);

            //分别创建读的线程
            new ReadThread(socket).start();
            System.out.println("Reader start");
            //创建写的线程
            new WriteThread(socket).start();
            System.out.println("Writer start");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
