package com.membattle.NewNavigation;

/**
 * Created by Севастьян on 12.11.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, Description, Play;
        public ImageView Image;
        public ViewHolder(View v) {
            super(v);
            Title = (TextView) v.findViewById(R.id.item_title);
            Description = (TextView) v.findViewById(R.id.item_description);
            Play = (TextView) v.findViewById(R.id.item_play);
            Image = (ImageView) v.findViewById(R.id.item_image);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {

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
