package com.membattle.main_activity;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.membattle.R;
import com.membattle.widget_plus.TextViewPlus;

/**
 * Created by Севастьян on 18.10.2017.
 */

public class Rules extends Fragment {
    TextViewPlus title, text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rules, container, false);
        text = (TextViewPlus) v.findViewById(R.id.rultext);
        title = (TextViewPlus) v.findViewById(R.id.ruletitle);
        text.setText(R.string.description);
        title.setText("Правила игры");
        return v;
    }
}
