package com.membattle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.membattle.API.APIService;

public class LoginActivity extends Activity {
    private static final String APP_PREFERENCES = "settings";
    private static APIService service;
    private static SharedPreferences mSettings;
    TextView title;
    EditText login, password;
    Button enter, gotoReg;
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
        String custom_font = "fonts/NAUTILUS.otf";
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
                if(login.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_LONG).show();
                } else {
                    new LoginAction(login.getText().toString(), password.getText().toString(), null, getApplicationContext());
                }
            }
        });

        String font_text = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
        Typeface CFt = Typeface.createFromAsset(getAssets(), font_text);
        enter.setTypeface(CFt);
        gotoReg.setTypeface(CFt);
        login.setTypeface(CFt);
        password.setTypeface(CFt);
        login.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }
}
