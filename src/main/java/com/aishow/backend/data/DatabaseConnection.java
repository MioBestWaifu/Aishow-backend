package com.aishow.backend.data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.aishow.backend.models.*;
import com.aishow.backend.utils.Utils;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

public abstract class DatabaseConnection {
    private static Connection conn;
    private static ArrayList<Integer> serviceIds;
    public static void connect() throws IOException{
        //BufferedReader txtReader = new BufferedReader(new InputStreamReader(DatabaseConnection.class.getResourceAsStream("../modular/conninfo.txt")));
        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/aluguel";
        String username = "root";
        String serverPassword = "uiharu";
        try {
            conn = DriverManager.getConnection(url, username, serverPassword);
        } catch (SQLException ex) {
            System.out.println("Conexão merda");
            System.out.println(ex.getMessage());
            return;
        }
        serviceIds = new ArrayList<>();
        try{
        var st = conn.prepareStatement("SELECT idServiceTemplates FROM servicetemplates");
        var res = st.executeQuery();
        while(res.next()){
            serviceIds.add(res.getInt(1));
        }
        } catch (SQLException ex){
            System.out.println("Init falhou");
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    public static ResultSet runQuery(PreparedStatement st) throws SQLException{
        return st.executeQuery();
    }

    public static int runUpdate(PreparedStatement st) throws SQLException{
        return st.executeUpdate();
    }

    public static Connection getConnection(){
        return conn;
    }

    public static ArrayList<Integer> getAvailableServiceIds(){
        ArrayList<Integer> available = new ArrayList<>(serviceIds.size());
        for(int i=0;i<serviceIds.size();i++){
            available.add(i);
        }
        Collections.copy(available, serviceIds);
        return available;
    }

    /* public static boolean IsOwner(int provider, int template){
        try{
            var st = conn.prepareStatement("SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ? AND idServiceTemplates = ?");
            st.setInt(1, provider);
            st.setInt(2, template);
            return st.executeQuery().next();
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM IsOwner");
            return false;
        }
    }*/

}