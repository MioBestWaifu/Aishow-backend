package com.aishow.backend.handlers.appinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.modular.ModularInfo;

public class PathfindHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    /**
    É pra retornar uma String com URL para a imgaem do recurso solicitado 
    @param params passar o tipo/categoria no 0 e o id no 1
    */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO MODIFICAR ISSO QUADNO TIVER UMA CDN OU FAZER UMA VERSÃO PARA TESTES E UMA PARA PRODUÇÃO
        ResultSet rs;
        try{
            switch(params[0]){
                case "service":
                    rs = DatabaseConnection.runQuery(StatementPreparer.getServiceImageUrlById(
                        DatabaseConnection.getConnection(), Integer.parseInt(params[1])));
                    rs.next();
                    return (G)(ModularInfo.BASE_IMAGE_URL+rs.getString(1));
                case "user":
                    rs = DatabaseConnection.runQuery(StatementPreparer.getUserProfileImageUrlById(
                    DatabaseConnection.getConnection(), Integer.parseInt(params[1])));
                    rs.next();
                    return (G)(ModularInfo.BASE_IMAGE_URL+rs.getString(1));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            return (G) "FAIL";
        }
    }

    public String getUserBaseUrl(int id) throws SQLException{
        ResultSet rs = DatabaseConnection.runQuery(StatementPreparer.getUserProfileImageUrlById(
        DatabaseConnection.getConnection(), id));
        rs.next();
        return rs.getString(1);
    }

    public String getServiceBaseUrl(int id) throws SQLException{
        ResultSet rs = DatabaseConnection.runQuery(StatementPreparer.getServiceImageUrlById(
        DatabaseConnection.getConnection(), id));
        rs.next();
        return rs.getString(1);
    }
 
}
