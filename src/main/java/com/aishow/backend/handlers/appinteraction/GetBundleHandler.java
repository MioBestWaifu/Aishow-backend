package com.aishow.backend.handlers.appinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.GenericInformation;
import com.aishow.backend.models.ServiceInformation;
import com.aishow.backend.models.UserInformation;

public class GetBundleHandler extends BaseHandler{

    // TODO #27 Pegar available de algum outro lugar, nao deixar na dbconection
    @Override
    public <T, G> G handle(T reqBody) {
        try{
            Integer[] alreadyHas = (Integer[]) reqBody;
            int[] toGet = new int[4];
            ArrayList<Integer> available = DatabaseConnection.getAvailableServiceIds();
            available.removeAll(Arrays.asList(alreadyHas));
            Random random = new Random();
            ArrayList<ServiceInformation> bundleServInfos = new ArrayList<>();
            ResultSet rs;
            int x;

            if (available.size() < 4)
                return null;

            for(int i = 0; i<=3; i++){
                x = random.nextInt(available.size());
                toGet[i] = available.get(x);
                available.remove(x);
            } 

            for (int i = 0; i<=3; i++){
                rs = DatabaseConnection.runQuery(StatementPreparer.getServiceById(DatabaseConnection.getConnection(), toGet[i]));
                rs.next();
                bundleServInfos.add(ServiceInformation.fromResultSet(rs));
                
                rs = DatabaseConnection.runQuery(StatementPreparer.getUserById(DatabaseConnection.getConnection(), bundleServInfos.get(i).getProvider().getUserId()));
                rs.next();
                bundleServInfos.get(i).setProvider(UserInformation.fromResultSet(rs));

                rs = DatabaseConnection.runQuery(StatementPreparer.getGenericInformationById(DatabaseConnection.getConnection(), "area", "idArea", bundleServInfos.get(i).getProvider().getArea().Id));
                rs.next();
                
                bundleServInfos.get(i).getProvider().setArea(GenericInformation.fromResultSet(rs, "area"));
            }

            return (G) bundleServInfos;

        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
