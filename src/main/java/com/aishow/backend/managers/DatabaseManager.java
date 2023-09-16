package com.aishow.backend.managers;

import java.io.IOException;

public class DatabaseManager extends Thread{
    
    public void run() {
        while(true){
            try {
                DatabaseConnection.connect();
                System.out.println("New Connection");
                Thread.sleep(600000);
                //Thread.sleep(7200000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
