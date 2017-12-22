package com.membattle.API.SupportClasses.Responses;

/**
 * Created by Севастьян on 22.12.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Winner {

    @SerializedName("rightLikes")
    @Expose
    private Integer rightLikes;
    @SerializedName("leftLikes")
    @Expose
    private Integer leftLikes;
    @SerializedName("winner")
    @Expose
    private Integer winner;

    public Integer getRightLikes() {
        return rightLikes;
    }

    public void setRightLikes(Integer rightLikes) {
        this.rightLikes = rightLikes;
    }

    public Integer getLeftLikes() {
        return leftLikes;
    }

    public void setLeftLikes(Integer leftLikes) {
        this.leftLikes = leftLikes;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

}