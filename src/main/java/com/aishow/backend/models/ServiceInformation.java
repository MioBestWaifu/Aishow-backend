package com.aishow.backend.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import com.aishow.backend.modular.ModularInfo;
import com.aishow.backend.utils.Utils;

public class ServiceInformation {
    //TODO #25 dar cabo dessas informações do user e criar um objeto UserInformation no lugar
    String serviceName,shortServiceName,description, templateImageUrl, modText, catText;
    float costPerHour;
    float averageScore;
    int templateId, modality, category;
    boolean[] availableDays;
    String[] availableFroms, availableTos;
    ArrayList<ReviewInfomation> reviews;
    UserInformation provider;

    public static ServiceInformation fromResultSet(ResultSet rs){
        ServiceInformation toReturn = new ServiceInformation();
        Class[] paramTypes = new Class[]{String.class};
        toReturn.serviceName =(String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"serviceName"});
        toReturn.description =(String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"description"});
        toReturn.templateImageUrl =ModularInfo.BASE_IMAGE_URL + "services/"+ (String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"templateImageUrl"});
        toReturn.costPerHour =(float) Utils.runMethodReflection(rs, "getFloat", paramTypes, new Object[]{"costPerHour"});
        toReturn.provider = new UserInformation();
        toReturn.provider.userId =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"idProvider"});
        toReturn.templateId =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"idServiceTemplates"});
        toReturn.modality =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"serviceModality"});
        toReturn.category =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"serviceCategory"});
        return toReturn;
    }

    public void setAvailablity(ResultSet rs) throws SQLException{
        boolean[] days = new boolean[7];
        Time[] from = new Time[7];
        Time[] to = new Time[7];
        int i;
        while(rs.next()){
            i = rs.getInt("weekday");
            days[i] = true;
            from[i] = rs.getTime("startHour");
            to[i] = rs.getTime("endHour");

            setAvailableDays(days);
            setAvailableFromsFromTime(from);
            setAvailableTosFromTime(to);  
        }
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
        if (reviews == null)
            return;
        if (reviews.size()>0){
            float tot = 0;
            for (ReviewInfomation reviewInfomation : reviews) {
                tot += reviewInfomation.getScore();
            }
            System.out.println("SET SCORE");
            System.out.println(tot);
            float x = tot / reviews.size();
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

    @com.fasterxml.jackson.annotation.JsonSetter
    public void setAvailableFroms(String[] froms){
        availableFroms = froms;
    }

    @com.fasterxml.jackson.annotation.JsonSetter
    public void setAvailableTos(String[] tos){
        availableTos = tos;
    }

    public void setAvailableFromsFromTime(Time[] from) {
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

    public void setAvailableTosFromTime(Time[] to) {
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

    public UserInformation getProvider() {
        return provider;
    }

    public void setProvider(UserInformation provider) {
        this.provider = provider;
    }

    
}
