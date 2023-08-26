package com.aishow.backend.info;

import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ReviewInfomation {
    UserInformation reviewer;
    int score,type;
    //0 = User, 1 = Service
    String comment;

    public ReviewInfomation(){}

    public ReviewInfomation(String json){
        var map = Utils.mapJson(json,this.getClass());
        if (map.containsKey("score")){
            score = Integer.parseInt(map.get("score"));
        }
        if (map.containsKey("type")){
            type = Integer.parseInt(map.get("type"));
        }
        if(map.containsKey("comment")){
            comment = map.get("comment");
        }
        if(map.containsKey("reviewerCode")){
            UserInformation toadd = new UserInformation();
            toadd.setUserId(Integer.parseInt(map.get("reviewerCode")));
            reviewer = toadd;
        }
    }

    public String toJson(){
        HashMap<String,String> toMap = new HashMap<>();
        if (!(reviewer == null)){
            toMap.put("reviewer", reviewer.toJson());
        }
        if (!(comment == null || comment.isBlank())){
            toMap.put("comment", comment);
        }
         toMap.put("score", Integer.toString(score));

         return Utils.toJson(toMap);
    }

    public UserInformation getReviewer() {
        return reviewer;
    }
    public int getScore() {
        return score;
    }
    public String getComment() {
        return comment;
    }
    public void setReviewer(UserInformation reviewer) {
        this.reviewer = reviewer;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    
}
