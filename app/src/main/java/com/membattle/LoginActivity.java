package com.membattle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.membattle.API.APIService;
import com.membattle.API.Exres;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {
    private static final String APP_PREFERENCES = "settings";
    private static APIService service;
    TextView title;
    EditText login, password;
    Button enter, gotoReg;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gotoReg = (Button) findViewById(R.id.login_but_reg);
        login = (EditText) findViewById(R.id.loginenter);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        password = (EditText) findViewById(R.id.passlogin);
        enter = (Button) findViewById(R.id.butenter);
        title = (TextView) findViewById(R.id.titlelogin);
        String custom_font = "fonts/Mr_Lonely.otf";
        Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);
        title.setTypeface(CF);
        gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(i);
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(login.getText().toString(), password.getText().toString(), null);
            }
        });
    }
    public void login(final String Login, String Pass, String Email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://78.24.223.212:3080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        RegistrationUser user = new RegistrationUser(Login, Pass, Email);
        Call<Exres> call = service.auth(user);
        call.enqueue(new Callback<Exres>() {

            @Override
            public void onResponse(Call<Exres> call, Response<Exres> response) {
                Exres exres = response.body();
                Log.i("code", "access: "+exres.getToken_access());
                Log.i("code", "refr: "+exres.getToken_refresh());
                Log.i("code", "er: "+exres.getError());
                Log.i("code", "mes: "+exres.getMessage());
                Log.i("code", "suc: "+exres.getSuccess());
                String forAccTok = null, forRefrTok = null;
                try {
                    JWTUtils.decoded(exres.getToken_access(), forAccTok);
                    JWTUtils.decoded(exres.getToken_refresh(), forRefrTok);
                } catch (Exception e) {
                    Log.i("code", "lolnet");
                }

                            /*Exres user = response.body();
                            if(response.code()==403){
                                Toast.makeText(getApplicationContext(), "Ошибка входа!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Toast.makeText(getApplicationContext(), "Ошибка входа!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show();

                                Log.i("code", "coins: "+user.getCoins());

                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("login", login.getText().toString());
                                editor.putInt("coins", user.getCoins());
                                editor.apply();

                                Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
                                i.putExtra("login", login.getText().toString());
                                i.putExtra("coins", user.getCoins());
                                startActivity(i);
                                finish();
                            }*/
                //Log.i("code", user.getMessage());
            }

            @Override
            public void onFailure(Call<Exres> call, Throwable t) {
                Log.i("code", t.toString());
            }
        });

    }

}
