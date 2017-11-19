package com.membattle.NewNavigation;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.membattle.R;

/**
 * Created by Севастьян on 19.11.2017.
 */

public class RateEvent extends Fragment {
    ListView list;
    TextView title;
    String[] names;
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rules_event, container, false);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        title = (TextView) v.findViewById(R.id.rate_title);
        list = (ListView) v.findViewById(R.id.rate_list);
        for(int i=0; i<100; i++) {
            names[0] = "name "+i;
            //if(names)
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);
        return v;
    }
}
