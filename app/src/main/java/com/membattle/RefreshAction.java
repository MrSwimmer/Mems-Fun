package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.membattle.API.APIService;
import com.membattle.API.SupportClasses.Requests.Secret;
import com.membattle.API.SupportClasses.Responses.Exres;
import com.membattle.API.SupportClasses.Responses.ValidToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    public RefreshAction(final Context context) {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mems.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        final String refresh_token = mSettings.getString("refresh_token", null);
        String access_token = mSettings.getString("access_token", null);
        Log.i("code", "acc "+ access_token);
        final Secret secret = new Secret(refresh_token);
        Call<ValidToken> callsec = service.getsecret("Bearer " + access_token);
        callsec.enqueue(new Callback<ValidToken>() {
            @Override
            public void onResponse(Call<ValidToken> call, Response<ValidToken> response) {
                ValidToken validToken = response.body();
                if(validToken==null) {
                    Log.i("code", "onResponse - Status : " + response.code());
                    Gson gson = new Gson();
                    TypeAdapter<ValidToken> adapter = gson.getAdapter(ValidToken.class);
                    try {
                        if (response.errorBody() != null) {
                            validToken = adapter.fromJson(response.errorBody().string());
                            Log.i("code", "suc" + validToken.getSuccess() + "");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("code", "secr " + validToken.getSuccess());
                if (!validToken.getSuccess()) {
                    Call<Exres> callrefr = service.refresh("Bearer " + refresh_token, secret);
                    callrefr.enqueue(new Callback<Exres>() {
                        @Override
                        public void onResponse(Call<Exres> call, Response<Exres> response) {
                            Log.i("code", "coderesrefr" + response.code());
                            Exres exres = response.body();
                            if(exres!=null) {
                                if (exres.getSuccess()) {
                                    Log.i("code", "access " + exres.getToken_access());
                                    Log.i("code", "refresh " + exres.getToken_refresh());
                                    SharedPreferences.Editor editor = mSettings.edit();
                                    editor.putString("access_token", exres.getToken_access());
                                    editor.putString("refresh_token", exres.getToken_refresh());
                                    editor.apply();
                                } else {
                                    Log.i("code", "errefr " + exres.getError() + " " + exres.getMessage());
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Exres> call, Throwable t) {
                            Log.i("code", t.toString());
                        }
                    });
                } else {

                }
            }
            @Override
            public void onFailure(Call<ValidToken> call, Throwable t) {

            }
        });
    }
}
