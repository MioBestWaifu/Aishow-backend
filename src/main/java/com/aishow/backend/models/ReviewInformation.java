package com.aishow.backend.models;

import java.sql.ResultSet;
import java.util.HashMap;

import com.aishow.backend.utils.Utils;

public class ReviewInformation {
    UserInformation reviewer;
    int score,type;
    //0 = User, 1 = Service
    String comment;

    public static ReviewInformation fromResultSet(ResultSet rs, int type){
        ReviewInformation toReturn = new ReviewInformation();
        Class[] paramTypes = new Class[]{String.class};
        toReturn.reviewer = new UserInformation();
        toReturn.reviewer.userId =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"idclient"});
        toReturn.score =(int) Utils.runMethodReflection(rs, "getInt", paramTypes, new Object[]{"score"});
        toReturn.comment =(String) Utils.runMethodReflection(rs, "getString", paramTypes, new Object[]{"comment"});
        toReturn.type = type;
        return toReturn;
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


    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    

    
    
}
