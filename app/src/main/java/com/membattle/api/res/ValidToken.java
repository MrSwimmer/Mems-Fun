package com.membattle.api.res;

/**
 * Created by Севастьян on 30.12.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidToken {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("error")
    @Expose
    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}