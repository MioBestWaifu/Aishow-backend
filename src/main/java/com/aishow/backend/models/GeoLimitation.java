package com.aishow.backend.models;
import java.sql.ResultSet;

import com.aishow.backend.utils.Utils;


public class GeoLimitation {
    int idUser, idArea;

    public static GeoLimitation fromResultSet(ResultSet rs){
        GeoLimitation toReturn = new GeoLimitation();
        Class[] paramTypes = new Class[]{String.class};
        toReturn.idUser =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"idUser"});
        toReturn.idArea = (int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"idArea"});
        return toReturn;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
}
