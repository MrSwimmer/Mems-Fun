package com.membattle.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.membattle.API.SupportClasses.Responses.Coins.Coins;
import com.membattle.API.SupportClasses.Responses.Game.PairLikes.PairLikes;
import com.membattle.API.SupportClasses.Responses.Game.PairMem.PairMem;
import com.membattle.R;
import com.membattle.API.SupportClasses.Requests.RequestToGame;
import com.membattle.RefreshAction;
import com.membattle.WidgetPlus.TextViewPlus;
import com.squareup.picasso.Picasso;

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
    //private Socket socket;
    Socket socket = null;
    private SharedPreferences mSettings;

    @Override
    public void onResume() {

        super.onResume();
        try {
            socket = IO.socket("https://api.mems.fun/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                RequestToGame requestToGame = new RequestToGame(USER_ID, 0, 0, "@@ws/CONNECT_TO_GAME_REQUEST");
                String json = gson.toJson(requestToGame);
                socket.emit("action", json);
                RequestToGame requestToGame2 = new RequestToGame(USER_ID, 0, 0, "@@ws/GET_MEM_PAIR_REQUEST");
                String json2 = gson.toJson(requestToGame2);
                socket.emit("action", json2);
            }
        });
        socket.on("connect", onConnect);
        socket.on("action", onAction);
        socket.on("error", onError);
        socket.connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHandler = new Handler();
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        //new RefreshAction(getActivity());
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
                if (!click) {
                    showOnImageLike(true);
                    RequestToGame req = new RequestToGame(USER_ID, 0, 0, "@@ws/CHOOSE_MEM_REQUEST");
                    String j = gson.toJson(req);
                    socket.emit("action", j);
                    click = true;
                }
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click) {
                    showOnImageLike(false);
                    RequestToGame req = new RequestToGame(USER_ID, 1, 0, "@@ws/CHOOSE_MEM_REQUEST");
                    String j = gson.toJson(req);
                    socket.emit("action", j);
                    click = true;
                }
            }
        });
        return v;
    }
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                Log.i("game", "onConnect " + args[0]);
            } catch (Exception e) {
                Log.i("game", "onConnect null");
            }
        }
    };
    private Emitter.Listener onAction = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("game", "onAction  " + args[0]);
            try {
                String type = getJsonFromArgs(args[0]).getString("type");
                Log.i("game", "type " + type);
                switch (type) {
                    case "@@ws/NEW_PAIR" :
                        onSetMemes(args[0]+"");
                        break;
                    case "@@ws/GET_MEM_PAIR_SUCCESS" :
                        onSetMemes(args[0]+"");
                        break;
                    case "@@ws/PAIR_WINNER" :
                        showlikes(args[0]+"");
                        break;
                    case "@@ws/ADD_COIN" :
                        addCoins(args[0]+"");
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private Emitter.Listener onError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("game", "onError  " + args[0]);
        }
    };
    void addCoins(final String args) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                addCoinsOnUI(args);
            }
        });
    }
    void addCoinsOnUI(String args) {
        Coins coins = gson.fromJson(args, Coins.class);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt("coins", Integer.parseInt(coins.getData().getCoins()));
        editor.apply();
    }
    void onSetMemes(final String args) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setMemesOnUI(args);
            }
        });
    }
    void setMemesOnUI(String args){
        hideTrash();

        startTick();
        click = false;
        PairMem pairMem = gson.fromJson(args, PairMem.class);
        String readyuril = pairMem.getData().getLeftMemeImg();
        String readyurir = pairMem.getData().getRightMemeImg();
        Log.i("game", readyuril+" "+readyurir);
        setim(readyuril, first);
        setim(readyurir, second);

    }
    void setim(String url, ImageView imageView){
        Picasso.with(getActivity())
                .load(url)
                .placeholder(R.color.white)
                .error(R.color.white)
                .into(imageView);
    }
    void showLikesOnUI(int countf, int counts){
        after1.setVisibility(View.VISIBLE);
        after2.setVisibility(View.VISIBLE);
        countfirst.setText(countf + "");
        countsecond.setText(counts + "");
        if (countf > counts) {
            winfirst.setText("Победитель!");
            winsecond.setText("");
        } else {
            winsecond.setText("Победитель!");
            winfirst.setText("");
        }
    }
    void showlikes(String args) {
        PairLikes pairLikes = gson.fromJson(args, PairLikes.class);
        final int countf = Integer.parseInt(pairLikes.getData().getLeftLikes());
        final int counts = Integer.parseInt(pairLikes.getData().getRightLikes());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showLikesOnUI(countf, counts);
            }
        });
    }

    void hideTrash() {
        after1.setVisibility(View.INVISIBLE);
        after2.setVisibility(View.INVISIBLE);
        likeonf.setVisibility(View.INVISIBLE);
        likeons.setVisibility(View.INVISIBLE);
    }

    void showOnImageLike(boolean num) {
        if (num) {
            likeonf.setVisibility(View.VISIBLE);
        } else {
            likeons.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        socket.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        socket.close();
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

    void startTick() {
        mChronometer.stop();
        timer.setText("15");
        mChronometer.start();
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - mChronometer.getBase();
                if (elapsedMillis > 1000) {
                    tick--;
                    timer.setText(tick + "");
                    elapsedMillis = 0;
                    if (tick == 0) {
                        mChronometer.stop();
                        tick = 15;//длина баттла
                    }
                }
            }
        });
    }
}
