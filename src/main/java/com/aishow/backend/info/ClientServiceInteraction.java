package com.aishow.backend.info;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ClientServiceInteraction {
    boolean isAccepted, hasFinished;
    int id, clientId, templateId;
    Date startDate, endDate;
    Time startTime, endTime;
    float cost;
    UserInformation client;
    ServiceInformation service;

    public ClientServiceInteraction(){}

    public ClientServiceInteraction(String json){
        var map = Utils.mapJson(json, this.getClass());
        if (map.containsKey("isAccepted"))
            isAccepted = Boolean.parseBoolean(map.get("isAccepted"));
        if (map.containsKey("hasFinished"))
            hasFinished = Boolean.parseBoolean(map.get("hasFinished"));
        if (map.containsKey("id"))
            id = Integer.parseInt(map.get("id"));
        if (map.containsKey("clientId"))
            clientId = Integer.parseInt(map.get("clientId"));
        if (map.containsKey("templateId"))
            templateId = Integer.parseInt(map.get("templateId"));
        if (map.containsKey("cost"))
            cost = Float.parseFloat(map.get("cost"));
        if (map.containsKey("startDate"))
            startDate = Date.valueOf(map.get("startDate"));
        if (map.containsKey("endDate"))
            endDate = Date.valueOf(map.get("endDate"));
        if (map.containsKey("startTime"))
            startTime = Time.valueOf(map.get("startTime"));
        if (map.containsKey("endTime"))
            endTime = Time.valueOf(map.get("endTime"));
    }

    public String toJson(){
        HashMap<String,String> mapFields = new HashMap<>();
        mapFields.put("id", Integer.toString(id));
        mapFields.put("clientId", Integer.toString(clientId));
        mapFields.put("templateId", Integer.toString(templateId));
        mapFields.put("cost", Float.toString(cost));

        var x = startDate.toLocalDate();
        mapFields.put("startDate",x.getDayOfMonth() + "/"+x.getMonthValue()+"/"+x.getYear());
        x = endDate.toLocalDate();
        mapFields.put("endDate", x.getDayOfMonth() + "/"+x.getMonthValue()+"/"+x.getYear());

        mapFields.put("startTime", startTime.toString());
        mapFields.put("endTime", endTime.toString());
        if(client != null);
            mapFields.put("client", client.toJson());
        if(service != null);
            mapFields.put("service", service.toJson());
        return Utils.toJson(mapFields);
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
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getTemplateId() {
        return templateId;
    }
    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date start) {
        this.startDate = start;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date end) {
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
    
    
}
