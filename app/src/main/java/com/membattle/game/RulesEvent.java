package com.membattle.game;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.membattle.R;

/**
 * Created by Севастьян on 15.11.2017.
 */

public class RulesEvent extends Fragment {
    TextView textRules;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rules_event, container, false);
        textRules = (TextView) v.findViewById(R.id.rules_event_text);
        //textRules.setText();
        return v;
    }

}
