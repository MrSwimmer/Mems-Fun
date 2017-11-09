package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
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

public class Profile extends Fragment {
    TextView username, coins, countgames, countwins, winstrik, winrate;
    private SharedPreferences mSettings;
    ImageView photo;
    public static final String APP_PREFERENCES = "mysettings";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, container, false);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        username = (TextView) v.findViewById(R.id.profileusername);
        coins = (TextView) v.findViewById(R.id.profilecoins);
        photo = (ImageView) v.findViewById(R.id.profile_image);
        countgames = (TextView) v.findViewById(R.id.countgames);
        countwins = (TextView) v.findViewById(R.id.countwins);
        winstrik = (TextView) v.findViewById(R.id.profwinstr);
        winrate = (TextView) v.findViewById(R.id.profwinrate);
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
        return  v;
    }

}
