package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.membattle.API.APIService;
import com.membattle.API.SupportClasses.Requests.Secret;
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

    public RefreshAction(final Context context) {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mems.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        final String refresh_token = mSettings.getString("refresh_token", null);
        final String access_token = mSettings.getString("access_token", null);
        final Secret secret = new Secret(refresh_token);
        Call<Exres> callsec = service.getsecret("Bearer " + access_token);
        callsec.enqueue(new Callback<Exres>() {
            @Override
            public void onResponse(Call<Exres> call, Response<Exres> response) {
                Exres exres = response.body();
                Log.i("code", "secr " + exres.getSuccess());
                if (!exres.getSuccess()) {
                    Call<Exres> callrefr = service.refresh("Bearer " + refresh_token, secret);
                    callrefr.enqueue(new Callback<Exres>() {
                        @Override
                        public void onResponse(Call<Exres> call, Response<Exres> response) {
                            Log.i("code", "coderesrefr" + response.code());
                            Exres exres = response.body();
                            assert exres != null;
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

                        @Override
                        public void onFailure(Call<Exres> call, Throwable t) {
                            Log.i("code", t.toString());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Exres> call, Throwable t) {

            }
        });
    }
}
