package com.membattle.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;

import agency.tango.materialintroscreen.MaterialIntroActivity;

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