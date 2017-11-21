package com.membattle.NewNavigation;

import android.app.Activity;
import android.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;
import com.membattle.MyApp;
import com.membattle.Profile;
import com.membattle.R;
import com.membattle.Rules;
import com.membattle.Settings;

public class MainActivity extends Activity {
    ModesFragment mModesFragment;
    CircleMenu circleMenu;
    static FragmentTransaction fTrans;
    private static SharedPreferences mSettings;
    private static final String APP_PREFERENCES = "settings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mModesFragment = new ModesFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.main_cont, mModesFragment);
        fTrans.commit();
        final Rules rules = new Rules();
        final Settings settings = new Settings();
        final Profile profile = new Profile();

        circleMenu = (CircleMenu) findViewById(R.id.circleMenu);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                String hintText = menuButton.getHintText();
                fTrans = getFragmentManager().beginTransaction();
                switch (hintText) {
                    case "игра" :
                        fTrans.replace(R.id.main_cont, mModesFragment); break;
                    case "помощь" :
                        fTrans.replace(R.id.main_cont, rules); break;
                    case "настройки" :
                        fTrans.replace(R.id.main_cont, settings); break;
                    case "профиль" :
                        fTrans.replace(R.id.main_cont, profile); break;
                    case "магазин" :
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
