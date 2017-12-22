package com.membattle.Game;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.membattle.API.SupportClasses.Responses.Item;
import com.membattle.API.SupportClasses.Responses.Memes;
import com.membattle.R;
import com.membattle.Sups.RequestToGame;
import com.membattle.TextViewPlus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;

import okhttp3.WebSocketListener;

import static com.membattle.Game.PlayActivity.USER_ID;
import static com.membattle.Game.PlayActivity.game_id;

public class Game extends android.app.Fragment {

    private SharedPreferences mSettings;
    private Handler mHandler;
    public static final String APP_PREFERENCES = "settings";
    String ADRESS = "https://mems.fun/";
    Gson gson = new Gson();
    static TextViewPlus timer, countfirst, countsecond, winfirst, winsecond;
    static ImageView first, second, clock, flikes, slikes, likeonf, likeons;
    RelativeLayout after1, after2;
    Chronometer mChronometer;

    int tick = 7;
    boolean click = false;
    boolean voice = true;
    private int id1 = 1, id2 = 2;

    public static Socket mSocket; {
        try {
            mSocket = IO.socket(ADRESS);
        } catch (URISyntaxException e) {
        }
    }
    @Override
    public void onResume() {

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        int games = mSettings.getInt("countgames", 0);
        editor.putInt("countgames", games - 1);
        mSocket.on("CONNECT_TO_GAME", onConnect);
        mSocket.on("NEW_PAIR", onGetMemes);
        mSocket.on("PAIR_WINNER", onGetWinner);
        mSocket.on("GET_MEM_PAIR", onFirstGet);
        mSocket.connect();
        RequestToGame requestToGame = new RequestToGame(USER_ID, false, 0, 0);
        String json = gson.toJson(requestToGame);
        mSocket.emit("GET_MEM_PAIR", json);
        mSocket.emit("CONNECT_TO_GAME", json);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battlefragment, container, false);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
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

                showOnImageLike(true);
                showlikes(1,2);
                /*RequestToGame req = new RequestToGame(USER_ID, false, 0, 1);
                String j = gson.toJson(req);
                mSocket.emit("CHOOSE_MEM", j);
                Log.i("code", "SendChoose1");
                if (!click) {
                    click = true;
                    voice = true;
                }*/
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTrash();
                /*showOnImageLike(false);
                RequestToGame req = new RequestToGame(USER_ID, true, 0, 2);
                String j = gson.toJson(req);
                mSocket.emit("CHOOSE_MEM", j);
                Log.i("code", "SendChoose2");
                if (!click) {
                    click = true;
                    voice = false;
                }*/
            }
        });
        return v;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = getJsonFromArgs(args);
            Log.i("game", "onConnect"+jsonObject);
        }
    };

    private Emitter.Listener onGetMemes = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("game", args[0]+"");
            onSetMemes((String) args[0]);
        }
    };
    private Emitter.Listener onFirstGet = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("game", args[0]+"");
            onSetMemes((String) args[0]);
        }
    };
    void onSetMemes(String args){
        hideTrash();
        startTick();
        voice = false;
        click = false;
        Memes memes = gson.fromJson((String) args, Memes.class);
        Item i1, i2;
        i1 = memes.getItems().get(0);
        i2 = memes.getItems().get(1);
        first.setImageURI(Uri.parse(i1.getUrl()));
        second.setImageURI(Uri.parse(i2.getUrl()));
        id1 = i1.getId();
        id2 = i2.getId();
    }
    private Emitter.Listener onGetWinner = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = gson.fromJson(String.valueOf(args[0]), JSONObject.class);
            Log.i("game", jsonObject+"");
            showlikes(1, 2);

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
