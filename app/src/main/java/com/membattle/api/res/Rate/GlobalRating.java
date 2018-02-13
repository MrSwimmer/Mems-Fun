package com.membattle.api.res.Rate;

/**
 * Created by Севастьян on 21.12.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalRating {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("coins")
    @Expose
    private int coins;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

}