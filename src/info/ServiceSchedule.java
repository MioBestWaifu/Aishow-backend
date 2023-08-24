package info;

import java.util.ArrayList;
import java.util.HashMap;

import managers.Utils;

public class ServiceSchedule {
    private ArrayList<ClientServiceInteraction> pendingInstances;
    private ArrayList<ClientServiceInteraction> pendingRequests;

    public String toJson(){
        HashMap<String,String> mapFields = new HashMap<>();
        if (!(pendingInstances == null || pendingInstances.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ClientServiceInteraction si : pendingInstances){
                toJoin.add(si.toJson());
            }
            mapFields.put("pendingInstances", Utils.joinJsonArray(toJoin));
        }
        if (!(pendingRequests == null || pendingRequests.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ClientServiceInteraction si : pendingRequests){
                toJoin.add(si.toJson());
            }
            mapFields.put("pendingRequests", Utils.joinJsonArray(toJoin));
        }

        if (mapFields.isEmpty()){
            return "{}";
        }
        return Utils.toJson(mapFields);
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
