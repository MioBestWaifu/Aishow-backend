package com.aishow.backend.handlers.appinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ReviewInformation;
import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

public class ReviewRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        if (params[0].equals("users")){
            return (G) getUserReviews(Integer.parseInt(params[1]));
        } else {
            return (G) getServiceReviews(Integer.parseInt(params[1]));
        }
    }

    private ArrayList<ReviewInformation> getServiceReviews(int parseInt) {
        try{
            PreparedStatement st = StatementPreparer.getAllReviewsToService(DatabaseConnection.getConnection(), parseInt);
            ResultSet rs = DatabaseConnection.runQuery(st);
            ArrayList<ReviewInformation> reviews = new ArrayList<>();
            while (rs.next()){
                reviews.add(ReviewInformation.fromResultSet(rs,1));
            }
            return reviews;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private ArrayList<ReviewInformation> getUserReviews(int parseInt) {
        try{
            PreparedStatement st = StatementPreparer.getAllReviewsToUser(DatabaseConnection.getConnection(), parseInt);
            ResultSet rs = DatabaseConnection.runQuery(st);
            ArrayList<ReviewInformation> reviews = new ArrayList<>();
            while (rs.next()){
                reviews.add(ReviewInformation.fromResultSet(rs,0));
            }
            return reviews;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
