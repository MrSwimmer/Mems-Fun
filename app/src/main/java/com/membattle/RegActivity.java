package com.membattle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.membattle.API.APIService;
import com.membattle.API.SupportClasses.Requests.RegistrationUser;
import com.membattle.API.SupportClasses.Responses.Exres;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegActivity extends Activity {
    Button Registration;
    private OkHttpClient client;
    TextView textreg;
    private SharedPreferences mSettings;
    EditText Login, Pass, Repeat, Email;
    public static final String APP_PREFERENCES = "mysettings";
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Email = (EditText) findViewById(R.id.reg_email);
        Login = (EditText) findViewById(R.id.loginreg);
        Pass = (EditText) findViewById(R.id.passreg);
        Repeat = (EditText) findViewById(R.id.repeatpassreg);
        textreg = (TextView) findViewById(R.id.textReg);
        Registration = (Button) findViewById(R.id.butreg);
        String custom_font = "fonts/Mr_Lonely.otf";
        Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);
        textreg.setTypeface(CF);
        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor editor = mSettings.edit();
//                editor.putString("login", Login.getText().toString());
//                editor.apply();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://78.24.223.212:3080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(APIService.class);

                    RegistrationUser user = new RegistrationUser(Login.getText().toString(), Pass.getText().toString(), Email.getText().toString());
                    Call<Exres> call = service.registration(user);
                if(Pass.getText().toString().equals(Repeat.getText().toString())){
                    call.enqueue(new Callback<Exres>() {
                        @Override
                        public void onResponse(Call<Exres> call, Response<Exres> response) {
                            Exres exres = response.body();

                            if(!exres.getSuccess()){
                                Log.i("code", exres.getError());
                                Toast.makeText(getApplicationContext(), "Такой пользователь уже существует!", Toast.LENGTH_SHORT).show();
                            } else {
                                new LoginAction(Login.getText().toString(), Pass.getText().toString(), null, getApplicationContext());
                                /*SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("login", Login.getText().toString());
                                editor.putInt("coins", 0);
                                editor.apply();
                                Intent i = new Intent(RegActivity.this, NavigationActivity.class);
                                startActivity(i);
                                finish();*/
                            }
                        }

                        @Override
                        public void onFailure(Call<Exres> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Пароли отличаются!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
