package com.membattle.Game;

/**
 * Created by Севастьян on 20.11.2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.membattle.Sups.LineRating;
import com.membattle.R;
import com.membattle.TextViewPlus;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<LineRating> {
    private final Context context;
    private final ArrayList<LineRating> values;

    public MyAdapter(Context context, ArrayList<LineRating> values) {
        super(context, R.layout.line_rating, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.line_rating, parent, false);
        TextViewPlus pos = (TextViewPlus) rowView.findViewById(R.id.line_pos);
        TextViewPlus name = (TextViewPlus) rowView.findViewById(R.id.line_user);
        TextViewPlus coins = (TextViewPlus) rowView.findViewById(R.id.line_points);
        ImageView imc = (ImageView) rowView.findViewById(R.id.line_imc);
        if(values.get(position).user.equals("|||")){
            pos.setText("");
            name.setText("...");
            coins.setText("");
        }
        else {
            imc.setImageResource(R.drawable.coin);
            int p = position+1;
            pos.setText(p+"");
            name.setText(values.get(position).user);
            coins.setText(values.get(position).coins+"");
        }
        return rowView;
    }
}
