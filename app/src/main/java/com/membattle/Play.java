package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.Toast;

import com.membattle.NewNavigation.PlayActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Play extends android.app.Fragment{

    private OkHttpClient client;
    WebSocket ws;
    private SharedPreferences mSettings;
    Request request;
    EchoWebSocketListener listener;

    public static final String APP_PREFERENCES = "mysettings";
    static TextView timer, countfirst, countsecond, winfirst, winsecond;
    static ImageView first, second;
    RelativeLayout after1, after2;
    Chronometer mChronometer;

    int tick=5;
    boolean click = false;
    private Handler mHandler;
    private int id1=1, id2=2;
    boolean voice = true;

    //int skin1 = R.drawable.mem1;
    //запуск сокетов
    @Override
    public void onResume() {
        request = new Request.Builder().url("wss://mems.fun/ws").build();
        listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
        SharedPreferences.Editor editor = mSettings.edit();
        int games = mSettings.getInt("countgames", 0);
        editor.putInt("countgames", games-1);
        super.onResume();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battlefragment, container, false);
        mHandler = new Handler();
        client = new OkHttpClient();
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        request = new Request.Builder().url("wss://mems.fun/ws").build();
        listener = new EchoWebSocketListener();

        mChronometer = (Chronometer) v.findViewById(R.id.battlechrono);
        timer = (TextView) v.findViewById(R.id.textcount);
        after1 = (RelativeLayout) v.findViewById(R.id.battle_after_first);
        after2 = (RelativeLayout) v.findViewById(R.id.battle_after_second);
        first = (ImageView) v.findViewById(R.id.battlefirstim);
        second = (ImageView) v.findViewById(R.id.battlesecondtim);
        countfirst = (TextView) v.findViewById(R.id.battle_after_likes);
        countsecond = (TextView) v.findViewById(R.id.battle_after_likes2);
        winfirst = (TextView) v.findViewById(R.id.battle_after_winner_first);
        winsecond = (TextView) v.findViewById(R.id.battle_after_winner_second);

        String font_text = "fonts/OPENGOSTTYPEA_REGULAR.ttf";
        Typeface CFt = Typeface.createFromAsset(getActivity().getAssets(), font_text);
        timer.setTypeface(CFt);
        final String choose = "{\"type\":\"CHOOSE_MEM\",\"id\":";
        /*int chooseskin = mSettings.getInt("skin1", 0);
        switch (chooseskin){
            case 0: skin1 = R.drawable.mem1; break;
            case 1: skin1 = R.drawable.pay1; break;
            case 2: skin1 = R.drawable.pay2; break;
            case 3: skin1 = R.drawable.pay3; break;
            case 4: skin1 = R.drawable.pay4; break;
            case 5: skin1 = R.drawable.pay5; break;
            case 6: skin1 = R.drawable.pay6; break;
        }*/
        first.setImageResource(R.drawable.bb);
        second.setImageResource(R.drawable.bb);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showlikes();
                if(!click){

                    ws.send(choose+id1+"}");
                    click = true;
                    voice = true;
                }
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidelikes();
                if(!click){

                    ws.send(choose+id2+"}");
                    click = true;
                    voice = false;
                }
            }
        });
        return  v;
    }
    void showlikes(){
        after1.setVisibility(View.VISIBLE);
        after2.setVisibility(View.VISIBLE);
        countfirst.setText(12+"");
        countsecond.setText(23+"");
    }
    void hidelikes(){
        after1.setVisibility(View.INVISIBLE);
        after2.setVisibility(View.INVISIBLE);
    }
    class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.i("code", "open");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            //Log.i("code", text);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(text);
                String type = jsonObject.getString("type");
                if(type.equals("END_TIMER")){
                    int winid = jsonObject.getJSONObject("data").getInt("winner_id");
                    click=true;
                    if(winid==id1){
                        getWinnerMemAsync winner = new getWinnerMemAsync();
                        winner.execute(true);
                    }
                    else {
                        getWinnerMemAsync newwin = new getWinnerMemAsync();
                        newwin.execute(false);
                    }
                }
                String url1 = jsonObject.getJSONArray("data").getJSONObject(0).getString("url");
                String url2 = jsonObject.getJSONArray("data").getJSONObject(1).getString("url");
                id2 = jsonObject.getJSONArray("data").getJSONObject(1).getInt("id");
                id1 = jsonObject.getJSONArray("data").getJSONObject(0).getInt("id");

                if(type.equals("START_TIMER")){
                    click = false;
                    getMemesAsync myAsyncTaskStart = new getMemesAsync();
                    myAsyncTaskStart.execute(url1, url2, String.valueOf(id1), String.valueOf(id2));
                }
                if(type.equals("MEMES_LIKES")){
                    int likesf, likess;
                    likesf = jsonObject.getJSONArray("data").getJSONObject(0).getInt("likes");
                    likess = jsonObject.getJSONArray("data").getJSONObject(1).getInt("likes");
                    String slikef, slikess;
                    slikef = String.valueOf(likesf);
                    slikess = String.valueOf(likess);
                    setLikesAsync setl = new setLikesAsync();
                    setl.execute(slikef, slikess);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.i("code","bytes: " + bytes+"");
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);

        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {

        }

    }
    void setim(String url, ImageView imageView){
        Picasso.with(getActivity())
                .load(url)
                .placeholder(R.color.white)
                .error(R.color.white)
                .into(imageView);
    }

    private class getWinnerMemAsync extends AsyncTask<Boolean, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected Void doInBackground(final Boolean... parameter) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getWinOnUI(parameter);
                }
            });

            return null;
        }
    }

    private void getWinOnUI(Boolean[] parameter) {
        SharedPreferences.Editor editor = mSettings.edit();
        int coins = mSettings.getInt("coins", 0);
        int wins = mSettings.getInt("countwins", 0);
        int games = mSettings.getInt("countgames", 1);
        int strik = mSettings.getInt("winstrik",1);
        games++;
        Log.i("code", parameter[0]+" "+voice);
        //победа первого мема
        if(parameter[0]){

            editor.putInt("countgames", games);
            //если проголосовали за 1
            if(voice){
                wins++;
                coins+=strik;
                if(strik<5){
                    strik++;
                }
                editor.putInt("winstrik", strik);
                editor.putInt("coins", coins);
                editor.putInt("countwins", wins);
            }
            //если проголосовали за 2
            else {
                strik=1;
                editor.putInt("winstrik", strik);
            }
            editor.apply();
        }
        //победа второго мема
        else {

            editor.putInt("countgames", games);
            //если проголосовали за 2
            if(!voice){

                wins++;
                coins+=strik;
                if(strik<5){
                    strik++;
                }
                editor.putInt("winstrik", strik);
                editor.putInt("coins", coins);
                editor.putInt("countwins", wins);

            }
            //если проголосовали за 1
            else {
                strik=1;
                editor.putInt("winstrik", strik);
            }
            editor.apply();
        }
        Log.i("code", "work!");
    }
    private class getMemesAsync extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected Void doInBackground(final String... parameter) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    SetMemsOnUI(parameter);
                }
            });

            return null;
        }
    }
    void SetMemsOnUI(String[] parameter){
        timer.setText("5");
        mChronometer.start();
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - mChronometer.getBase();
                if (elapsedMillis > 1000) {
                    tick--;
                    String fortick = String.valueOf(tick);
                    timer.setText("Осталось " + fortick);
                    elapsedMillis=0;
                    if(tick==0){
                        mChronometer.stop();
                        tick=7;//длина баттла
                    }
                }
            }
        });
        /*countfirst.setText("");
        countsecond.setText("");*/
        after1.setVisibility(View.INVISIBLE);
        after2.setVisibility(View.INVISIBLE);
        setim(parameter[0], first);
        setim(parameter[1], second);
    }
    private class setLikesAsync extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // [... Обновите индикатор хода выполнения, уведомления или другой
            // элемент пользовательского интерфейса ...]
        }

        @Override
        protected Void doInBackground(final String... parameter) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    SetLikesOnUI(parameter);
                }
            });

            return null;
        }
    }
    void SetLikesOnUI(String[] parameter){
        after1.setVisibility(View.VISIBLE);
        after2.setVisibility(View.VISIBLE);
        countfirst.setText(parameter[0]);
        countsecond.setText(parameter[1]);

        /*firstlikes.setVisibility(View.VISIBLE);
        secondlikes.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void onDestroyView() {
        ws.cancel();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        ws.cancel();
        super.onPause();
    }


}
