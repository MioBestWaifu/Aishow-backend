package com.aishow.backend.info;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ClientServiceInteraction {
    boolean isAccepted, hasFinished, isProvider;
    int id, clientId, templateId;
    Date startDate, endDate;
    Time startTime, endTime;
    float cost;
    UserInformation client;
    ServiceInformation service;


    
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
    public boolean isProvider() {
        return isProvider;
    }
    public void setProvider(boolean isProvider) {
        this.isProvider = isProvider;
    }
    
    
}
