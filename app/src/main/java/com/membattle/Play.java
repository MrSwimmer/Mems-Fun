package com.membattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Play extends Fragment{
    private OkHttpClient client;
    static TextView timer, countfirst, countsecond;
    Button Skip;
    static Context context;
    Chronometer mChronometer;
    static ImageView first, second;
    private int id1=1, id2=2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battlefragment, container, false);
        //Log.i("code", "ok");
        int count=3;
        client = new OkHttpClient();
        mChronometer = (Chronometer) v.findViewById(R.id.battlechrono);
        context = getActivity();
        Request request = new Request.Builder().url("ws://192.168.0.142:8000/ws").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        final WebSocket ws = client.newWebSocket(request, listener);
        //ws.send();
        String con = "{\"type\":\"GET_STAGE\"}";
        //ws.send(con);
        Skip = (Button) v.findViewById(R.id.battleskip);
        first = (ImageView) v.findViewById(R.id.battlefirstim);
        second = (ImageView) v.findViewById(R.id.battlesecondim);
        timer = (TextView) v.findViewById(R.id.battletimer);
        countfirst = (TextView) v.findViewById(R.id.battlecountfirst);
        countsecond = (TextView) v.findViewById(R.id.battlecountsecond);
        //timer.setText("До новой пары "+3+" cек.");
        client.dispatcher().executorService().shutdown();
        final String choose = "{\"type\":\"CHOOSE_MEM\",\"id\":";
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send(choose+id1+"}");

            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send(choose+id2+"}");
            }
        });
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send(choose+id1+"}");
            }
        });
        return  v;
    }
    class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        private Bitmap mIcon11;
        private Bitmap mIcon12;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.i("code", "open");
            //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.i("code", text);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(text);
                String type = jsonObject.getString("type");
                if(type=="END_TIMER"){
                    int winid = jsonObject.getJSONObject("data").getInt("winner_id");
                    Log.i("code", "wi: "+winid);
                }
                String url1 = jsonObject.getJSONArray("data").getJSONObject(0).getString("url");
                String url2 = jsonObject.getJSONArray("data").getJSONObject(1).getString("url");
                id2 = jsonObject.getJSONArray("data").getJSONObject(1).getInt("id");
                Log.i("code", url1);
                id1 = jsonObject.getJSONArray("data").getJSONObject(0).getInt("id");
                //int coin = jsonObject.getJSONArray("data").getJSONObject(0).getInt("like");
                if(type=="START_TIMER"){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mChronometer.setCountDown(true);
                    }
                    // установим начальное значение
                    mChronometer.setBase(SystemClock.elapsedRealtime() + 1000 * 5);
                    countfirst.setText("");
                    countsecond.setText("");
                }
                setim(url1, first);
                setim(url2, second);
                if(type == "MEMES_LIKES"){
                    int likesf, likess;
                    likesf = jsonObject.getJSONArray("data").getJSONObject(0).getInt("likes");
                    likess = jsonObject.getJSONArray("data").getJSONObject(1).getInt("likes");
                    String slikef, slikess;
                    slikef = String.valueOf(likesf);
                    slikess = String.valueOf(likess);
                    countfirst.setText(slikef);
                    countsecond.setText(slikess);
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
                .error(R.drawable.mem1)
                .into(imageView);
    }
}
