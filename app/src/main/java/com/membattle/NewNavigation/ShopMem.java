package com.membattle.NewNavigation;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.membattle.R;

/**
 * Created by Севастьян on 15.11.2017.
 */

class ShopMem extends Fragment{
    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.shop_mem, container, false);
            String custom_font = "fonts/NAUTILUS.otf";
        Typeface CF = Typeface.createFromAsset(getActivity().getAssets(), custom_font);
        String font_text = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
        Typeface CFt = Typeface.createFromAsset(getActivity().getAssets(), font_text);
        TextView title, text;
        title = (TextView) v.findViewById(R.id.shop_title);
        text = (TextView) v.findViewById(R.id.shop_text);
        title.setTypeface(CF);
        text.setTypeface(CFt);
    return v;
    }
}
