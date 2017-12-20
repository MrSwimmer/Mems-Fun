package com.membattle.Game;

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
        if(millisUntilFinished/1000>59){
            String min = millisUntilFinished/60000+"";
            String sec = ((int) (millisUntilFinished%60000)/1000)+"";
            mTextTime.setText(min+":"+sec);
        } else {
            mTextTime.setText((String.valueOf(millisUntilFinished / 1000)));
        }
    }
}
