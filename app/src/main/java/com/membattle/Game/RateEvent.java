package com.membattle.Game;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.membattle.Sups.LineRating;
import com.membattle.R;
import com.membattle.TextViewPlus;

/**
 * Created by Севастьян on 19.11.2017.
 */

public class RateEvent extends Fragment {
    ListView list;
    LineRating[] names = new LineRating[100];
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rate_event, container, false);
        list  = (ListView) v.findViewById(R.id.rate_list);
        for(int i=0; i<100; i++) {
            names[i]=new LineRating("user", 30, i+1);
            if(i==99){
                Log.i("code", "99");
                MyAdapter adapter = new MyAdapter(getActivity(), names);
                list.setAdapter(adapter);
            }
        }
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return v;
    }
}
