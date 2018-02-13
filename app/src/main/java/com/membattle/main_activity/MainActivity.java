package com.membattle.main_activity;

import android.app.Activity;
import android.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.membattle.game.ModesFragment;
import com.membattle.game.RateEvent;
import com.membattle.R;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

public class MainActivity extends Activity {
    ModesFragment modes;
    CircleMenu circleMenu;
    public static FragmentTransaction fTrans;
    private static SharedPreferences mSettings;
    private static final String APP_PREFERENCES = "settings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        modes = new ModesFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.main_cont, modes);
        fTrans.commit();

        final Rules rules = new Rules();
        final Settings settings = new Settings();
        final Profile profile = new Profile();
        final RateEvent rate = new RateEvent();

        int masdrawable[] = new int[] {R.drawable.ic_game, R.drawable.ic_action_name, R.drawable.ic_shop, R.drawable.ic_help, R.drawable.ic_prf, R.drawable.ic_rate};
        String masstr[] = new String[] {};
        masstr = getResources().getStringArray(R.array.menu_names);

        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_1);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            final int finalI = i;
            bmb.addBuilder(new TextOutsideCircleButton.Builder()
                    .normalImageRes(masdrawable[i])
                    .normalText(masstr[i])
                    .imagePadding(new Rect(10, 10, 10, 10))
                    .textSize(14)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            fTrans = getFragmentManager().beginTransaction();
                            switch (finalI) {
                                case 0 :
                                    fTrans.replace(R.id.main_cont, modes); break;
                                case 1 :
                                    fTrans.replace(R.id.main_cont, settings); break;
                                case 2 :
                                    Toast.makeText(getApplicationContext(), "Упс, пока не работает ;)", Toast.LENGTH_SHORT).show(); break;
                                case 3 :
                                    fTrans.replace(R.id.main_cont, rules); break;
                                case 4 :
                                    fTrans.replace(R.id.main_cont, profile); break;
                                case 5 :
                                    fTrans.replace(R.id.main_cont, rate); break;
                            }
                            fTrans.commit();
                        }
                    }));
        }
        /*circleMenu = (CircleMenu) findViewById(R.id.circleMenu);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                String hintText = menuButton.getHintText();
                fTrans = getFragmentManager().beginTransaction();
                switch (hintText) {
                    case "игра" :
                        fTrans.replace(R.id.main_cont, modes); break;
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
        });*/
    }
}
