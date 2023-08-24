package info;

import java.util.ArrayList;
import java.util.HashMap;

import managers.Utils;

public class ServiceBundle {
    ArrayList<ServiceInformation> servInfos;

    public ServiceBundle() {
    }

    public ArrayList<ServiceInformation> getServInfos() {
        return servInfos;
    }
    
    
    public String toJson(){
        HashMap<String,String> mapFields  = new HashMap<String,String>();
        if (!(servInfos == null || servInfos.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ServiceInformation si : servInfos){
                toJoin.add(si.toJson());
            }
            mapFields.put("ServiceInfos", Utils.joinJsonArray(toJoin));
        }

        return Utils.toJson(mapFields);
    }

    public void setServInfos(ArrayList<ServiceInformation> servInfos) {
        this.servInfos = servInfos;
    }
    
}
