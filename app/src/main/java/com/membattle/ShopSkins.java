package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Севастьян on 17.10.2017.
 */

public class ShopSkins extends Fragment{
    TextView title, cost1, cost2, cost3, cost4, cost5, cost6;
    boolean buy1, buy2, buy3, buy4, buy5, buy6;
    ImageView im1, im2,im3,im4,im5,im6;
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    int coins;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.shop_activity, container, false);
        title = (TextView) v.findViewById(R.id.title_shop);
        String custom_font = "fonts/Mr_Lonely.otf";
        Typeface CF = Typeface.createFromAsset(getActivity().getAssets(), custom_font);
        title.setTypeface(CF);
        im1 = (ImageView) v.findViewById(R.id.im1);
        im2 = (ImageView) v.findViewById(R.id.im2);
        im3 = (ImageView) v.findViewById(R.id.im3);
        im4 = (ImageView) v.findViewById(R.id.im4);
        im5 = (ImageView) v.findViewById(R.id.im5);
        im6 = (ImageView) v.findViewById(R.id.im6);

        cost1 = (TextView) v.findViewById(R.id.cost1);
        cost2 = (TextView) v.findViewById(R.id.cost2);
        cost3 = (TextView) v.findViewById(R.id.cost3);
        cost4 = (TextView) v.findViewById(R.id.cost4);
        cost5 = (TextView) v.findViewById(R.id.cost5);
        cost6 = (TextView) v.findViewById(R.id.cost6);
        buy1=mSettings.getBoolean("buy1", false);
        buy2=mSettings.getBoolean("buy2", false);
        buy3=mSettings.getBoolean("buy3", false);
        buy4=mSettings.getBoolean("buy4", false);
        buy5=mSettings.getBoolean("buy5", false);
        buy6=mSettings.getBoolean("buy6", false);
        coins = mSettings.getInt("coins", 0);
        if(buy1){
            cost1.setText("Потрачено");
        }
        if(buy2){
            cost2.setText("Потрачено");
        }
        if(buy3){
            cost3.setText("Потрачено");
        }
        if(buy4){
            cost4.setText("Потрачено");
        }
        if(buy5){
            cost5.setText("Потрачено");
        }
        if(buy6){
            cost6.setText("Потрачено");
        }
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buy1&&coins>1000){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("coins", coins-1000);
                    editor.putBoolean("buy1", true);
                    editor.putInt("skin1", 1);
                    editor.apply();
                    Toast.makeText(getActivity(), "Скин куплен!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "У вас недостаточно мемкоинов!", Toast.LENGTH_SHORT).show();
                }
                if(buy1){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("skin1", 1);
                    Toast.makeText(getActivity(), "Мем выбран как скин!", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buy2&&coins>2000){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("coins", coins-2000);
                    editor.putBoolean("buy2", true);
                    editor.putInt("skin1", 2);
                    editor.apply();
                    Toast.makeText(getActivity(), "Скин куплен!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "У вас недостаточно мемкоинов!", Toast.LENGTH_SHORT).show();
                }
                if(buy2){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("skin1", 2);
                    Toast.makeText(getActivity(), "Мем выбран как скин!", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buy3&&coins>3000){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("coins", coins-3000);
                    editor.putBoolean("buy3", true);
                    editor.putInt("skin1", 3);
                    editor.apply();
                    Toast.makeText(getActivity(), "Скин куплен!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "У вас недостаточно мемкоинов!", Toast.LENGTH_SHORT).show();
                }
                if(buy3){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("skin1", 3);
                    Toast.makeText(getActivity(), "Мем выбран как скин!", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buy4&&coins>4000){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("coins", coins-4000);
                    editor.putBoolean("buy4", true);
                    editor.putInt("skin1", 4);
                    editor.apply();
                    Toast.makeText(getActivity(), "Скин куплен!", Toast.LENGTH_SHORT).show();
                }
                if(buy4){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("skin1", 4);
                    Toast.makeText(getActivity(), "Мем выбран как скин!", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });
        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buy5&&coins>5000){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("coins", coins-5000);
                    editor.putBoolean("buy1", true);
                    editor.putInt("skin1", 5);
                    editor.apply();
                    Toast.makeText(getActivity(), "Скин куплен!", Toast.LENGTH_SHORT).show();
                }
                if(buy5){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("skin1", 5);
                    Toast.makeText(getActivity(), "Мем выбран как скин!", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });
        im6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buy1&&coins>100000){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("coins", coins-100000);
                    editor.putBoolean("buy6", true);
                    editor.putInt("skin1", 6);
                    editor.apply();
                    Toast.makeText(getActivity(), "Скин куплен!", Toast.LENGTH_SHORT).show();
                }
                if(buy1){
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("skin1", 6);
                    Toast.makeText(getActivity(), "Мем выбран как скин!", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });
        return v;
    }
}
