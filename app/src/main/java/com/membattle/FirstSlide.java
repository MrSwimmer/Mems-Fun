package com.membattle;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import agency.tango.materialintroscreen.SlideFragment;

/**
 * Created by Севастьян on 15.10.2017.
 */

public class FirstSlide extends SlideFragment {
    String text;
    TextView Title, Text;

    public FirstSlide() {
        super();
    }

    @SuppressLint("ValidFragment")
    public FirstSlide(String i) {
        super();
        text = i;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.firstslide_layout, container, false);
        Title = (TextView) view.findViewById(R.id.titleintro);
        Text = (TextView) view.findViewById(R.id.textintro);
        String custom_font = "fonts/Mr_Lonely.otf";
        String font_text = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
        Typeface CF = Typeface.createFromAsset(getActivity().getAssets(), custom_font);
        Title.setTypeface(CF);
        Typeface CFt = Typeface.createFromAsset(getActivity().getAssets(), font_text);
        Text.setTypeface(CFt);
        Text.setText(text);
        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.custom_slide_background;
    }

    @Override
    public int buttonsColor() {
        return R.color.memblue;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Ошибка";
    }
}
