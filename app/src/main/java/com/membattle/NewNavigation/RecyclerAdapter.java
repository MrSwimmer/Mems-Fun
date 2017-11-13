package com.membattle.NewNavigation;

/**
 * Created by Севастьян on 12.11.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.membattle.NavigationActivity;
import com.membattle.Play;
import com.membattle.R;

import java.util.ArrayList;

import static com.membattle.NewNavigation.MainActivity.fTrans;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ModeItem> mDataset;

    private Context context;
    int tick = 0;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, Description, Play, TextTime;
        public ImageView Image;
        public Chronometer mChronometer;

        public ViewHolder(View v) {
            super(v);
            Title = (TextView) v.findViewById(R.id.item_title);
            Description = (TextView) v.findViewById(R.id.item_description);
            Play = (TextView) v.findViewById(R.id.item_play);
            Image = (ImageView) v.findViewById(R.id.item_image);
            TextTime = (TextView) v.findViewById(R.id.item_text_timer);
            MyCountDownTimer mCountDownTimer;
            mCountDownTimer = new MyCountDownTimer(10000, 1000);
            mCountDownTimer.start();
            /*mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    long elapsedMillis = SystemClock.elapsedRealtime()
                            - mChronometer.getBase();
                    if (elapsedMillis > 1000) {
                        tick++;
                        String fortick = String.valueOf(tick);
                        elapsedMillis=0;
                    }
                }
            });*/

        }
        class MyCountDownTimer extends CountDownTimer {

            public MyCountDownTimer(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onFinish() {
                TextTime.setText("Поехали!");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                TextTime.setText((String.valueOf(millisUntilFinished / 1000)));
            }
        }
    }

    public RecyclerAdapter(ArrayList<ModeItem> dataset, Context context) {
        mDataset = dataset;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int[] backcount = {mDataset.get(position).Time};
        holder.Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0 :
                        Intent intent = new Intent(context, PlayActivity.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
        holder.Title.setText(mDataset.get(position).Title);
        holder.Description.setText(mDataset.get(position).Description);
        holder.Image.setImageResource(mDataset.get(position).Image);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
