package com.aishow.backend.handlers.appinteraction;

import java.sql.PreparedStatement;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ReviewInformation;

public class ReviewAdditionHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        ReviewInformation review = (ReviewInformation) reqBody;
        PreparedStatement st;
        try {
        if (review.getType() == 0){
            st = StatementPreparer.createUserReview(DatabaseConnection.getConnection(), review.getReviewer().getUserId(), 
            Integer.parseInt(params[0]), review.getScore(), review.getComment());
            if (DatabaseConnection.runUpdate(st) == 1)
                return (G) "OK";
            return (G) "NO";
        } else {
            st = StatementPreparer.createServiceReview(DatabaseConnection.getConnection(), review.getReviewer().getUserId(), 
            Integer.parseInt(params[0]), review.getScore(), review.getComment());
            if (DatabaseConnection.runUpdate(st) == 1)
                return (G) "OK";
            return (G) "NO";
        }
    } catch (Exception e) {
        e.printStackTrace();
        return (G) "NO";
    }
    }
    
}
