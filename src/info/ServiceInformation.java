package info;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import managers.Utils;

public class ServiceInformation {
    private String serviceName,shortServiceName,description, providerName, providerUrl, providerImageUrl, providerArea, templateImageUrl, modText, catText;
    private float costPerHour, costInNumber;
    private float averageScore;
    private int providerId, templateId, modality, category;
    private boolean[] availableDays;
    private Time[] availableFroms, availableTos;
    private ArrayList<ReviewInfomation> reviews;
    
    public ServiceInformation(){}
    
    public ServiceInformation(String json){
        var map = Utils.mapJson(json,this.getClass());
        
        if(map.containsKey("costInNumber")){
            costInNumber = Float.parseFloat(map.get("costInNumber"));
        }

        if(map.containsKey("templateId")){
            templateId = Integer.parseInt(map.get("templateId"));
        }

        if(map.containsKey("providerId")){
            providerId = Integer.parseInt(map.get("providerId"));
        }

        if(map.containsKey("modality")){
            modality = Integer.parseInt(map.get("modality"));
        }

        if(map.containsKey("category")){
            category = Integer.parseInt(map.get("category"));
        }

        if(map.containsKey("serviceName")){
            serviceName = map.get("serviceName");
        }

        if(map.containsKey("description")){
            description = map.get("description");
        }

        if(map.containsKey("costPerHour")){
            try{
            costPerHour = Float.parseFloat(map.get("costPerHour"));
            } catch (Exception ex){

            }
        }

        if(map.containsKey("availableDays")){
            availableDays = new boolean[7];
            String[] s = Utils.breakJsonArray(map.get("availableDays"));
            for (int a = 0; a<=6;a++){
                availableDays[a] = Boolean.parseBoolean(s[a]);
            }
        }

        if(map.containsKey("availableFroms")){
            availableFroms = new Time[7];
            String[] s = Utils.breakJsonArray(map.get("availableFroms"));
            for (int a = 0; a<=6;a++){
                if (!s[a].equals("null"))
                    s[a] = s[a]+":00";
                else 
                    s[a] = "00:00:00";
                availableFroms[a] = Time.valueOf((s[a]));
            }
        }

        if(map.containsKey("availableTos")){
            availableTos = new Time[7];
            String[] s = Utils.breakJsonArray(map.get("availableTos"));
            for (int a = 0; a<=6;a++){
                if (!s[a].equals("null"))
                    s[a] = s[a]+":00";
                else 
                    s[a] = "00:00:00";
                availableTos[a] = Time.valueOf((s[a]));
            }
        }
    }

    public String toJson(){
        HashMap<String,String> mapFields = new HashMap<>();
        mapFields.put("serviceName", serviceName);
        mapFields.put("modality", Integer.toString(modality));
        mapFields.put("category", Integer.toString(category));
        mapFields.put("templateId", Integer.toString(templateId));
        mapFields.put("providerId", Integer.toString(providerId));
        if (serviceName.length()>25)
            mapFields.put("shortServiceName", serviceName.substring(0, 23)+"...");
        else
            mapFields.put("shortServiceName", serviceName);
        if (description.isBlank()){
                description = "DESC";
        }
        if (catText != null)
            mapFields.put("catText", catText);
        if (modText != null)
            mapFields.put("modText", modText);
        mapFields.put("averageScore", Float.toString(averageScore));
        if (! (providerArea == null || providerArea.isBlank()))
            mapFields.put("providerArea", providerArea);
        mapFields.put("description", description);
        if (! (providerName == null || providerName.isBlank()))
            mapFields.put("providerName", providerName);
        if (! (providerUrl == null || providerUrl.isBlank()))
            mapFields.put("providerUrl", providerUrl);
        if (! (templateImageUrl == null || templateImageUrl.isBlank()))
            mapFields.put("templateImageUrl", templateImageUrl);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(symbols);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        if (! (providerImageUrl == null || providerImageUrl.isBlank()))
            mapFields.put("providerImageUrl", providerImageUrl);
        if (!(reviews == null || reviews.size() == 0)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (ReviewInfomation si : reviews){
                toJoin.add(si.toJson());
            }
            mapFields.put("reviews", Utils.joinJsonArray(toJoin));
        }
        
        if (!(availableDays == null)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (boolean si : availableDays){
                toJoin.add(Boolean.toString(si));
            }
            mapFields.put("availableDays", Utils.joinJsonArray(toJoin));
        }

        if (!(availableFroms == null)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (Time si: availableFroms){
                if(si == null){
                    toJoin.add("\"1970-01-01:00:00:00\"");
                } else {
                    toJoin.add("\"1970-01-01:"+si.toString()+"\"");
                }
            }
            mapFields.put("availableFroms", Utils.joinJsonArray(toJoin));
        }

        if (!(availableTos == null)){
            ArrayList<String> toJoin = new ArrayList<String>();
            for (Time si : availableTos){
                if(si == null){
                    toJoin.add("\"1970-01-01:00:00:00\"");
                } else {
                toJoin.add("\"1970-01-01:"+si.toString()+"\"");
                }
            }
            mapFields.put("availableTos", Utils.joinJsonArray(toJoin));
        }
        mapFields.put("costPerHour", "R$"+format.format(costPerHour)+"/hr");
        mapFields.put("costInNumber", Float.toString(costPerHour));
        return Utils.toJson(mapFields);
    }

    public static String JsonifyArray(ArrayList<ServiceInformation> toUse){
        ArrayList<String> toJoin = new ArrayList<String>();
            for (ServiceInformation si : toUse){
                toJoin.add(si.toJson());
            }
            return Utils.joinJsonArray(toJoin);
    }
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
        this.providerUrl = "http://"+Utils.ipAddress+"/users/"+providerCode;
    }
    public String getProviderImageUrl() {
        return providerImageUrl;
    }
    public void setProviderImageUrl(String providerCode) {
        this.providerImageUrl = "http://"+Utils.ipAddress+"/images/"+providerCode;
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
        this.templateImageUrl = "http://"+Utils.ipAddress+"/images/services/"+templateImageUrl;
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

    public Time[] getAvailableFroms() {
        return availableFroms;
    }

    public void setAvailableFroms(Time[] from) {
        this.availableFroms = from;
    }

    public Time[] getAvailableTos() {
        return availableTos;
    }

    public void setAvailableTos(Time[] to) {
        this.availableTos = to;
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
     public void cu(){}
}
