package com.membattle.Game;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.membattle.API.SupportClasses.Responses.Memes;
import com.membattle.R;
import com.membattle.API.SupportClasses.Requests.RequestToGame;
import com.membattle.WidgetPlus.TextViewPlus;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;

import static com.membattle.Game.PlayActivity.USER_ID;

public class Game extends android.app.Fragment {

    private Handler mHandler;
    public static final String APP_PREFERENCES = "settings";
    Gson gson = new Gson();
    static TextViewPlus timer, countfirst, countsecond, winfirst, winsecond;
    static ImageView first, second, clock, flikes, slikes, likeonf, likeons;
    RelativeLayout after1, after2;
    Chronometer mChronometer;

    int tick = 7;
    boolean click = false;
    boolean voice = true;
    private int id1 = 1, id2 = 2;

    private Socket mSocket; {
        try {
            mSocket = IO.socket("https://api.mems.fun/");
        } catch (URISyntaxException e) {
            Log.i("game", "socketer " + e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSocket.on("@@ws/CONNECT_TO_GAME_SUCCESS", onConnect);
        mSocket.on("@@ws/NEW_PAIR", onGetMemes);
        mSocket.on("@@ws/PAIR_WINNER", onGetWinner);
        mSocket.on("@@ws/GET_MEM_PAIR_SUCCESS", onFirstGet);
        RequestToGame requestToGame = new RequestToGame(USER_ID, false, 0, "@@ws/CONNECT_TO_GAME_REQUEST");
        String json = gson.toJson(requestToGame);
        mSocket.emit("action", json);
        Log.i("game", json);
        mSocket.connect();
        //mSocket.emit("@@ws/CONNECT_TO_GAME_REQUEST", json);
        //mSocket.emit("@@ws/GET_MEM_PAIR_REQUEST", json);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.battlefragment, container, false);
        clock = (ImageView) v.findViewById(R.id.battle_clockim);
        clock.setImageResource(R.drawable.hourglass);
        mChronometer = (Chronometer) v.findViewById(R.id.battlechrono);
        timer = (TextViewPlus) v.findViewById(R.id.textcount);
        //лэйауты с лайками и победителем
        after1 = (RelativeLayout) v.findViewById(R.id.battle_after_first);
        after2 = (RelativeLayout) v.findViewById(R.id.battle_after_second);
        //картинки
        first = (ImageView) v.findViewById(R.id.battlefirstim);
        second = (ImageView) v.findViewById(R.id.battlesecondtim);
        //лойсы после получения результатов
        countfirst = (TextViewPlus) v.findViewById(R.id.battle_after_likes);
        countsecond = (TextViewPlus) v.findViewById(R.id.battle_after_likes2);
        //метка победителя
        winfirst = (TextViewPlus) v.findViewById(R.id.battle_after_winner_first);
        winsecond = (TextViewPlus) v.findViewById(R.id.battle_after_winner_second);
        //подрубаем иконки и тп
        flikes = (ImageView) v.findViewById(R.id.battle_imlikef);
        slikes = (ImageView) v.findViewById(R.id.battle_imlikes);
        flikes.setImageResource(R.drawable.ic_like);
        slikes.setImageResource(R.drawable.ic_like);
        timer.setText(7 + "");
        likeonf = (ImageView) v.findViewById(R.id.battle_likeonf);
        likeons = (ImageView) v.findViewById(R.id.battle_likeons);
        likeonf.setImageResource(R.drawable.onimagelike);
        likeons.setImageResource(R.drawable.onimagelike);
        first.setImageResource(R.drawable.bb);
        second.setImageResource(R.drawable.bb);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(!click) {
                    showOnImageLike(true);
                    RequestToGame req = new RequestToGame(USER_ID, false, 0, "@@ws/CHOOSE_MEM_REQUEST");
                    String j = gson.toJson(req);
                    mSocket.emit("action", j);
                    click = true;
                    voice = true;
                //}
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!click) {
                    showOnImageLike(true);
                    RequestToGame req = new RequestToGame(USER_ID, true, 0,"@@ws/CHOOSE_MEM_REQUEST");
                    String j = gson.toJson(req);
                    mSocket.emit("action", j);
                    Log.i("code", "SendChoose1");
                    click = true;
                    voice = true;
                }
            }
        });
        return v;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = getJsonFromArgs(args);
            Log.i("game", "onConnect "+jsonObject);
        }

    };

    private Emitter.Listener onGetMemes = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("game", "getpair "+args[0]);
            onSetMemes((String) args[0]);
        }
    };
    private Emitter.Listener onFirstGet = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("game", "firstpair "+args[0]);
            onSetMemes((String) args[0]);
        }
    };
    void onSetMemes(String args){
        hideTrash();
        startTick();
        voice = true;
        click = false;
        Memes memes = gson.fromJson(args, Memes.class);
        first.setImageURI(Uri.parse(memes.getLeftMemeImg()));
        second.setImageURI(Uri.parse(memes.getRightMemeImg()));
        id1 = memes.getLeftMemeId();
        id2 = memes.getRightMemeId();
    }
    private Emitter.Listener onGetWinner = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("game", "getwinner "+args[0]);
        }
    };
    void showlikes(int countf, int counts){
        after1.setVisibility(View.VISIBLE);
        after2.setVisibility(View.VISIBLE);
        countfirst.setText(countf+"");
        countsecond.setText(counts+"");
        if(countf>counts) {
            winfirst.setText("Победитель!");
            winsecond.setText("");
        } else {
            winsecond.setText("Победитель!");
            winfirst.setText("");
        }
    }
    void hideTrash(){
        after1.setVisibility(View.INVISIBLE);
        after2.setVisibility(View.INVISIBLE);
        likeonf.setVisibility(View.INVISIBLE);
        likeons.setVisibility(View.INVISIBLE);
    }
    void showOnImageLike(boolean num){
        if(num) {
            likeonf.setVisibility(View.VISIBLE);
        } else {
            likeons.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    JSONObject getJsonFromArgs(Object... args) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(String.valueOf(args[0]));
            Log.i("code", "json: " + jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    void startTick(){
        mChronometer.stop();
        timer.setText("10");
        mChronometer.start();
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - mChronometer.getBase();
                if (elapsedMillis > 1000) {
                    tick--;
                    timer.setText(tick+"");
                    elapsedMillis=0;
                    if(tick==0){
                        mChronometer.stop();
                        tick=10;//длина баттла
                    }
                }
            }
        });
    }
}
