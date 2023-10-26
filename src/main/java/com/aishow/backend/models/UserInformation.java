package com.aishow.backend.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.modular.ModularInfo;
import com.aishow.backend.utils.Utils;

//TODO #29 remover redundancia

public class UserInformation {
    //Vai enviar senha no json tbm
    String email, password, gender,name,imageUrl;
    int userId;
    float averageScore;
    boolean providingService;
    Date birthday;
    GenericInformation area;
    public GenericInformation getArea() {
        return area;
    }

    public void setArea(GenericInformation area) {
        this.area = area;
    }
    ArrayList<ServiceBundle> serviceRecs;
    ArrayList<ServiceInformation> services;
    ArrayList<ReviewInfomation> reviews;

    public static UserInformation fromResultSet(ResultSet rs){
        UserInformation toReturn = new UserInformation();
        Class[] paramTypes = new Class[]{String.class};

        toReturn.email =(String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"email"});
        toReturn.password =(String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"password"});
        toReturn.gender =(String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"gender"});
        toReturn.name =(String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"name"});
        toReturn.imageUrl =ModularInfo.BASE_IMAGE_URL + (String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"profileUrl"});

        toReturn.userId =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"idUser"});
        toReturn.area = new GenericInformation();
        toReturn.area.Id =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"area"});

        toReturn.birthday =(Date) Utils.runMethodReflection(rs, "getDate", paramTypes, new Object[]{"birthday"});

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
        this.imageUrl = ModularInfo.BASE_IMAGE_URL+userCode;
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
