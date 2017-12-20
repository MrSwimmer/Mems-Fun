package com.membattle.Game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;
import com.membattle.R;

public class PlayActivity extends Activity {
    static CircleMenu circleMenu;
    static FragmentTransaction fTrans;
    static int mode = 0;
    private String[] modes;
    public static int game_id, USER_ID;
    private SharedPreferences mSettings;
    ImageView photo, imc;
    public static final String APP_PREFERENCES = "settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        USER_ID = mSettings.getInt("id", 99);
        //mSocket.emit("CONNECT_TO_GAME", "{user_id:"+mSettings.getInt("id",999)+ ",game_id:number}");
        modes = getResources().getStringArray(R.array.modes_game);
        Intent intent = getIntent();
        boolean ison = intent.getBooleanExtra("ison", false);
        final int mode = intent.getIntExtra("mode", 0);
        fTrans = getFragmentManager().beginTransaction();
        if(ison) {
            game_id = 1234;
            Game play = new Game();
            fTrans.replace(R.id.play_cont, play);
        } else {
            RulesEvent rulesEvent = new RulesEvent();
            fTrans.replace(R.id.play_cont, rulesEvent);
        }
        circleMenu = (CircleMenu) findViewById(R.id.play_circle_menu);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                String hintText = menuButton.getHintText();
                fTrans = getFragmentManager().beginTransaction();
                switch (hintText) {
                    case "игра" :
                        Game play = new Game();
                        fTrans.replace(R.id.play_cont, play);
                        break;
                    case "правила" :
                        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                        builder.setTitle("Правила")
                                .setMessage(modes[mode])
                                .setCancelable(false)
                                .setPositiveButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        /*RulesEvent rules = new RulesEvent();
                        fTrans.replace(R.id.play_cont, rules);*/
                        break;
                    case "рейтинг" :
                        RateEvent rateEvent = new RateEvent();
                        fTrans.replace(R.id.play_cont, rateEvent);
                        //Toast.makeText(getApplicationContext(), "Упс, пока не работает ;)", Toast.LENGTH_SHORT).show();
                        break;
                    case "назад" :
                        finish();
                        break;
                    case "сетка" :
                        Toast.makeText(getApplicationContext(), "Упс, пока не работает ;)", Toast.LENGTH_SHORT).show();
                        break;
                }
                fTrans.commit();
            }
        });
        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {

            }

            @Override
            public void onMenuCollapsed() {

            }
        });
    }
}
