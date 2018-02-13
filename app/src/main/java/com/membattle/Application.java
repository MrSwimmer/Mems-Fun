package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;

import com.membattle.api.APIService;


/**
 * Created by Севастьян on 16.10.2017.
 */

public class Application extends android.app.Application {
    private static Application singleton;
    private static final String APP_PREFERENCES = "settings";
    private static APIService service;
    String ADRESS;
    private static SharedPreferences mSettings;
    public static Application getInstance() {
        return singleton;

    }
    @Override
    public final void onCreate() {
        super.onCreate();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String access = mSettings.getString("token_refresh", null);
        if(access!=null){
            //Log.i("code", "myapp "+ access);
        }
        //mainRealm = Realm.getInstance(this);
    }
}
