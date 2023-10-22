package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ReviewInfomation;
import com.aishow.backend.models.ServiceInformation;

public class ServiceRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        return null;
    }

    /**
     * Passar esse metodo pro normal com o AUTH, passar o id vai ser desnecessario
     * @param params passar id no 0
     */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try{
        PreparedStatement st = StatementPreparer.getServiceById(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
        ResultSet rs = DatabaseConnection.runQuery(st);
        ServiceInformation toReturn = ServiceInformation.fromResultSet(rs);
        completeServiceInformaiton(toReturn);
        return (G) toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    //Adiciona reviews, availability e qqr outra coisa q possa vir a ser util no ServiceInformaion q ja tenho o basico
    public static void completeServiceInformaiton (ServiceInformation info) throws SQLException{
        PreparedStatement st = StatementPreparer.getAllReviewsToService(DatabaseConnection.getConnection(), info.getTemplateId());
        ArrayList<ReviewInfomation> reviews = new ArrayList<>();
        ResultSet rs = DatabaseConnection.runQuery(st);

        while (rs.next()){
            reviews.add(ReviewInfomation.fromResultSet(rs, 1));
        }

        info.setReviews(reviews);
        st = StatementPreparer.getAvailabilityByTemplateId(DatabaseConnection.getConnection(), info.getTemplateId());
        rs = DatabaseConnection.runQuery(st);
        info.setAvailablity(rs);
    }

    public static int findLastCreatedService(int creator){
        try {
            PreparedStatement st = StatementPreparer.getLastCreatedServiceByProviderId(DatabaseConnection.getConnection(), creator);
            ResultSet rs = DatabaseConnection.runQuery(st);
            if (rs.next())
                return rs.getInt(1);
            return -1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return -1;
        }
    }
}
