package com.example.chatroom.thread;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread{
    private Socket socket;
    public WriteThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        while(true){
            OutputStream os = null;
            try {
                os = this.socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                Scanner sc = new Scanner(System.in);
                String message = sc.nextLine();
                pw.println(message);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
