package com.aishow.backend.handlers.personalinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ClientServiceInteraction;
import com.ctc.wstx.shaded.msv_core.reader.State;

//TODO #32 evitar o uso de var

public class AnswerRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        return null;
    }

    //AUTH
    /**
     * Retorna apenas uma mensagem apropriada
     * @param params tipo/categoria no 0, id do request no 1 e idUser (que chamou) no 2
     */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        //SECURITY
        switch(params[0]){
            case "accept":
                if(accept(Integer.parseInt(params[1]))){
                    return (G)"OK";
                } else {
                    return (G)"FAIL";
                }
            case "deny":
                if(deny(Integer.parseInt(params[1]))){
                    return(G)"OK";
                } else {
                    return(G)"FAIL";
                }
        }

        //fazer um retorno diferente disso pra diferenciar
        return(G)"FAIL";
    }

    public boolean accept(int reqId){
        try{
            PreparedStatement st = StatementPreparer.getRequestById(DatabaseConnection.getConnection(), reqId);
            ResultSet rs = DatabaseConnection.runQuery(st);
            rs.next();

            st = StatementPreparer.createServiceInstance(DatabaseConnection.getConnection(), ClientServiceInteraction.fromResultSet(rs, false));
            int x = DatabaseConnection.runUpdate(st);
            if (x != 1)
                return false;
            
            st = StatementPreparer.deleteServiceRequest(DatabaseConnection.getConnection(), reqId);
            int y = DatabaseConnection.runUpdate(st);

            //Tem q diferencia o local de erro
            return y == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deny(int reqId){
        try {
            PreparedStatement st = StatementPreparer.deleteServiceRequest(DatabaseConnection.getConnection(), reqId);
            int y = DatabaseConnection.runUpdate(st);
            return y == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
}
