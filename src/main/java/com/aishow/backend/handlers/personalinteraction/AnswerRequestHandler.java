package com.aishow.backend.handlers.personalinteraction;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.BaseHandler;

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
        switch(params[0]){
            case "accept":
                if(DatabaseConnection.AcceptRequest(Integer.parseInt(params[1]),Integer.parseInt(params[2]))){
                    return (G)"OK";
                } else {
                    return (G)"FAIL";
                }
            case "deny":
                if(DatabaseConnection.DenyRequest(Integer.parseInt(params[1]),Integer.parseInt(params[2]))){
                    return(G)"OK";
                } else {
                    return(G)"FAIL";
                }
        }

        //fazer um retorno diferente disso pra diferenciar
        return(G)"FAIL";
    }
    
}
