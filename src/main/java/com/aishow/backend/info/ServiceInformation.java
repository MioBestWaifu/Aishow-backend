package com.aishow.backend.info;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import com.aishow.backend.managers.Utils;
import com.aishow.backend.modular.ImageHandler;
import com.aishow.backend.modular.ModularInfo;

public class ServiceInformation {
    String serviceName,shortServiceName,description, providerName, providerUrl, providerImageUrl, providerArea, templateImageUrl, modText, catText;
    float costPerHour, costInNumber;
    float averageScore;
    int providerId, templateId, modality, category;
    boolean[] availableDays;
    String[] availableFroms, availableTos;
    ArrayList<ReviewInfomation> reviews;
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public String getProviderUrl() {
        return providerUrl;
    }
    public void setProviderUrl(String providerCode) {
        // this.providerUrl = "http://"+Utils.ipAddress+"/users/"+providerCode;
    }
    public String getProviderImageUrl() {
        return providerImageUrl;
    }
    public void setProviderImageUrl(String providerCode) {
        this.providerImageUrl = ModularInfo.BASE_IMAGE_URL+providerCode;
    }
    public float getCostPerHour() {
        return costPerHour;
    }
    public void setCostPerHour(float costPerHour) {
        this.costPerHour = costPerHour;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public int getProviderId() {
        return providerId;
    }
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
    public String getProviderArea() {
        return providerArea;
    }
    public void setProviderArea(String providerArea) {
        this.providerArea = providerArea;
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
    public int getTemplateId() {
        return templateId;
    }
    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    public String getTemplateImageUrl() {
        return templateImageUrl;
    }
    public void setTemplateImageUrl(String templateImageUrl) {
        this.templateImageUrl = ModularInfo.BASE_IMAGE_URL+"services/"+templateImageUrl;
    }
    public ArrayList<ReviewInfomation> getReviews() {
        return reviews;
    }
    public void setReviews(ArrayList<ReviewInfomation> reviews) {
        this.reviews = reviews;
        if (reviews.size()>0){
            int tot = 0;
            for (ReviewInfomation reviewInfomation : reviews) {
                tot += reviewInfomation.getScore();
            }
            var x = ((float)tot) / reviews.size();
            setAverageScore(x);
        }
    }

    public int getModality() {
        return modality;
    }

    public void setModality(int modality) {
        this.modality = modality;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean[] getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(boolean[] availableDays) {
        this.availableDays = availableDays;
    }

    public String[] getAvailableFroms() {
        return availableFroms;
    }

    public void setAvailableFroms(Time[] from) {
        this.availableFroms = new String[7];
        for(int a = 0; a<=6; a++){
            try{
                this.availableFroms[a] = from[a].toString();
            } catch (Exception ex){}
        }

    }

    public String[] getAvailableTos() {
        return availableTos;
    }

    public void setAvailableTos(Time[] to) {
        this.availableTos = new String[7];
        for(int a = 0; a<=6; a++){
            try{
                this.availableTos[a] = to[a].toString();
            } catch (Exception ex){}
        }
    }

    public String getModText() {
        return modText;
    }

    public void setModText(String modText) {
        this.modText = modText;
    }

    public String getCatText() {
        return catText;
    }

    public void setCatText(String catText) {
        this.catText = catText;
    }

    public String getShortServiceName() {
        return shortServiceName;
    }

    public void setShortServiceName(String shortServiceName) {
        this.shortServiceName = shortServiceName;
    }

    public float getCostInNumber() {
        return costInNumber;
    }

    public void setCostInNumber(float costInNumber) {
        this.costInNumber = costInNumber;
    }
    
    public Time[] getFromsAsTime(){
        Time[] tR= new Time[7];
        for (int a = 0; a<=6; a++){
            try{
                tR[a] = Utils.stringToTime(availableFroms[a]);
            } catch (Exception ex){}
        }

        return tR;
    }

    public Time[] getTosAsTime(){
        Time[] tR= new Time[7];
        for (int a = 0; a<=6; a++){
            try{
                tR[a] = Utils.stringToTime(availableTos[a]);
            } catch (Exception ex){}
        }

        return tR;
    }
}
