package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.handlers.personalinteraction.AnswerRequestHandler;

public class CancelRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    //AUTH SECURITY
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        if (new AnswerRequestHandler().deny(Integer.parseInt(params[0]))){
            return (G) "OK";
        } else {
            return (G) "FAIL";
        }
    }
    
}
