package com.aishow.backend.handlers.userinteraction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.UserInformation;
import com.mysql.cj.xdevapi.PreparableStatement;

public class UserRequestHandler extends BaseHandler{
    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    /**
     * Passar esse metodo pro normal com o AUTH, passar o id vai ser desnecessario
     * @param params passar id no 0
     */

    //AUTH SECURITY
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try {
            return (G) getUserById(Integer.parseInt(params[0]));
        } catch (SQLException ex){
            return null;
        }
    }
    
    public UserInformation getUserById(int id) throws SQLException{
        PreparedStatement st = StatementPreparer.getUserById(DatabaseConnection.getConnection(), id);
        return UserInformation.fromResultSet(DatabaseConnection.runQuery(st));
    }
}
