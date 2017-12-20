package com.membattle.SignInUp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.membattle.ButtonPlus;
import com.membattle.EditTextPlus;
import com.membattle.R;
import com.membattle.TextViewPlus;

public class LoginActivity extends Activity {
    private static final String APP_PREFERENCES = "settings";
    TextViewPlus title;
    EditTextPlus login, password;
    ButtonPlus enter, gotoReg;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gotoReg = (ButtonPlus) findViewById(R.id.login_but_reg);
        login = (EditTextPlus) findViewById(R.id.loginenter);
        password = (EditTextPlus) findViewById(R.id.passlogin);
        enter = (ButtonPlus) findViewById(R.id.butenter);
        title = (TextViewPlus) findViewById(R.id.titlelogin);

        /*String custom_font = "fonts/NAUTILUS.otf";
        Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);
        title.setTypeface(CF);

        String font_text = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
        Typeface CFt = Typeface.createFromAsset(getAssets(), font_text);
        enter.setTypeface(CFt);
        gotoReg.setTypeface(CFt);
        login.setTypeface(CFt);
        password.setTypeface(CFt);
        login.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);*/

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
    }
}
