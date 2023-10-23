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

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        if (params[0].equals("service")){
            return(G) searchServices(params[1], Integer.parseInt(params[2]));
        } else if (params[0].equals("users")){
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
                UserInformation buffer = UserInformation.fromResultSet(rs);
                areaRs = DatabaseConnection.runQuery(StatementPreparer.getGenericInformationById(
                    DatabaseConnection.getConnection(), "area", "idarea", buffer.getAreaCode()));
                areaRs.next();
                buffer.setArea(GenericInformation.fromResultSet(areaRs, "area"));
                toReturn.add(buffer);
            }
            return toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private ArrayList<ServiceInformation> searchServices(String query, int offset){
        try{
            ArrayList<ServiceInformation> toReturn = new ArrayList<>();
            PreparedStatement st = StatementPreparer.searchTemplates(DatabaseConnection.getConnection(), query, offset);
            ResultSet rs = DatabaseConnection.runQuery(st);
            ResultSet providerRs;

            while (rs.next()){
                ServiceInformation buffer = ServiceInformation.fromResultSet(rs);
                providerRs = DatabaseConnection.runQuery(StatementPreparer.getUserById(
                    DatabaseConnection.getConnection(),buffer.getProviderId()));
                providerRs.next();
                buffer.setProvider(UserInformation.fromResultSet(providerRs));
                toReturn.add(buffer);
            }
            return toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
}
