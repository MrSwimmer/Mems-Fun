package com.membattle.NewNavigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;
import com.membattle.Play;
import com.membattle.R;

public class PlayActivity extends Activity {
    static CircleMenu circleMenu;
    static FragmentTransaction fTrans;
    static int mode = 0;
    private String[] modes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        modes = getResources().getStringArray(R.array.modes_game);
        Intent intent = getIntent();
        boolean ison = intent.getBooleanExtra("ison", false);
        final int mode = intent.getIntExtra("mode", 0);
        fTrans = getFragmentManager().beginTransaction();
        if(ison) {
            Play play = new Play();
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
                        Play play = new Play();
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
                        Toast.makeText(getApplicationContext(), "Упс, пока не работает ;)", Toast.LENGTH_SHORT).show();
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
