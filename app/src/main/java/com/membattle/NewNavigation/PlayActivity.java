package com.membattle.NewNavigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;
import com.membattle.Play;
import com.membattle.R;
import com.membattle.Rules;
import com.membattle.RulesEvent;

public class PlayActivity extends Activity {
    static CircleMenu circleMenu;
    static FragmentTransaction fTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        Intent intent = getIntent();
        boolean ison = intent.getBooleanExtra("ison", false);
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
                    case "правила" :
                        RulesEvent rules = new RulesEvent();
                        fTrans.replace(R.id.play_cont, rules);
                    case "рейтинг" :

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
