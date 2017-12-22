package com.membattle.API.SupportClasses.Responses;

/**
 * Created by Севастьян on 22.12.2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Memes {

    @SerializedName("leftMemeId")
    @Expose
    private Integer leftMemeId;
    @SerializedName("leftMemeImg")
    @Expose
    private String leftMemeImg;
    @SerializedName("rightMemeId")
    @Expose
    private Integer rightMemeId;
    @SerializedName("rightMemeImg")
    @Expose
    private String rightMemeImg;
    @SerializedName("rightLikes")
    @Expose
    private Integer rightLikes;
    @SerializedName("leftLikes")
    @Expose
    private Integer leftLikes;

    public Integer getLeftMemeId() {
        return leftMemeId;
    }

    public void setLeftMemeId(Integer leftMemeId) {
        this.leftMemeId = leftMemeId;
    }

    public String getLeftMemeImg() {
        return leftMemeImg;
    }

    public void setLeftMemeImg(String leftMemeImg) {
        this.leftMemeImg = leftMemeImg;
    }

    public Integer getRightMemeId() {
        return rightMemeId;
    }

    public void setRightMemeId(Integer rightMemeId) {
        this.rightMemeId = rightMemeId;
    }

    public String getRightMemeImg() {
        return rightMemeImg;
    }

    public void setRightMemeImg(String rightMemeImg) {
        this.rightMemeImg = rightMemeImg;
    }

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

}