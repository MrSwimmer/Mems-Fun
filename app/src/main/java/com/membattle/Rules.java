package com.membattle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Севастьян on 18.10.2017.
 */

public class Rules extends Fragment{
    TextView title, text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rules, container, false);
        text = (TextView) v.findViewById(R.id.rultext);
        title = (TextView) v.findViewById(R.id.ruletitle);
        text.setText(R.string.rules);
        title.setText("Правила игры");
        return v;
    }
}
