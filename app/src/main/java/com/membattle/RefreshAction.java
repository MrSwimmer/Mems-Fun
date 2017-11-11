package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.membattle.API.APIService;
import com.membattle.API.SupportClasses.Responses.Exres;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Севастьян on 09.11.2017.
 */

public class RefreshAction {
    private static final String APP_PREFERENCES = "settings";
    private static APIService service;
    private static SharedPreferences mSettings;

    public RefreshAction(final String accessToken, final Context context) {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://78.24.223.212:3080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        JSONObject Text = null;
        String refresh_token = mSettings.getString("refresh_token", null);
        try {
            Text = new JSONObject("{\"token_refresh\":\""+refresh_token+"\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("code", Text.toString());
        RequestBody myreqbody = null;
        try {
            myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONObject(String.valueOf(Text))).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //RefreshTok tok = new RefreshTok(accessToken);
        Call<Exres> call = service.refreshbody(myreqbody);
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

                if(exres.getSuccess()) {
                    Log.i("code", "access " + exres.getToken_access());
                    Log.i("code", "refresh " + exres.getToken_refresh());
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("access_token", exres.getToken_access());
                    editor.putString("refresh_token", exres.getToken_refresh());
                    editor.apply();
                } else {
                    Log.i("code", "errefr "+exres.getError() + " " + exres.getMessage());
                }
            }
            @Override
            public void onFailure(Call<Exres> call, Throwable t) {
                Log.i("code", t.toString());
            }
        });
    }
}
