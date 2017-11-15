package com.membattle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Севастьян on 15.10.2017.
 */

public class Settings extends android.app.Fragment{
    TextView Out, Mark, Clear, About;
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings, container, false);
        Out = (TextView) v.findViewById(R.id.setout);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Mark = (TextView) v.findViewById(R.id.setmark);
        Clear = (TextView) v.findViewById(R.id.setclear);
        About = (TextView) v.findViewById(R.id.aboutv);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("О версии").setMessage("Что нового?\nДобавили статистику игр, правила(а то говорят, что ничего не понятно), еще МЕМОВ!\nЧто ожидать в следующих версиях?\nАватарки для профиля и что-нибудь еще(пока не придумали)").setPositiveButton("Неплохо", new DialogInterface.OnClickListener() {
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
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Очистка данных")
                        .setMessage("Вы действительно хотите удалить данные?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putInt("countwins", 0);
                                editor.putInt("countgames", 0);
                                editor.apply();
                                dialog.cancel();

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
