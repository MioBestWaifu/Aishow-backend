package com.aishow.backend.managers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.modular.ImageHandler;

import io.netty.handler.codec.http.HttpContentEncoder.Result;

public class DatabaseManager extends Thread{
    int count = 0;
    
    public void run() {
        ImageHandler.GetBlobServiceClientTokenCredential();
        System.out.println("STORAGE ACCESS");
        while(true){
            try {
                DatabaseConnection.connect();
                System.out.println("New Connection");
                System.out.println(completeInstances());
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

    //uses setDueInstancesAsCompleted() from StatementPreparer to set all due instances as completed
    public static int completeInstances(){
        try{
        PreparedStatement st = StatementPreparer.setDueInstancesAsCompleted(DatabaseConnection.getConnection());
        return DatabaseConnection.runUpdate(st);
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
