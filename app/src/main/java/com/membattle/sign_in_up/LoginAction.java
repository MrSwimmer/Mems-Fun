package com.membattle.sign_in_up;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.membattle.api.APIService;
import com.membattle.api.req.RegistrationUser;
import com.membattle.api.res.Exres;
import com.membattle.main_activity.MainActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAction {
    private static SharedPreferences mSettings;
    private static final String APP_PREFERENCES = "settings";

    public LoginAction(final String Login, String Pass, String Email, final Context context) {

        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mems.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        final RegistrationUser user = new RegistrationUser(Login, Pass, Email);
        Call<Exres> call = service.auth(user);
        call.enqueue(new Callback<Exres>() {
            @Override
            public void onResponse(Call<Exres> call, Response<Exres> response) {
                Exres exres = response.body();
                if(response.code()!=502) {
                    Log.i("code", response.code() + "");
                    if (exres == null) {
                        Log.i("code", "onResponse - Status : " + response.code());
                        Gson gson = new Gson();
                        TypeAdapter<Exres> adapter = gson.getAdapter(Exres.class);
                        try {
                            if (response.errorBody() != null) {
                                exres = adapter.fromJson(response.errorBody().string());
                                Log.i("code", "suc" + exres.getSuccess() + "");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (exres.getSuccess()) {
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putString("access_token", exres.getToken_access());
                        editor.putString("refresh_token", exres.getToken_refresh());
                        editor.putString("login", Login);
                        editor.putInt("coins", 0);
                        editor.putInt("id", exres.getId());
                        editor.apply();

                        Log.i("code", "id" + exres.getId());
                        Log.i("code", "putlog " + mSettings.getString("login", "nifiga"));

                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else {
                        Log.i("code", exres.getMessage() + " " + exres.getError());
                    }
                } else {
                    Toast.makeText(context.getApplicationContext(), "Ошибка подключения к серверу!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Exres> call, Throwable t) {
                Log.i("code", t.toString());
            }
        });
    }
}
