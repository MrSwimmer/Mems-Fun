package com.membattle.API.SupportClasses.Responses;

/**
 * Created by Севастьян on 20.12.2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemW {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("likes")
    @Expose
    private Integer likes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

}