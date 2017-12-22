package com.membattle.SignInUp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.membattle.API.APIService;
import com.membattle.API.SupportClasses.Requests.RegistrationUser;
import com.membattle.API.SupportClasses.Responses.Exres;
import com.membattle.WidgetPlus.ButtonPlus;
import com.membattle.WidgetPlus.EditTextPlus;
import com.membattle.R;
import com.membattle.WidgetPlus.TextViewPlus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegActivity extends Activity {
    ButtonPlus Registration;
    TextViewPlus textreg;
    EditTextPlus Login, Pass, Repeat, Email;
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Email = (EditTextPlus) findViewById(R.id.reg_email);
        Login = (EditTextPlus) findViewById(R.id.loginreg);
        Pass = (EditTextPlus) findViewById(R.id.passreg);
        Repeat = (EditTextPlus) findViewById(R.id.repeatpassreg);
        textreg = (TextViewPlus) findViewById(R.id.textReg);
        Registration = (ButtonPlus) findViewById(R.id.butreg);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Login.getText().toString().equals("")||Pass.getText().toString().equals("")||Repeat.getText().toString().equals("")||Email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_LONG).show();
                } else {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://dev.themezv.ru:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    service = retrofit.create(APIService.class);

                    RegistrationUser user = new RegistrationUser(Login.getText().toString(), Pass.getText().toString(), Email.getText().toString());
                    Call<Exres> call = service.registration(user);
                    if (Pass.getText().toString().equals(Repeat.getText().toString())) {
                        call.enqueue(new Callback<Exres>() {
                            @Override
                            public void onResponse(Call<Exres> call, Response<Exres> response) {
                                Exres exres = response.body();

                                if (!exres.getSuccess()) {
                                    Log.i("code", exres.getError());
                                    Toast.makeText(getApplicationContext(), "Такой пользователь уже существует!", Toast.LENGTH_SHORT).show();
                                } else {
                                    new LoginAction(Login.getText().toString(), Pass.getText().toString(), null, getApplicationContext());
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
            }
        });
    }


}
