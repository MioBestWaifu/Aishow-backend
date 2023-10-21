package com.aishow.backend.models;

import java.sql.ResultSet;

import com.aishow.backend.managers.Utils;

public class GenericInformation{
    public int Id;
    public String Name;

    public static GenericInformation fromResultSet(ResultSet rs, String type){
        GenericInformation toReturn = new GenericInformation();
        var paramTypes = new Class[]{String.class};
        toReturn.Id =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"id"+type});
        toReturn.Name = (String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"name"});
        return toReturn;
    }
}
