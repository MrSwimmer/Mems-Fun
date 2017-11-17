package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Севастьян on 14.10.2017.
 */

public class Profile extends android.app.Fragment {
    TextView username, coins, countgames, countwins, winstrik, winrate;
    TextView title1, title2, title3, title4;
    private SharedPreferences mSettings;
    ImageView photo;
    public static final String APP_PREFERENCES = "settings";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, container, false);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        title1 = (TextView) v.findViewById(R.id.title1);
        title2 = (TextView) v.findViewById(R.id.title2);
        title3 = (TextView) v.findViewById(R.id.title3);
        title4 = (TextView) v.findViewById(R.id.title4);
        username = (TextView) v.findViewById(R.id.profileusername);
        coins = (TextView) v.findViewById(R.id.profilecoins);
        photo = (ImageView) v.findViewById(R.id.profile_image);
        photo.setImageResource(R.drawable.logo_circlel);
        countgames = (TextView) v.findViewById(R.id.countgames);
        countwins = (TextView) v.findViewById(R.id.countwins);
        winstrik = (TextView) v.findViewById(R.id.profwinstr);
        winrate = (TextView) v.findViewById(R.id.profwinrate);

        String custom_font = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
        Typeface CF = Typeface.createFromAsset(getActivity().getAssets(), custom_font);
        String font = "fonts/NAUTILUS.otf";
        Typeface CFt = Typeface.createFromAsset(getActivity().getAssets(), font);

        String user, c, Countg, Countw;
        user = mSettings.getString("login", "username");
        c = String.valueOf(mSettings.getInt("coins", 0));
        Countg = String.valueOf(mSettings.getInt("countgames",0));
        Countw = String.valueOf(mSettings.getInt("countwins",0));
        int cg = mSettings.getInt("countgames",1);
        int cw = mSettings.getInt("countwins",1);
        float rate = 100*((float)cw/(float)cg);
        String Rate = String.format("%.2f", rate);
        int strik = mSettings.getInt("winstrik",1);
        winstrik.setText(String.valueOf(strik));
        winrate.setText(Rate+"%");
        username.setText(user);
        coins.setText(c);
        countgames.setText(Countg);
        countwins.setText(Countw);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refresh = mSettings.getString("token_refresh", null);
                Log.i("code", "pr " + refresh);
                new RefreshAction(refresh, getActivity());
            }
        });
        username.setTypeface(CFt);
        title1.setTypeface(CF);
        title2.setTypeface(CF);
        title3.setTypeface(CF);
        title4.setTypeface(CF);
        coins.setTypeface(CF);
        countgames.setTypeface(CF);
        countwins.setTypeface(CF);
        winrate.setTypeface(CF);
        winstrik.setTypeface(CF);

        return  v;
    }

}
