package com.membattle.NewNavigation;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.membattle.Play;
import com.membattle.R;

import java.sql.Time;
import java.util.ArrayList;

import static com.membattle.NewNavigation.MainActivity.fTrans;

/**
 * Created by Севастьян on 11.11.2017.
 */

public class ModesFragment extends android.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static int Tick;
    static long start = 0;
    static Chronometer sChronometer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modes_fragment, container, false);
        ArrayList<ModeItem> myDataset = getDataSet();
        Tick = 0;
        mRecyclerView = (RecyclerView) v.findViewById(R.id.mods_recycler);
        start = System.currentTimeMillis();
        /*sChronometer = (Chronometer) v.findViewById(R.id.modes_chrono);
        sChronometer.start();
        sChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - sChronometer.getBase();
                if (elapsedMillis > 1000) {
                    Tick++;
                    elapsedMillis=0;
                }
            }
        });*/
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(myDataset, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
    private ArrayList<ModeItem> getDataSet() {

        ArrayList<ModeItem> mDataSet = new ArrayList();
        ModeItem modeItem = new ModeItem(R.drawable.dp, "Бесконечный баттл", 0);
        mDataSet.add(modeItem);
        for (int i = 0; i < 10; i++) {
            ModeItem modeItem1 = new ModeItem(R.drawable.dp, "День первокурсника", 50);
            mDataSet.add(modeItem1);
        }

        return mDataSet;
    }
    void gotoGame(){
        Play play = new Play();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.main_cont, play);
        fTrans.commit();
    }
}
