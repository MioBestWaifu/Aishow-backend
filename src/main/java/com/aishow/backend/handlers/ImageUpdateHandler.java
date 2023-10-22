package com.aishow.backend.handlers;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.serviceinteraction.ServiceRequestHandler;
import com.aishow.backend.modular.ImageHandler;
import com.aishow.backend.utils.Utils;

public class ImageUpdateHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    /**
     * Retorna apenas uma mensagem apropriada
     * @param params tipo/categoria no 0, id no 1 e idProvider no 2 caso seja um servico
     */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        switch (params[0]){
            case "service":
                return (G) tryToUpdateServicePicture((byte[])reqBody, Integer.parseInt(params[2]),Integer.parseInt(params[1]));
            case "user":
                return (G) tryToUpdateUserPicture((byte[])reqBody, Integer.parseInt(params[1]));
        }
        return null;
    }

    //TODO #4 Fazer versão de produção e teste dissoq nd tiver cdn
    //AUTH
    private String tryToUpdateServicePicture(byte[]image,int creator, int id){
        if(id==-1){
            id = ServiceRequestHandler.findLastCreatedService(creator);
        }
        //A função acima retorna -1 se houver erro
        if (id!=-1 && ImageHandler.updateServicePicture(id, "png",image)){
            return "OK";
        } else {
            return"FAIL";
        }
    }

    //AUTH
    private String tryToUpdateUserPicture(byte[]image, int id){
        if (ImageHandler.updateUserProfilePicture(id,"png", image)) {
            return "OK";
        } else {
            return "FAIL";
        }
    }
    
}
