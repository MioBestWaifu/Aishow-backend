package com.aishow.backend.handlers.appinteraction;

import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;

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
        switch(params[0]){
            case "service":
                return (G)("http://localhost:8080/images/"+DatabaseConnection.getServiceImageUrl(Integer.parseInt(params[1])));
            case "user":
                return (G)("http://localhost:8080/images/"+DatabaseConnection.getUserImageUrl(Integer.parseInt(params[1])));
        }
        return (G)"FAIL";
    }
 
}
