package com.aishow.backend.managers;

import java.io.IOException;

import com.aishow.backend.modular.ImageHandler;

public class DatabaseManager extends Thread{
    int count = 0;
    
    public void run() {
        ImageHandler.GetBlobServiceClientTokenCredential();
        System.out.println("STORAGE ACCESS");
        while(true){
            try {
                DatabaseConnection.connect();
                System.out.println("New Connection");
                Thread.sleep(600000);
                count++;
                if (count == 10){
                    ImageHandler.GetBlobServiceClientTokenCredential();
                    count = 0;
                    System.out.println("STORAGE ACCESS");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
