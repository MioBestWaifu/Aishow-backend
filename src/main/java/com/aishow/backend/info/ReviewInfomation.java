package com.aishow.backend.info;

import java.util.HashMap;

import com.aishow.backend.managers.Utils;

public class ReviewInfomation {
    UserInformation reviewer;
    int score,type;
    //0 = User, 1 = Service
    String comment;


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
