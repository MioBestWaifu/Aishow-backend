package com.aishow.backend.info;

import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ServiceBundle {
    ArrayList<ServiceInformation> ServiceInfos;

    public ServiceBundle() {
    }

    public ArrayList<ServiceInformation> getServiceInfos() {
        return ServiceInfos;
    }
    
    
    public String toJson(){
        HashMap<String,String> mapFields  = new HashMap<String,String>();
        if (!(ServiceInfos == null || ServiceInfos.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ServiceInformation si : ServiceInfos){
                toJoin.add(si.toJson());
            }
            mapFields.put("ServiceInfos", Utils.joinJsonArray(toJoin));
        }

        return Utils.toJson(mapFields);
    }

    public void setServiceInfos(ArrayList<ServiceInformation> servInfos) {
        this.ServiceInfos = servInfos;
    }
    
}
