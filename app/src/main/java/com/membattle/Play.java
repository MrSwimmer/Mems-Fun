package com.membattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Play extends Fragment{
    private OkHttpClient client;
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    int tick=5;
    static TextView timer, countfirst, countsecond;
    Button Skip;
    boolean click = false;
    LinearLayout firstlikes, secondlikes;
    static Context context;
    private Handler mHandler;
    Chronometer mChronometer;
    static ImageView first, second;
    private int id1=1, id2=2;
    private Realm mRealm;
    boolean voice = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battlefragment, container, false);
        mHandler = new Handler();
        //mRealm = MyApp.getInstance().mainRealm;
        int count=3;
        client = new OkHttpClient();
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        firstlikes = (LinearLayout) v.findViewById(R.id.battlefirstlike);
        secondlikes = (LinearLayout) v.findViewById(R.id.battleseclike);
        mChronometer = (Chronometer) v.findViewById(R.id.battlechrono);
        context = getActivity();
        Request request = new Request.Builder().url("http://94.180.119.78:8000/ws").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        final WebSocket ws = client.newWebSocket(request, listener);
        timer = (TextView) v.findViewById(R.id.textcount);
        Skip = (Button) v.findViewById(R.id.battleskip);
        first = (ImageView) v.findViewById(R.id.battlefirstim);
        second = (ImageView) v.findViewById(R.id.battlesecondim);
        countfirst = (TextView) v.findViewById(R.id.battlecountfirst);
        countsecond = (TextView) v.findViewById(R.id.battlecountsecond);
        client.dispatcher().executorService().shutdown();
        final String choose = "{\"type\":\"CHOOSE_MEM\",\"id\":";
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if(!click){
                ws.send(choose+id2+"}");
                click = true;
                    voice = false;
                }
            }
        });
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!click){
                ws.send(choose+id1+"}");
                click = true;
                    voice = true;
                }
            }
        });
        return  v;
    }
    class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;


        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.i("code", "open");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.i("code", text);
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
                .placeholder(R.drawable.mem1)
                .error(R.drawable.mem2)
                .into(imageView);
    }

    void getOnUI(String[] parameter){
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
                    timer.setText(fortick);
                    elapsedMillis=0;
                    if(tick==0){
                        mChronometer.stop();
                        tick=5;
                    }
                }
            }
        });
        countfirst.setText("");
        Log.i("code", "start2");
        countsecond.setText("");
        setim(parameter[0], first);
        Log.i("code", "start3");
        setim(parameter[1], second);
        firstlikes.setVisibility(View.INVISIBLE);
        secondlikes.setVisibility(View.INVISIBLE);
    }
    private class getWinnerMemAsync extends AsyncTask<Boolean, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // [... Обновите индикатор хода выполнения, уведомления или другой
            // элемент пользовательского интерфейса ...]
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
        int games = mSettings.getInt("countgames", 0);
        games++;
        if(parameter[0]){
            try{
                Toast.makeText(getActivity(), "Победа первого мема!", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
            editor.putInt("countgames", games);
            if(voice){
                wins++;
                coins+=2;
                editor.putInt("coins", coins);
                editor.putInt("countwins", wins);
                editor.apply();
            }
        }
        else {
            try{
                Toast.makeText(getActivity(), "Победа второго мема!", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
            editor.putInt("countgames", games);
            if(!voice){
                wins++;
                coins+=2;
                editor.putInt("coins", coins);
                editor.putInt("countwins", wins);
                editor.apply();
            }
        }
    }
    private class getMemesAsync extends AsyncTask<String, Integer, Void> {
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
                    getOnUI(parameter);
                }
            });

            return null;
        }
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
                    setOnUI(parameter);
                }
            });

            return null;
        }
    }
    void setOnUI(String[] parameter){
        countfirst.setText(parameter[0]);
        countsecond.setText(parameter[1]);
        firstlikes.setVisibility(View.VISIBLE);
        secondlikes.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
