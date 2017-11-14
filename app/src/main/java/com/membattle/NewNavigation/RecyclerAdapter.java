package com.membattle.NewNavigation;

/**
 * Created by Севастьян on 12.11.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
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
import static com.membattle.NewNavigation.ModesFragment.start;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ModeItem> mDataset;
    private static ArrayList<Time> mTimes;
    String font_text = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
    Typeface CFt;
    private Context context;
    int tick = 0;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView Play;
        public TextView TextTime;
        public TextView Rules;
        public ImageView Image;

        public ViewHolder(View v) {
            super(v);
            Title = (TextView) v.findViewById(R.id.item_title);
            Play = (TextView) v.findViewById(R.id.item_play);
            Rules = (TextView) v.findViewById(R.id.item_rules);
            Image = (ImageView) v.findViewById(R.id.item_image);
            TextTime = (TextView) v.findViewById(R.id.item_text_timer);
        }
    }
    public RecyclerAdapter(ArrayList<ModeItem> dataset, Context context) {
        mDataset = dataset;
        this.context = context;
        CFt = Typeface.createFromAsset(context.getAssets(), font_text);
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_new, parent, false);
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
        holder.Image.setImageResource(mDataset.get(position).Image);
        holder.Title.setTypeface(CFt);
        holder.Play.setTypeface(CFt);
        holder.Rules.setTypeface(CFt);
        holder.TextTime.setTypeface(CFt);
        String tick = String.valueOf(ModesFragment.Tick);
        //holder.TextTime.setText(tick);
        MyCountDownTimer mCountDownTimer;
        long timegone = System.currentTimeMillis() - start;
        Log.i("code", "gone " + timegone);
        Log.i("code", "> "+mDataset.get(position).Time);
        if(mDataset.get(position).Time*1000-timegone>0&&(mDataset.get(position).Time!=0)){
            mCountDownTimer = new MyCountDownTimer(mDataset.get(position).Time*1000 - timegone, 1000, holder.TextTime);
            mCountDownTimer.start();
        }
        else {
            holder.TextTime.setText("Поехали!");
        }
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
