package com.membattle.API.SupportClasses.Requests;

/**
 * Created by Севастьян on 09.11.2017.
 */

public class Secret {
    private String secret, plus="garbage";

    public Secret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }
}
