package com.membattle;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Севастьян on 16.10.2017.
 */

public class MyApp extends Application {
    private static MyApp singleton;
    //public Realm mainRealm;
    public static MyApp getInstance() {
        return singleton;
    }
    @Override
    public final void onCreate() {
        super.onCreate();
        singleton = this;
        //mainRealm = Realm.getInstance(this);
    }
}
