package com.aishow.backend.info;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class UserInformation {
    String email, password, gender,name,imageUrl;
    int userId, area;
    float averageScore;
    String areaName;
    boolean providingService;
    Date birthday;
    ArrayList<ServiceBundle> serviceRecs;
    ArrayList<ServiceInformation> services;
    ArrayList<ReviewInfomation> reviews;
    public UserInformation(){

    }
    public UserInformation(String json){
    var map = Utils.mapJson(json, this.getClass());
    if (map.containsKey("email"))
        email = map.get("email");
    if (map.containsKey("name"))
        name = map.get("name");
    else if (map.containsKey("username"))
        name = map.get("username");
    if (map.containsKey("password"))
        password = map.get("password");
    if (map.containsKey("userId"))
        userId = Integer.parseInt(map.get("userId"));
    if (map.containsKey("gender"))
        gender = map.get("gender");
    if (map.containsKey("area"))
        area = Integer.parseInt(map.get("area"));
    if (map.containsKey("providingService"))
        providingService = Boolean.parseBoolean(map.get("email"));
    if (map.containsKey("birthday"))
        birthday = Date.valueOf(map.get("birthday"));
    }

    public String toJson(){
        HashMap<String,String> mapFields = new HashMap<>();
        mapFields.put("userID", Integer.toString(userId));
        if (email != null)
            mapFields.put("email", email);
        if (imageUrl != null)
            mapFields.put("imageUrl", imageUrl);
        if (name != null)
            mapFields.put("name", name);
        //Essa porra é confusa
        if (areaName != null)
            mapFields.put("area", areaName);
        if (gender != null)
            mapFields.put("gender", gender);
        mapFields.put("providingService", Boolean.toString(providingService));
        if (birthday != null)
            mapFields.put("birthday", birthday.toString());
        if (averageScore != 0f)
            mapFields.put("averageScore", Float.toString(averageScore));
        if (!(serviceRecs == null || serviceRecs.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ServiceBundle si : serviceRecs){
                toJoin.add(si.toJson());
            }
            mapFields.put("serviceRecs", Utils.joinJsonArray(toJoin));
        }
        if (!(services == null || services.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ServiceInformation si : services){
                toJoin.add(si.toJson());
            }
            mapFields.put("services", Utils.joinJsonArray(toJoin));
        }
        if (!(reviews == null || reviews.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ReviewInfomation si : reviews){
                toJoin.add(si.toJson());
            }
            mapFields.put("reviews", Utils.joinJsonArray(toJoin));
        }
        String toReturn = Utils.toJson(mapFields);
        
        return toReturn;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String genre) {
        this.gender = genre;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public boolean isProvidingService() {
        return providingService;
    }

    public void setProvidingService(boolean providingService) {
        this.providingService = providingService;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<ServiceBundle> getServiceRecs() {
        return serviceRecs;
    }
    public void setServiceRecs(ArrayList<ServiceBundle> reccomendations) {
        this.serviceRecs = reccomendations;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String userCode) {
        this.imageUrl = "http://localhost:8080/images/"+userCode;
    }
    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        this.area = area;
    }
    public ArrayList<ServiceInformation> getServices() {
        return services;
    }
    public void setServices(ArrayList<ServiceInformation> services) {
        this.services = services;
    }
    public ArrayList<ReviewInfomation> getReviews() {
        return reviews;
    }
    public void setReviews(ArrayList<ReviewInfomation> reviews) {
        this.reviews = reviews;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    public float getAverageScore() {
        return averageScore;
    }
    public void setAverageScore(float scoreAverage) {
        DecimalFormat format = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(1);   
        this.averageScore = Float.parseFloat(format.format(scoreAverage));
    }
    
}
