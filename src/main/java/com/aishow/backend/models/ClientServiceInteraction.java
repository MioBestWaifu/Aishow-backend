package com.aishow.backend.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.HashMap;

import com.aishow.backend.utils.Utils;

public class ClientServiceInteraction {
    boolean isAccepted, hasFinished, isProvider;
    int id;
    String startDate, endDate;
    Time startTime, endTime;
    float cost;
    UserInformation client;
    ServiceInformation service;

    public static ClientServiceInteraction fromResultSet(ResultSet rs, boolean isInstance){
        ClientServiceInteraction toReturn = new ClientServiceInteraction();
        Class[] paramTypes = new Class[]{String.class};
        if (isInstance){
            toReturn.id = (int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"idServiceInstances"});
            toReturn.hasFinished = (boolean) Utils.runMethodReflection(rs, "getBoolean", paramTypes, new Object[]{"finished"});
        }
        else{
            toReturn.id = (int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"serviceRequestID"});
        }
        toReturn.startDate = (String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"startDate"});
        toReturn.endDate = (String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"endDate"});
        toReturn.startTime = (Time) Utils.runMethodReflection(rs, "getTime", paramTypes, new Object[]{"startTime"});
        toReturn.endTime = (Time) Utils.runMethodReflection(rs, "getTime", paramTypes, new Object[]{"endTime"});
        toReturn.cost = (float) Utils.runMethodReflection(rs, "getFloat", paramTypes, new Object[]{"cost"});
        toReturn.service = new ServiceInformation();
        toReturn.client = new UserInformation();
        toReturn.service.templateId = (int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"templateID"});
        toReturn.client.userId = (int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"clientID"});
        toReturn.isAccepted = isInstance;
        return toReturn;
    }
    
    public boolean isAccepted() {
        return isAccepted;
    }
    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
    public boolean isHasFinished() {
        return hasFinished;
    }
    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String start) {
        this.startDate = start;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String end) {
        this.endDate = end;
    }
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time starTime) {
        this.startTime = starTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    

    public UserInformation getClient() {
        return client;
    }

    public void setClient(UserInformation client) {
        this.client = client;
    }

    public ServiceInformation getService() {
        return service;
    }

    public void setService(ServiceInformation service) {
        this.service = service;
    }
    public boolean isProvider() {
        return isProvider;
    }
    public void setProvider(boolean isProvider) {
        this.isProvider = isProvider;
    }
    
    
}
