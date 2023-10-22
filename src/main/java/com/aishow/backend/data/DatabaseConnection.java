package com.aishow.backend.data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.aishow.backend.models.*;
import com.aishow.backend.utils.Utils;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

public abstract class DatabaseConnection {
    private static Connection conn;
    private static ArrayList<Integer> serviceIds;
    public static void connect() throws IOException{
        //BufferedReader txtReader = new BufferedReader(new InputStreamReader(DatabaseConnection.class.getResourceAsStream("../modular/conninfo.txt")));
        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/aluguel";
        String username = "root";
        String serverPassword = "uiharu";
        try {
            conn = DriverManager.getConnection(url, username, serverPassword);
        } catch (SQLException ex) {
            System.out.println("Conexão merda");
            System.out.println(ex.getMessage());
            return;
        }
        serviceIds = new ArrayList<>();
        try{
        var st = conn.prepareStatement("SELECT idServiceTemplates FROM servicetemplates");
        var res = st.executeQuery();
        while(res.next()){
            serviceIds.add(res.getInt(1));
        }
        } catch (SQLException ex){
            System.out.println("Init falhou");
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    public static ResultSet runQuery(PreparedStatement st) throws SQLException{
        return st.executeQuery();
    }

    public static int runUpdate(PreparedStatement st) throws SQLException{
        return st.executeUpdate();
    }

    public static Connection getConnection(){
        return conn;
    }

    public static ArrayList<Integer> getAvailableServiceIds(){
        ArrayList<Integer> available = new ArrayList<>(serviceIds.size());
        for(int i=0;i<serviceIds.size();i++){
            available.add(i);
        }
        Collections.copy(available, serviceIds);
        return available;
    }


    public static UserInformation getBasicUserInformation(UserInformation info){
        try {
            var st = conn.prepareStatement("SELECT idUser, name, birthday, gender, profileUrl,area FROM user WHERE user.email = ? OR user.idUser = ?");
            st.setString(1, info.getEmail());
            st.setInt(2, info.getUserId());
            var result = st.executeQuery();
            result.next();
            info.setUserId(result.getInt("idUser"));
            info.setName(result.getString("name"));
            info.setBirthday(result.getDate("birthday"));
            info.setGender(result.getString("gender"));
            info.setImageUrl(result.getString("profileUrl"));

            try{
            var areaSt = conn.prepareStatement("SELECT name FROM area WHERE idArea = ?");
            areaSt.setInt(1, result.getInt("area"));
            var areaRes = areaSt.executeQuery();
            areaRes.next();
            info.setAreaName(areaRes.getString(1));
            } catch (SQLException ex){
            }

            return info;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static UserInformation userFromId(int id){
        UserInformation user = new UserInformation();
        user.setUserId(id);
        return user;
    }

    private static ServiceInformation serviceFromId(int id){
        ServiceInformation user = new ServiceInformation();
        user.setTemplateId(id);
        return user;
    }

    public static UserInformation getSensitiveUserInformation(UserInformation info){
        try {
            var st = conn.prepareStatement("SELECT email, providingService FROM user WHERE user.idUser = ?");
            st.setInt(1, info.getUserId());
            var result = st.executeQuery();
            result.next();
            info.setEmail(result.getString("email"));
            info.setProvidingService(result.getBoolean("providingService"));
            return info;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static UserInformation getActiveUserInformation(UserInformation info){
        info = getBasicUserInformation(info);
        info = getSensitiveUserInformation(info);
        info.setServiceRecs(getServiceRecommendations(info.getUserId()));
        info = getUserReviews(info);
        return info;
    }

    public static UserInformation getRequestedUserInformation(int id){
        UserInformation info = getBasicUserInformation(userFromId(id));
        info.setServices(getUserServices(info.getUserId()));
        info = getUserReviews(info);
        return info;
    }

    

    public static boolean tryToRegister(UserInformation info){
        try {
            var st = conn.prepareStatement("SELECT * FROM user WHERE user.email = ?");
            st.setString(1, info.getEmail());
            var result = st.executeQuery();
            if (result.next())
                return false;
        st = conn.prepareStatement("INSERT INTO user (name,email,password,birthday,profileUrl,gender,area)"+
        "VALUES(?,?,?,?,?,?,?)");
        st.setString(1, info.getName());
        st.setString(2, info.getEmail());
        st.setString(3, info.getPassword());
        st.setDate(4, info.getBirthday());
        st.setString(5, "0.png");
        st.setString(6, info.getGender());
        st.setInt(7, info.getAreaCode());
        int rowsAffected = st.executeUpdate();
        return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tryToAddServiceTemplate(ServiceInformation info){
        try {
            var st = conn.prepareStatement("INSERT INTO serviceTemplates (idProvider,serviceName,description,costPerHour,serviceModality,serviceCategory,templateImageUrl)"+
            "VALUES (?,?,?,?,?,?,?)");
            st.setInt(1, info.getProviderId());
            st.setString(2, info.getServiceName());
            st.setString(3, info.getDescription());
            st.setFloat(4, info.getCostPerHour());
            st.setInt(5, info.getModality());
            st.setInt(6, info.getCategory());
            st.setString(7, "0.png");
            var creation =  st.executeUpdate()>0;
            var x = info.getFromsAsTime();
            var y = info.getTosAsTime();

            for(int a = 0; a<=6;a++){
                if (info.getAvailableDays()[a] == true){
                    addAvailability(getLastCreatedService(info.getProviderId()), a, x[a], y[a]);
                }
            }

            return creation;
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("CAT"+info.getCategory());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tryToUpdateServiceTemplate(ServiceInformation info){
        try{
            var st = conn.prepareStatement("UPDATE servicetemplates "+
            "SET serviceModality = ?, serviceCategory = ?, description = ?, serviceName = ?, costPerHour = ? "+
            "WHERE idServiceTemplates = ?");
            st.setInt(1, info.getModality());
            st.setInt(2, info.getCategory());
            st.setString(3, info.getDescription());
            st.setString(4, info.getServiceName());
            st.setFloat(5, info.getCostPerHour());
            st.setInt(6, info.getTemplateId());
            return st.executeUpdate() == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM UPDATE TEMPLATE");
            return false;
        }
    }

    public static boolean addAvailability(int templateId, int weekday, Time from, Time to){
        try {
            var st = conn.prepareStatement("INSERT INTO serviceavailability (templateID, weekday,startHour,endHour) VALUES (?,?,?,?)");
            st.setInt(1, templateId);
            st.setInt(2, weekday);
            st.setTime(3, from);
            st.setTime(4, to);
            return st.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateAvailabilityScheme(ServiceInformation info){
        try{
            var st = conn.prepareStatement("SELECT serviceAvailabilityID FROM serviceavailability WHERE templateID = ?");
            st.setInt(1, info.getTemplateId());
            var res = st.executeQuery();
            while(res.next()){
                if (!deleteAvailability(res.getInt(1)))
                    return false;
            }

            var x = info.getFromsAsTime();
            var y = info.getTosAsTime();

            for(int a = 0; a<=6;a++){
                if (info.getAvailableDays()[a] == true){
                    if (!addAvailability(info.getTemplateId(), a, x[a], y[a]))
                        return false;
                }
            }

            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM DELETE AVILABILITY");
            return false;
        }
    }

    public static boolean deleteAvailability(int id){
        try{
            var st = conn.prepareStatement("DELETE FROM serviceavailability WHERE serviceAvailabilityID = ?");
            st.setInt(1, id);
            return st.executeUpdate() == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM DELETE AVILABILITY");
            return false;
        }
    }

    public static ArrayList<ServiceBundle> getServiceRecommendations(int userCode){
        ArrayList<ServiceBundle> toReturn = new ArrayList<>();
        ArrayList<ServiceInformation> buffer = new ArrayList<>();
        ArrayList<Integer> available = new ArrayList<>(serviceIds.size());
        for(int i=0;i<serviceIds.size();i++){
            available.add(i);
        }
        Collections.copy(available, serviceIds);
        PreparedStatement providerSt = null;
        try {
            providerSt = conn.prepareStatement("SELECT * FROM user WHERE idUser = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 1; i<=12; i++){
            try {
                var x = new Random().nextInt(available.size());
                ServiceInformation toAdd = DatabaseConnection.serviceFromId(available.get(x));
                toAdd = DatabaseConnection.getBasicServiceInformation(toAdd);
                providerSt.setInt(1, toAdd.getProviderId());
                var providerRes = providerSt.executeQuery();
                providerRes.next();
                toAdd.setProviderName(providerRes.getString("name"));
                toAdd.setProviderImageUrl(providerRes.getString("profileUrl"));
                var k = providerRes.getString("profileUrl");
                toAdd.setProviderUrl(Integer.toString(providerRes.getInt("idUser")));

                var areaSt = conn.prepareStatement("SELECT name FROM area WHERE area.idArea = ?");
                areaSt.setInt(1,providerRes.getInt("area"));
                var areaRes = areaSt.executeQuery();
                areaRes.next();
                toAdd.setProviderArea(areaRes.getString(1));

                DatabaseConnection.getServiceReviews(toAdd, false);
                buffer.add(toAdd);
                available.remove(x);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        toReturn.add(new ServiceBundle());
        toReturn.add(new ServiceBundle());
        toReturn.add(new ServiceBundle());
        toReturn.get(0).setServiceInfos(new ArrayList<ServiceInformation>(buffer.subList(0, 4)));
        toReturn.get(1).setServiceInfos(new ArrayList<ServiceInformation>(buffer.subList(4, 8)));
        toReturn.get(2).setServiceInfos(new ArrayList<ServiceInformation>(buffer.subList(8, 12)));
        return toReturn;
    }

    public static boolean tryToUpdateUserName(int id, String newName){
        try{
            var st = conn.prepareStatement("UPDATE user SET name = ? WHERE idUser = ?");
            st.setString(1, newName);
            st.setInt(2, id);
            var rst = st.executeUpdate();
            return rst == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tryToUpdateUserImageUrl(int id, String newUrl){
        try{
            var st = conn.prepareStatement("UPDATE user SET profileUrl = ? WHERE idUser = ?");
            st.setString(1, newUrl);
            st.setInt(2, id);
            var rst = st.executeUpdate();
            return rst == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tryToUpdateServiceImageUrl(int id, String newUrl){
        try{
            var st = conn.prepareStatement("UPDATE servicetemplates SET templateImageUrl = ? WHERE idServiceTemplates = ?");
            st.setString(1, newUrl);
            st.setInt(2, id);
            var rst = st.executeUpdate();
            return rst == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tryToUpdateUserArea(int userId, int areaId){
        try{
            var st = conn.prepareStatement("UPDATE user SET area = ? WHERE idUser = ?");
            st.setInt(1, areaId);
            st.setInt(2, userId);
            var rst = st.executeUpdate();
            return rst == 1;

        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean tryToUpdateUserArea(int userId, String areaName){
        try{
            var st = conn.prepareStatement("UPDATE user SET area = (SELECT idArea FROM area WHERE area.name = ?) WHERE idUser = ?");
            st.setString(1, areaName);
            st.setInt(2, userId);
            var rst = st.executeUpdate();
            return rst == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean tryToAddReviewToService(int reviewer, int target, int score, String comment){
        try{
            var st = conn.prepareStatement("INSERT INTO servicereviews VALUES (?,?,?,?)");
            st.setInt(1, reviewer);
            st.setInt(2, target);
            st.setInt(3, score);
            st.setString(4, comment);
            var rst = st.executeUpdate();
            return rst == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean tryToAddReviewToUser(int reviewer, int target, int score, String comment){
        try{
            var st = conn.prepareStatement("INSERT INTO userreviews VALUES (?,?,?,?)");
            st.setInt(1, reviewer);
            st.setInt(2, target);
            st.setInt(3, score);
            st.setString(4, comment);
            var rst = st.executeUpdate();
            return rst == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }


    public static String getSingleGenericInfo(String table, String idCol, String nameCol, int id){
        try{
            var st = conn.prepareStatement("SELECT * FROM "+table+" WHERE "+idCol+" = ?");
            st.setInt(1, id);
            var rst = st.executeQuery();
            if(rst.next())
                return rst.getString(nameCol);
            return null;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<ServiceInformation> getUserServices(int id){
        try{
            var st = conn.prepareStatement("SELECT idServiceTemplates, costPerHour, description, serviceName, templateImageUrl, serviceModality, serviceCategory FROM servicetemplates WHERE idProvider = ?");
            st.setInt(1, id);
            var res = st.executeQuery();
            ArrayList<ServiceInformation> toAdd = new ArrayList<>();
            ServiceInformation buffer;
            while (res.next()){
                buffer = new ServiceInformation();
                buffer.setProviderId(id);
                buffer.setTemplateId(res.getInt("idServiceTemplates"));
                buffer.setCostPerHour(res.getFloat("costPerHour"));
                buffer.setDescription(res.getString("description"));
                buffer.setServiceName(res.getString("serviceName"));
                try{
                    buffer.setShortServiceName(buffer.getServiceName().substring(0, 23)+"...");
                } catch(Exception ex){
                    buffer.setShortServiceName(buffer.getServiceName());
                }
                buffer.setTemplateImageUrl(res.getString("templateImageUrl"));
                buffer.setCategory(res.getInt("serviceCategory"));
                buffer.setCatText(getSingleGenericInfo("servicecategory", "idServiceCategory", "categoryName", buffer.getCategory()));
                buffer.setModality(res.getInt("serviceModality"));
                buffer.setModText(getSingleGenericInfo("servicemodality", "idServiceModality", "modalityName", buffer.getModality()));
                getServiceReviews(buffer, false);
                GetServiceAvailability(buffer);
                toAdd.add(buffer);
            }
            return toAdd;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static UserInformation getUserReviews(UserInformation info){
        try{
            var st = conn.prepareStatement("SELECT idreviewer, score, comment FROM userreviews WHERE idtarget = ?");
            st.setInt(1, info.getUserId());
            var res = st.executeQuery();
            ArrayList<ReviewInfomation> toAdd = new ArrayList<>();
            ReviewInfomation buffer;
            ArrayList<Integer> scores = new ArrayList<>();
            while (res.next()){
                scores.add(res.getInt("score"));
                buffer = new ReviewInfomation();
                buffer.setScore(res.getInt("score"));
                buffer.setComment(res.getString("comment"));
                UserInformation x = new UserInformation();
                x.setUserId(res.getInt("idreviewer"));
                buffer.setReviewer(getBasicUserInformation(x));
                toAdd.add(buffer);
            }
            info.setReviews(toAdd);
            try{
                var r = Utils.sumOfArray(scores);
                var s =(float) r/scores.size();
                info.setAverageScore(s);
            } catch (Exception ex){
                info.setAverageScore(0);
                ex.printStackTrace();
            }
            return info;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ServiceInformation getServiceReviews(ServiceInformation info, boolean keepReviews){
        try{
            var st = conn.prepareStatement("SELECT idclient, score, comment FROM servicereviews WHERE idtemplate = ?");
            st.setInt(1, info.getTemplateId());
            var res = st.executeQuery();
            ArrayList<ReviewInfomation> toAdd = new ArrayList<>();
            ArrayList<Integer> scores = new ArrayList<>();
            ReviewInfomation buffer;
            while (res.next()){
                buffer = new ReviewInfomation();
                buffer.setScore(res.getInt("score"));
                buffer.setComment(res.getString("comment"));
                UserInformation x = new UserInformation();
                x.setUserId(res.getInt("idclient"));
                buffer.setReviewer(getBasicUserInformation(x));
                scores.add(res.getInt("score"));
                toAdd.add(buffer);
            }
            try{
                var r = Utils.sumOfArray(scores);
                var s =(float) r/scores.size();
                info.setAverageScore(s);
            } catch (Exception ex){
                info.setAverageScore(0);
                ex.printStackTrace();
            }
            
            if(keepReviews)
                info.setReviews(toAdd);
            return info;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ServiceInformation getBasicServiceInformation(ServiceInformation info){
        try{
            var st = conn.prepareStatement("SELECT idProvider, idServiceTemplates, costPerHour, description, serviceName, templateImageUrl,serviceModality,serviceCategory FROM servicetemplates WHERE idServiceTemplates = ?");
            st.setInt(1, info.getTemplateId());
            var res = st.executeQuery();
            res.next();
            info.setProviderId(res.getInt("idProvider"));
            info.setCostPerHour(res.getFloat("costPerHour"));
            info.setDescription(res.getString("description"));
            info.setServiceName(res.getString("serviceName"));
            try{
                info.setShortServiceName(info.getServiceName().substring(0, 23)+"...");
            } catch (Exception ex){
                info.setShortServiceName(info.getServiceName());
            }
            info.setTemplateImageUrl(res.getString("templateImageUrl"));
            var x = res.getString("templateImageUrl");
            info.setTemplateId(res.getInt("idServiceTemplates"));
            info.setCategory(res.getInt("serviceCategory"));
            info.setCatText(getSingleGenericInfo("servicecategory", "idServiceCategory", "categoryName", info.getCategory()));
            info.setModality(res.getInt("serviceModality"));
            info.setModText(getSingleGenericInfo("servicemodality", "idServiceModality", "modalityName", info.getModality()));
            return info;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ServiceInformation getFullServiceInformation(int id, boolean keepReviews){
        ServiceInformation info = serviceFromId(id);
        info = getBasicServiceInformation(info);
        info = getServiceReviews(info,keepReviews);
        DatabaseConnection.GetServiceAvailability(info);
        return info;
    }

    public static float getCredits(int id){
        try {
            var st = conn.prepareStatement("SELECT credits FROM users WHERE idUser = ?");
            st.setInt(1, id);
            var res = st.executeQuery();
            res.next();
            return res.getFloat("credits");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static int getLastCreatedService(int creator){
        try {
            var st = conn.prepareStatement("SELECT idServiceTemplates FROM serviceTemplates WHERE idProvider = ? ORDER BY idServiceTemplates DESC LIMIT 1");
            st.setInt(1, creator);
            var res = st.executeQuery();
            res.next();
            return res.getInt("idServiceTemplates");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
    }



    public static void addNewServiceRequest(ClientServiceInteraction info){
        try{
            var st = conn.prepareStatement("INSERT INTO servicerequests (templateID, clientID, startDate, endDate, startTime, endTime,cost)"+
             "VALUES (?,?,?,?,?,?,?)");
            st.setInt(1, info.getTemplateId());
            st.setInt(2, info.getClientId());
            st.setString(3, info.getStartDate());
            st.setString(4, info.getEndDate());
            st.setTime(5, info.getStartTime());
            st.setTime(6, info.getEndTime());
            st.setFloat(7, info.getCost());
            st.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void GetServiceAvailability(ServiceInformation info){
        try{
            var st = conn.prepareStatement("SELECT * FROM serviceavailability WHERE templateID = ?");
            st.setInt(1, info.getTemplateId());
            var res = st.executeQuery();
            boolean[] days = new boolean[7];
            Time[] from = new Time[7];
            Time[] to = new Time[7];
            int i;
            if(res.next()){
                i = res.getInt("weekday");
                days[i] = true;
                from[i] = res.getTime("startHour");
                to[i] = res.getTime("endHour");
                while(res.next()){
                    i = res.getInt("weekday");
                    days[i] = true;
                    from[i] = res.getTime("startHour");
                    to[i] = res.getTime("endHour");
                }
            } else {
                //for (int a = 0; a<=6; a++){
                    //days[a] = false;
                    //from[a] = new Time(0,0,0);
                    //to[a] = new Time(0, 0, 0);
                //}
            }
            info.setAvailableDays(days);
            info.setAvailableFromsFromTime(from);
            info.setAvailableTosFromTime(to);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ArrayList<ClientServiceInteraction> getUserRequests(int id){
        try{
            var st = conn.prepareStatement("SELECT * FROM servicerequests WHERE clientID = ?");
            st.setInt(1, id);
            var res = st.executeQuery();
            ArrayList<ClientServiceInteraction> buffer = new ArrayList<>();
            ClientServiceInteraction x;
            while (res.next()){
                x = new ClientServiceInteraction();
                x.setAccepted(true);
                x.setId(res.getInt("serviceRequestID"));
                x.setCost(res.getFloat("cost"));
                x.setStartDate(res.getString("startDate"));
                x.setEndDate(res.getString("endDate"));
                x.setStartTime(res.getTime("startTime"));
                x.setEndTime(res.getTime("endTime"));
                x.setTemplateId(res.getInt("templateID"));
                x.setClientId(res.getInt("clientID"));
                x.setProvider(x.getClientId() == id);
                System.out.println(x.getId());
                var y = new UserInformation();
                var z = new ServiceInformation();
                z.setTemplateId(x.getTemplateId());
                y.setUserId(x.getClientId());
                x.setClient(DatabaseConnection.getBasicUserInformation(y));
                x.setService(DatabaseConnection.getBasicServiceInformation(z));
                buffer.add(x);
            }

            return buffer;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ServiceSchedule getScheduleByUser (int id){
        try{
            ServiceSchedule toReturn = new ServiceSchedule();
            ArrayList<ClientServiceInteraction> buffer = new ArrayList<>();
            var st = conn.prepareStatement("SELECT * FROM serviceinstances WHERE templateID IN (SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ?) OR clientID = ? ORDER BY startDate, startTime");
            st.setInt(1, id);
            st.setInt(2, id);

            var res = st.executeQuery();

            ClientServiceInteraction x;
            while (res.next()){

                x = new ClientServiceInteraction();
                x.setAccepted(true);
                x.setId(res.getInt("idServiceInstances"));
                x.setCost(res.getFloat("cost"));
                x.setHasFinished(res.getBoolean("finished"));
                x.setStartDate(res.getString("startDate"));
                x.setEndDate(res.getString("endDate"));
                x.setStartTime(res.getTime("startTime"));
                x.setEndTime(res.getTime("endTime"));
                x.setTemplateId(res.getInt("templateID"));
                x.setClientId(res.getInt("clientID"));
                x.setProvider(x.getClientId() == id);

                System.out.println(x.getId());
                var y = new UserInformation();
                var z = new ServiceInformation();
                z.setTemplateId(x.getTemplateId());
                y.setUserId(x.getClientId());
                x.setClient(DatabaseConnection.getBasicUserInformation(y));
                x.setService(DatabaseConnection.getBasicServiceInformation(z));

                buffer.add(x);
            }

            toReturn.setPendingInstances(buffer);

            buffer = new ArrayList<>();

            st = conn.prepareStatement("SELECT * FROM servicerequests WHERE templateID IN (SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ?) ORDER BY startDate, startTime");
            st.setInt(1, id);
            res = st.executeQuery(); 

            while (res.next()){
                x = new ClientServiceInteraction();
                x.setAccepted(false);
                x.setId(res.getInt("serviceRequestID"));
                x.setCost(res.getFloat("cost"));
                x.setHasFinished(false);
                x.setStartDate(res.getString("startDate"));
                x.setEndDate(res.getString("endDate"));
                x.setStartTime(res.getTime("startTime"));
                x.setEndTime(res.getTime("endTime"));
                x.setTemplateId(res.getInt("templateID"));
                x.setClientId(res.getInt("clientID"));
                System.out.println(x.getId());
                var y = new UserInformation();
                var z = new ServiceInformation();
                z.setTemplateId(x.getTemplateId());
                y.setUserId(x.getClientId());
                x.setClient(DatabaseConnection.getBasicUserInformation(y));
                x.setService(DatabaseConnection.getBasicServiceInformation(z));
                
                buffer.add(x);
            }

            toReturn.setPendingRequests(buffer);

            return toReturn;
            
        } catch (SQLException ex){
            System.out.println("EXCEÇÃO NO SERV SCHEDULE");
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean AcceptRequest(int reqId, int userId){
        try{
            var st = conn.prepareStatement("SELECT * FROM servicerequests WHERE serviceRequestID = ?");
            st.setInt(1, reqId);
            var res = st.executeQuery();
            if (!res.next()){
                return false;
            }
            
            //Isso aq vai ser desnecessario como AUTH
            if(!DatabaseConnection.IsOwner(userId, res.getInt("templateID"))){
                return false;
            }

            st = conn.prepareStatement("INSERT INTO serviceinstances (clientID,templateID,startDate,endDate,startTime,endTime,cost)"+
            "VALUES (?,?,?,?,?,?,?)");
            st.setInt(1, res.getInt("clientID"));
            st.setInt(2, res.getInt("templateID"));
            st.setDate(3, res.getDate("startDate"));
            st.setDate(4, res.getDate("endDate"));
            st.setTime(5, res.getTime("startTime"));
            st.setTime(6, res.getTime("endTime"));
            st.setFloat(7, res.getFloat("cost"));

            st.executeUpdate();
            st = conn.prepareStatement("DELETE FROM servicerequests WHERE serviceRequestID = ?");
            st.setInt(1, reqId);
            return st.executeUpdate() > 0;
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM ACCEPT REQ");
            return false;
        }
    }

    public static boolean DenyRequest(int reqId, int userId){      
        try{
            var st = conn.prepareStatement("SELECT templateID FROM servicerequests WHERE serviceRequestID = ?");
            st.setInt(1, reqId);
            var res = st.executeQuery();
            if (!res.next()){
                return false;
            }
            if(!DatabaseConnection.IsOwner(userId, res.getInt("templateID"))){
                return false;
            }
            st = conn.prepareStatement("DELETE FROM servicerequests WHERE serviceRequestID = ?");
            st.setInt(1, reqId);
            return st.executeUpdate() > 0;
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM ACCEPT REQ");
            return false;
        }
    }

    public static boolean IsOwner(int provider, int template){
        try{
            var st = conn.prepareStatement("SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ? AND idServiceTemplates = ?");
            st.setInt(1, provider);
            st.setInt(2, template);
            return st.executeQuery().next();
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM IsOwner");
            return false;
        }
    }

    public static String checkAvailability(ClientServiceInteraction info){
        try{
            var st = conn.prepareStatement("SELECT startTime, endTime FROM serviceinstances WHERE (templateID IN (SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ?) OR clientID = ?) AND" + //
                    "((DATE(?) BETWEEN startDate AND endDate) AND ((TIME(?) BETWEEN startTime AND endTime) OR (TIME(?) BETWEEN startTime AND endTime))) ORDER BY startDate, startTime");
            st.setInt(1, info.getService().getProviderId());
            st.setInt(2, info.getService().getProviderId());
            //ISSO NAO ACOMODA PRA MULTIPLOS DIAS
            st.setString(3, info.getStartDate());
            st.setTime(4, info.getStartTime());
            st.setTime(5, info.getEndTime());
            var res = st.executeQuery();

            if(res.next()){
                String toReturn = "Unavailable that day between: \n"+res.getTime("startTime").toString() + 
                    " and "+res.getTime("endTime").toString();
                while (res.next()){
                    toReturn += "\n"+res.getTime("startTime").toString() + 
                    " and "+res.getTime("endTime").toString();
                }
                return toReturn;
            } else {
                return "AVA";
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("ERRO EM checkAvailavility");
            return "BROKE";
        }
    }

    //AUTH segurança
    public static boolean cancelRequest(int id){
        try{
            var st = conn.prepareStatement("DELETE FROM servicerequests WHERE serviceRequestID = ?");
            st.setInt(1, id);
            var res = st.executeUpdate();
            return res == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

}