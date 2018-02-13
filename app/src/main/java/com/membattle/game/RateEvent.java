package com.membattle.game;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.membattle.api.APIService;
import com.membattle.api.req.Id;
import com.membattle.api.res.Rate.Rate;
import com.membattle.api.res.Rate.UserRating;
import com.membattle.RefreshAction;
import com.membattle.sups.LineRating;
import com.membattle.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Севастьян on 19.11.2017.
 */

public class RateEvent extends Fragment {
    ListView list;
    ArrayList<LineRating> names = new ArrayList<LineRating>();
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    private APIService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new RefreshAction(getActivity());
        View v = inflater.inflate(R.layout.rate_event, container, false);
        list  = (ListView) v.findViewById(R.id.rate_list);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mems.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        names.clear();
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String s = mSettings.getString("access_token", "nope");
        Log.i("code", "secret "+s);
        int id = mSettings.getInt("id", 120);
        Log.i("code", "id "+id);
        Id rid = new Id(id);
        Call<Rate> call = service.getrate("Bearer "+ s, rid);
        //Call<Rate> call = service.getrate(s, "Bearer", requestToGame);
        call.enqueue(new Callback<Rate>() {
            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response) {
                Rate rate = response.body();
                try {
                    if(response.code()!=502) {
                        Log.i("code", rate.getGlobalRating().size()+"size");
                        for(int i=0; i<rate.getGlobalRating().size(); i++) {
                            names.add(new LineRating( rate.getGlobalRating().get(i).getUsername(), rate.getGlobalRating().get(i).getCoins(), i + 1));
                        }
                        UserRating userRating = rate.getUserRating();
                        if(userRating.getRating()>10) {
                            names.add(new LineRating("|||", 1, 1));
                            names.add(new LineRating(userRating.getUsername(), userRating.getCoins(), userRating.getRating()));
                        }
                        Log.i("code", "size"+names.size());
                        for(int i=0; i<names.size(); i++) {
                            if(i==names.size()-1){
                                Log.i("code", "99");
                                MyAdapter adapter = new MyAdapter(getActivity(), names);
                                list.setAdapter(adapter);
                            }
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Ошибка подключения к серверу!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.i("code", "rate er "+e);
                }

            }
            @Override
            public void onFailure(Call<Rate> call, Throwable t) {

            }
        });
        return v;
    }
}
