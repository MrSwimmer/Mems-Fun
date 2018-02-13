package com.membattle.game;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.membattle.sups.ModeItem;
import com.membattle.R;

import java.util.ArrayList;

import static com.membattle.main_activity.MainActivity.fTrans;

/**
 * Created by Севастьян on 11.11.2017.
 */

public class ModesFragment extends android.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static int Tick;
    static long start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modes_fragment, container, false);
        ArrayList<ModeItem> myDataset = getDataSet();
        Tick = 0;
        mRecyclerView = (RecyclerView) v.findViewById(R.id.mods_recycler);
        start = System.currentTimeMillis();
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(myDataset, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
    private ArrayList<ModeItem> getDataSet() {

        ArrayList<ModeItem> mDataSet = new ArrayList();

        for (int i = 0; i < 1; i++) {
            ModeItem modeItem1 = new ModeItem(R.drawable.ng, "Новогодний баттл", 0, 0, Color.WHITE);
            mDataSet.add(modeItem1);
        }
        ModeItem modeItem = new ModeItem(R.drawable.bb, "Бесконечный баттл", 0, 0, R.color.memblue);
        mDataSet.add(modeItem);
        return mDataSet;
    }
    void gotoGame(){
        Game play = new Game();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.main_cont, play);
        fTrans.commit();
    }
}
