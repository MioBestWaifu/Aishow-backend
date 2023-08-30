package com.aishow.backend.info;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;
import com.aishow.backend.modular.ModularInfo;

public class UserInformation {
    //Vai enviar senha no json tbm
    String email, password, gender,name,imageUrl;
    int userId, areaCode;
    float averageScore;
    String areaName;
    boolean providingService;
    Date birthday;
    ArrayList<ServiceBundle> serviceRecs;
    ArrayList<ServiceInformation> services;
    ArrayList<ReviewInfomation> reviews;
    
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
    public int getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(int area) {
        this.areaCode = area;
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
