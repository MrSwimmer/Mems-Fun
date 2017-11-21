package com.membattle.NewNavigation;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.membattle.R;

import java.util.ArrayList;

/**
 * Created by Севастьян on 19.11.2017.
 */

public class RateEvent extends Fragment {
    ListView list;
    TextView title;
    LineRating[] names = new LineRating[100];
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rate_event, container, false);
        list  = (ListView) v.findViewById(R.id.rate_list);
        /*mRecyclerView = (RecyclerView) v.findViewById(R.id.rate_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);*/
        for(int i=0; i<100; i++) {
            names[i]=new LineRating("user", 30, i+1);
            if(i==99){
                Log.i("code", "99");
                MyAdapter adapter = new MyAdapter(getActivity(), names);
                list.setAdapter(adapter);
                /*mAdapter = new RateAdapter(names, getActivity());
                mRecyclerView.setAdapter(mAdapter);*/
            }
            //if(names)
        }


        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        title = (TextView) v.findViewById(R.id.rate_title);
        String custom_font = "fonts/NAUTILUS.otf";
        Typeface CF = Typeface.createFromAsset(getActivity().getAssets(), custom_font);
        title.setTypeface(CF);
        /*RateAdapter adapter = new RateAdapter(getActivity(), names);
        list.setAdapter(adapter);*/

        /*ArrayAdapter<LineRating> adapter = new ArrayAdapter<LineRating>(getActivity(),
                R.layout.line_rating, names);*/

        return v;
    }
}
