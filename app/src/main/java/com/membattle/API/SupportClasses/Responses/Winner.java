package com.membattle.API.SupportClasses.Responses;

/**
 * Created by Севастьян on 20.12.2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Winner {

    @SerializedName("items")
    @Expose
    private List<ItemW> items = null;

    public List<ItemW> getItems() {
        return items;
    }

    public void setItems(List<ItemW> items) {
        this.items = items;
    }

}