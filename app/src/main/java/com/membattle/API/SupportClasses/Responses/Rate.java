package com.membattle.API.SupportClasses.Responses;

/**
 * Created by Севастьян on 21.12.2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rate {

    @SerializedName("userRating")
    @Expose
    private UserRating userRating;
    @SerializedName("globalRating")
    @Expose
    private List<GlobalRating> globalRating = null;

    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    public List<GlobalRating> getGlobalRating() {
        return globalRating;
    }

    public void setGlobalRating(List<GlobalRating> globalRating) {
        this.globalRating = globalRating;
    }

}