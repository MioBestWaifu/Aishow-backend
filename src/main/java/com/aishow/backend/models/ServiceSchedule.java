package com.aishow.backend.models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ServiceSchedule {
    ArrayList<ClientServiceInteraction> pendingInstances;
    ArrayList<ClientServiceInteraction> pendingRequests;

    //Dar conta do isProvider aq
    public static ServiceSchedule fromResultSet(ResultSet rs){
        return null;
    }

    public ArrayList<ClientServiceInteraction> getPendingInstances() {
        return pendingInstances;
    }

    public void setPendingInstances(ArrayList<ClientServiceInteraction> pendingInstances) {
        this.pendingInstances = pendingInstances;
    }

    public ArrayList<ClientServiceInteraction> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(ArrayList<ClientServiceInteraction> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    
}
