package com.membattle.sign_in_up;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.membattle.widget_plus.ButtonPlus;
import com.membattle.widget_plus.EditTextPlus;
import com.membattle.R;
import com.membattle.widget_plus.TextViewPlus;

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
