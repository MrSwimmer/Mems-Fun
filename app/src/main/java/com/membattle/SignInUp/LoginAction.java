package com.membattle.SignInUp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.membattle.API.APIService;
import com.membattle.API.SupportClasses.Requests.RegistrationUser;
import com.membattle.API.SupportClasses.Responses.Exres;
import com.membattle.MainActivity.MainActivity;
import com.membattle.R;

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

        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.URL))
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
                //Log.i("code", "refresh_token " + exres.getToken_access());
                try {
                    if(exres.getSuccess()) {

                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putString("access_token", exres.getToken_access());
                        editor.putString("refresh_token", exres.getToken_refresh());
                        editor.putString("login", Login);
                        editor.putInt("coins", 0);
                        editor.putInt("id", exres.getId());
                        editor.apply();

                        Log.i("code", "id"+exres.getId());
                        Log.i("code", "putlog "+mSettings.getString("login", "nifiga"));

                        Intent i = new Intent( context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Неверное имя или пароль!", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<Exres> call, Throwable t) {
                Log.i("code", t.toString());
            }
        });
    }
}
