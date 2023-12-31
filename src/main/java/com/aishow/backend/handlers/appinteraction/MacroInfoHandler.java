package com.aishow.backend.handlers.appinteraction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.GenericInformation;
import com.aishow.backend.utils.Utils;

//TODO #30 revear as nomeações de colunas de nome nos statements e no servidor

public class MacroInfoHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        return null;
    }
    
    @Override
    public <T, G> G handle(T reqBody, String[] params){
        if (params[1].equals("all")){
            try{
                switch(params[0]){
                    case "areas":
                        return (G) sendInfos("area");
                    case "mod":
                        return (G) sendInfos("servicemodality");
                    case "cat":
                        return (G) sendInfos("servicecategory");
                    default:
                        return (G) "ERROR";
                } 
            } catch (IOException ex){
                return null;
            }
        } else {
            try{
                switch(params[0]){
                    case "areas":
                        return (G) sendSingleInfo("area",Integer.parseInt(params[2]));
                    case "mod":
                        return (G) sendSingleInfo("servicemodality",Integer.parseInt(params[2]));
                    case "cat":
                        return (G) sendSingleInfo("servicecategory",Integer.parseInt(params[2]));
                    default:
                        return (G) "ERROR";
                } 
            } catch (IOException ex){
                return null;
            }
        }
    }

    private ArrayList<GenericInformation> sendInfos(String type) throws IOException{
        try {
            PreparedStatement st = StatementPreparer.getAllGenericInformation(DatabaseConnection.getConnection(), type);
            ResultSet rs = DatabaseConnection.runQuery(st);
            ArrayList<GenericInformation> toReturn = new ArrayList<>();

            while (rs.next()){
                if (rs.getInt(1) == 99)
                    continue;
                toReturn.add(GenericInformation.fromResultSet(rs,type));
            }

            return toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private GenericInformation sendSingleInfo(String type, int id) throws IOException{
        try {
            PreparedStatement st = StatementPreparer.getGenericInformationById(DatabaseConnection.getConnection(), type,"id"+type ,id);
            ResultSet rs = DatabaseConnection.runQuery(st);
            if (rs.next())
                return GenericInformation.fromResultSet(rs,type);
            return null;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
}
