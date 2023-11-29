package com.aishow.backend.handlers.appinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.GenericInformation;
import com.aishow.backend.models.ServiceInformation;
import com.aishow.backend.models.UserInformation;

public class SearchHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    //TODO #38 FAZER SEPAR COM OU SEM GEOLIMITAÇÃO
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        if (params[0].equals("service")){
            return(G) searchServices(params[1], Integer.parseInt(params[2]),true, Integer.parseInt(params[3]));
        } else if (params[0].equals("user")){
            return(G) searchUsers(params[1], Integer.parseInt(params[2]));
        } else {
            return null;
        }
    }

    private ArrayList<UserInformation> searchUsers(String query, int offset){
        try{
            ArrayList<UserInformation> toReturn = new ArrayList<>();
            PreparedStatement st = StatementPreparer.searchUsers(DatabaseConnection.getConnection(), query, offset);
            ResultSet rs = DatabaseConnection.runQuery(st);
            ResultSet areaRs;

            while (rs.next()){
                try{
                UserInformation buffer = UserInformation.fromResultSet(rs);
                areaRs = DatabaseConnection.runQuery(StatementPreparer.getGenericInformationById(
                    DatabaseConnection.getConnection(), "area", "idArea", buffer.getArea().Id));
                areaRs.next();
                buffer.setArea(GenericInformation.fromResultSet(areaRs, "area"));
                toReturn.add(buffer);
                } catch (Exception ex){
                    System.out.println("teve exeção em pesquisa");
                }
            }
            return toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private ArrayList<ServiceInformation> searchServices(String query, int offset, boolean geoLimit, int userIdArea){
        try{
            ArrayList<ServiceInformation> toReturn = new ArrayList<>();
            PreparedStatement st;
            if (geoLimit){
                st = StatementPreparer.searchTemplatesWithGeoLimitations(DatabaseConnection.getConnection(), query, offset,userIdArea);
            } else {
                st = StatementPreparer.searchTemplates(DatabaseConnection.getConnection(), query, offset);
            }
            System.out.println(st);
            ResultSet rs = DatabaseConnection.runQuery(st);
            ResultSet providerRs;

            System.out.println("VO AVERIGUAR");
            while (rs.next()){
                System.out.println("ACHEI UM");
                try{
                ServiceInformation buffer = ServiceInformation.fromResultSet(rs);
                providerRs = DatabaseConnection.runQuery(StatementPreparer.getUserById(
                    DatabaseConnection.getConnection(),buffer.getProvider().getUserId()));
                providerRs.next();
                buffer.setProvider(UserInformation.fromResultSet(providerRs));
                toReturn.add(buffer);
                } catch (Exception ex){
                    System.out.println("teve exeção em pesquisa");
                }
            }
            return toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
}
