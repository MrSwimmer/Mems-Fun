package com.membattle.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.membattle.R;
import com.membattle.RefreshAction;
import com.membattle.WidgetPlus.TextViewPlus;

/**
 * Created by Севастьян on 14.10.2017.
 */

public class Profile extends android.app.Fragment {
    TextViewPlus username, coins;
    private SharedPreferences mSettings;
    ImageView photo, imc;
    public static final String APP_PREFERENCES = "settings";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, container, false);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        imc = (ImageView) v.findViewById(R.id.prof_imc);
        imc.setImageResource(R.drawable.coin_menu);
        username = (TextViewPlus) v.findViewById(R.id.profileusername);
        coins = (TextViewPlus) v.findViewById(R.id.profilecoins);
        photo = (ImageView) v.findViewById(R.id.profile_image);
        photo.setImageResource(R.drawable.logo_circlel);

        String user, c;
        user = mSettings.getString("login", "username");
        c = String.valueOf(mSettings.getInt("coins", 0));

        username.setText(user);
        coins.setText(c);

       /* photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refresh = mSettings.getString("token_refresh", null);
                String secret = mSettings.getString("token_access", null);
                Log.i("code", "pr " + refresh);
                new RefreshAction(getActivity());
            }
        });*/
        return  v;
    }

}
