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
    
    
    public String toJson(){
        HashMap<String,String> mapFields  = new HashMap<String,String>();
        if (!(serviceInfos == null || serviceInfos.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ServiceInformation si : serviceInfos){
                toJoin.add(si.toJson());
            }
            mapFields.put("ServiceInfos", Utils.joinJsonArray(toJoin));
        }

        return Utils.toJson(mapFields);
    }

    public void setServiceInfos(ArrayList<ServiceInformation> servInfos) {
        this.serviceInfos = servInfos;
    }
    
}
