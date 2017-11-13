package com.membattle.NewNavigation;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Севастьян on 14.11.2017.
 */

class MyCountDownTimer extends CountDownTimer {
    TextView mTextTime;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        mTextTime = textView;
    }

    @Override
    public void onFinish() {
        mTextTime.setText("Поехали!");
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextTime.setText((String.valueOf(millisUntilFinished / 1000)));
    }
}
