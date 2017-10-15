package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class FirstStartActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);
        addSlide(new FirstSlide("Люди, которые заходят в Интернет, чтобы получить удовольствие, тонут в огромном пространстве некачественного и малосмешного контента"));
        addSlide(new FirstSlide("MemBattle позволяет выбрать наиболее интересные и смешные мемы из популярных сообществ «Вконтакте»"));
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}