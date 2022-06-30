package com.example.chatroom.thread;

import com.example.chatroom.ChatServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import com.example.chatroom.ChatServer;

public class ChatThread extends Thread{
    String username;
    Socket socket;
    public ChatThread(String username, Socket socket){
        this.username = username;
        this.socket = socket;
    }
    public void run(){
        System.out.println("Thread init");
        while(true){
            InputStream is = null;
            Map<String, Socket> socketMap = ChatServer.socketMap;
            Set<String> keySet = socketMap.keySet();
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.addAll(keySet);
            OutputStream os_self = null;
            try {
                os_self = this.socket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter pw_self = null;
            if(os_self != null) {
                pw_self = new PrintWriter(os_self);
            }

            try {

                is = this.socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String message = br.readLine();
                System.out.println("message:"+this.username+"->"+message);
                String[] values = message.split("->");
                // judge: group chat or private chat
                if(values.length == 1){
                    for(String username:keySet){
                        Socket socket2 = socketMap.get(username);
                        if(this.socket!=socket2){
                            OutputStream os = socket2.getOutputStream();
                            PrintWriter pw = new PrintWriter(os);
                            pw.println("(Group)"+this.username+":"+message);
                            pw.flush();
                        }
                    }
                }
                else{
                    String username = values[0];
                    try{
                        Socket socket2 = socketMap.get(username);
                        OutputStream os = socket2.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);
                        pw.println("(Private)"+this.username+":"+values[1]);
                        pw.flush();
                    }catch (Exception e){
                        if(pw_self!=null) {
                            pw_self.println("User not exists or User offline");
                            pw_self.flush();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
