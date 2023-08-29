package com.aishow.backend.info;

import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ServiceSchedule {
    ArrayList<ClientServiceInteraction> pendingInstances;
    ArrayList<ClientServiceInteraction> pendingRequests;

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
