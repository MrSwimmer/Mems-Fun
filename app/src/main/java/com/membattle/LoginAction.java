package com.membattle;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.membattle.API.APIService;
import com.membattle.API.Exres;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Севастьян on 09.11.2017.
 */

public class LoginAction {
    private static final String APP_PREFERENCES = "settings";
    private static APIService service;
    private static SharedPreferences mSettings;

    public LoginAction(final String Login, String Pass, String Email, final Context context) {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://78.24.223.212:3080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        final RegistrationUser user = new RegistrationUser(Login, Pass, Email);
        Call<Exres> call = service.auth(user);
        call.enqueue(new Callback<Exres>() {

            @Override
            public void onResponse(Call<Exres> call, Response<Exres> response) {
                Exres exres = response.body();
                /*String forAccTok = null, forRefrTok = null;
                try {
                    JWTUtils.decoded(exres.getToken_access(), forAccTok);
                    JWTUtils.decoded(exres.getToken_refresh(), forRefrTok);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JSONObject token_refresh = null;
                try {
                    token_refresh = new JSONObject(forAccTok);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                Log.i("code", "token_refresh " + exres.getToken_access());
                if(exres.getSuccess()) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("access_token", exres.getToken_access());
                    editor.putString("token_refresh", exres.getToken_refresh());
                    editor.putString("login", Login);
                    editor.putInt("coins", 0);
                    //editor.putInt("coins", user.getCoins());
                    editor.apply();
                    Intent i = new Intent( context, NavigationActivity.class);
                    i.putExtra("login", Login);
                    //i.putExtra("coins", user.getCoins());
                    i.putExtra("coins", 0);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
            @Override
            public void onFailure(Call<Exres> call, Throwable t) {
                Log.i("code", t.toString());
            }
        });
    }
}
