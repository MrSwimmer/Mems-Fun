package com.membattle;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Севастьян on 16.10.2017.
 */

public class RealmObj extends RealmObject {
    @Required
    private String url1, url2;

    public boolean isWho() {
        return who;
    }

    public void setWho(boolean who) {
        this.who = who;
    }

    private boolean who;

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }
}
