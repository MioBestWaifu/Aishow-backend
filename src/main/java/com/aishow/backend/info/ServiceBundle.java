package com.aishow.backend.info;

import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ServiceBundle {
    ArrayList<ServiceInformation> serviceInfos;

    public ServiceBundle() {
    }

    public ArrayList<ServiceInformation> getServiceInfos() {
        return serviceInfos;
    }

    public void setServiceInfos(ArrayList<ServiceInformation> servInfos) {
        this.serviceInfos = servInfos;
    }
    
}
