package com.membattle.API.SupportClasses.Requests;

/**
 * Created by Севастьян on 09.11.2017.
 */

public class Id {
    private int id;
    String garbage="garbage";

    public Id(int id) {
        this.id = id;
    }

    public int getSecret() {
        return id;
    }

    public void setSecret(int secret) {
        this.id = secret;
    }

}
