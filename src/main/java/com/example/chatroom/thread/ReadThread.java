package com.example.chatroom.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread{
    private Socket socket;
    public ReadThread(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run(){
        while(true){
            InputStream is = null;
            try {
                is = this.socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line  = br.readLine();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
