package com.aishow.backend.models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ServiceBundle {
    ArrayList<ServiceInformation> serviceInfos;

    public ServiceBundle() {
    }

    public static ServiceBundle fromResultSet(ResultSet rs){
        return null;
    }

    public ArrayList<ServiceInformation> getServiceInfos() {
        return serviceInfos;
    }

    public void setServiceInfos(ArrayList<ServiceInformation> servInfos) {
        this.serviceInfos = servInfos;
    }
    
}
