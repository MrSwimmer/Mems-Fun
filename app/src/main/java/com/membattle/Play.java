package com.membattle;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    static ImageView first, second;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battlefragment, container, false);
        //Log.i("code", "ok");
        int count=3;
        client = new OkHttpClient();
        context = getActivity();
        Request request = new Request.Builder().url("ws://192.168.0.142:8000/ws").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        final WebSocket ws = client.newWebSocket(request, listener);
        //ws.send();
        String con = "{\"type\":\"GET_STAGE\"}";
        ws.send(con);
        Skip = (Button) v.findViewById(R.id.battleskip);
        first = (ImageView) v.findViewById(R.id.battlefirstim);
        second = (ImageView) v.findViewById(R.id.battlesecondim);
        timer = (TextView) v.findViewById(R.id.battletimer);
        countfirst = (TextView) v.findViewById(R.id.battlecountfirst);
        countsecond = (TextView) v.findViewById(R.id.battlecountsecond);
        timer.setText("До новой пары "+3+" cек.");
        countfirst.setText("75");
        countsecond.setText("43");
        countfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseMem mem = new ChooseMem("CHOOSE_MEM", 3);
                String buf = "{\"type\":\"CHOOSE_MEM\",\"id\":1}";
                ws.send(buf);
            }
        });
        client.dispatcher().executorService().shutdown();
        return  v;
    }

}
