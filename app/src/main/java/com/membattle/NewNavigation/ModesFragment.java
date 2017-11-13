package com.membattle.NewNavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.membattle.Play;
import com.membattle.R;

import java.util.ArrayList;

import static com.membattle.NewNavigation.MainActivity.fTrans;

/**
 * Created by Севастьян on 11.11.2017.
 */

public class ModesFragment extends android.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modes_fragment, container, false);
        ArrayList<ModeItem> myDataset = getDataSet();

        mRecyclerView = (RecyclerView) v.findViewById(R.id.mods_recycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(myDataset, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
    private ArrayList<ModeItem> getDataSet() {

        ArrayList<ModeItem> mDataSet = new ArrayList();

        for (int i = 0; i < 4; i++) {
            ModeItem modeItem = new ModeItem(R.drawable.party, "Title"+i, "Description"+i, 50);
            mDataSet.add(modeItem);
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
