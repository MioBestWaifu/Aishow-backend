package managers;

import java.util.HashMap;

import info.UserConnection;
import info.UserInformation;

public abstract class UserConnectionManager {
    private static HashMap<String,UserConnection> connections = new HashMap<String,UserConnection>();

    public static void addConnection(String address){
        connections.put(address, new UserConnection());
    }

    public static void setConnectionUserInfo(String address,UserInformation info){
        connections.get(address).setUserInformation(info);
    }

    public static UserConnection getConnection(String address){
        return connections.get(address);
    }

    public static UserInformation getInformation(String address){
        return connections.get(address).getUserInformation();
    }

    public static boolean hasIp(String host){
        return connections.containsKey(host);
    }
}
