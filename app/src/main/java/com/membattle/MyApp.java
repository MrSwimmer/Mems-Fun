package com.membattle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

import com.membattle.API.APIService;

import org.json.JSONObject;



/**
 * Created by Севастьян on 16.10.2017.
 */

public class MyApp extends Application {
    private static MyApp singleton;
    private static final String APP_PREFERENCES = "settings";
    private static APIService service;
    private static SharedPreferences mSettings;
    public static MyApp getInstance() {
        return singleton;
    }
    @Override
    public final void onCreate() {
        super.onCreate();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String access = mSettings.getString("token_refresh", null);
        if(access!=null){
            Log.i("code", "myapp "+ access);
        }
        //mainRealm = Realm.getInstance(this);
    }
}
