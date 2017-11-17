package com.membattle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.membattle.API.APIService;
import com.membattle.API.SupportClasses.Requests.RegistrationUser;
import com.membattle.API.SupportClasses.Responses.Exres;
import com.membattle.NewNavigation.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Севастьян on 09.11.2017.
 */

public class LoginAction {
    private static SharedPreferences mSettings;
    private static final String APP_PREFERENCES = "settings";
    private static APIService service;


    public LoginAction(final String Login, String Pass, String Email, final Context context) {

        mSettings = context.getApplicationContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
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
                Log.i("code", "refresh_token " + exres.getToken_access());
                if(exres.getSuccess()) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("access_token", exres.getToken_access());
                    editor.putString("refresh_token", exres.getToken_refresh());
                    editor.putString("login", Login);
                    editor.putInt("coins", 0);
                    editor.apply();
                    Log.i("code", "putlog "+mSettings.getString("login", "nifiga"));
                    //editor.putInt("coins", user.getCoins());

                    Intent i = new Intent( context, MainActivity.class);
                    /*i.putExtra("login", Login);
                    i.putExtra("coins", 0);
                    i.putExtra("access_token", exres.getToken_access());
                    i.putExtra("refresh_token", exres.getToken_refresh());*/
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
