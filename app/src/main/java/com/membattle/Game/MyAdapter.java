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

public class MyAdapter extends ArrayAdapter<LineRating> {
    private final Context context;
    private final LineRating[] values;

    public MyAdapter(Context context, LineRating[] values) {
        super(context, R.layout.line_rating, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.line_rating, parent, false);
        TextView pos = (TextView) rowView.findViewById(R.id.line_pos);
        TextView name = (TextView) rowView.findViewById(R.id.line_user);
        TextView coins = (TextView) rowView.findViewById(R.id.line_points);
        ImageView imc = (ImageView) rowView.findViewById(R.id.line_imc);
        imc.setImageResource(R.drawable.coin);
        int p = position+1;
        pos.setText(p+"");
        name.setText(values[position].user);
        coins.setText(values[position].coins+"");
        String font_text = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
        Typeface CFt = Typeface.createFromAsset(context.getAssets(), font_text);
        name.setTypeface(CFt);
        pos.setTypeface(CFt);
        coins.setTypeface(CFt);

        return rowView;
    }
}
