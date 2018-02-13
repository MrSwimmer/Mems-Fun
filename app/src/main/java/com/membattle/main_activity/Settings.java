package com.membattle.main_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.membattle.sign_in_up.LoginActivity;
import com.membattle.R;
import com.membattle.widget_plus.TextViewPlus;

/**
 * Created by Севастьян on 15.10.2017.
 */

public class Settings extends android.app.Fragment{
    TextViewPlus Out, Mark, Share, About, Title;
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings, container, false);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Out = (TextViewPlus) v.findViewById(R.id.setout);
        Mark = (TextViewPlus) v.findViewById(R.id.setmark);
        Share = (TextViewPlus) v.findViewById(R.id.setshare);
        About = (TextViewPlus) v.findViewById(R.id.aboutv);
        Title = (TextViewPlus) v.findViewById(R.id.settitle);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("О версии").setMessage("Что нового?\nБагфикс\nЧто ожидать в следующих версиях?\nМоре товаров в нашем Мемагазине, чтобы вы могли тратить свои мемоины!").setPositiveButton("Неплохо", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Выход из аккаунта")
                        .setMessage("Вы действительно хотите выйти из аккаунта?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("login", "no");
                                editor.putInt("coins", 0);
                                editor.putInt("countwins", 1);
                                editor.putInt("countgames", 1);
                                editor.apply();
                                dialog.cancel();
                                Intent i = new Intent(getActivity(),LoginActivity.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "Присоединяйтесь к mems.fun !");
                i.putExtra(Intent.EXTRA_SUBJECT, "Поделиться");
                i = Intent.createChooser(i, "С помощью");
                startActivity(i);
            }
        });
        Mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.membattle"));
                startActivity(intent);
            }
        });
    return v;
    }
}
