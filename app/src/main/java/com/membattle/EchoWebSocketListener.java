package com.membattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.membattle.Play.context;

/**
 * Created by Севастьян on 15.10.2017.
 */
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
            int id2 = jsonObject.getJSONArray("data").getJSONObject(1).getInt("id");
            Log.i("code", url1);
            int id1 = jsonObject.getJSONArray("data").getJSONObject(0).getInt("id");
            int coin = jsonObject.getJSONArray("data").getJSONObject(0).getInt("like");
            Picasso.with(context)
                    .load(url1)
                    .placeholder(R.drawable.mem1)
                    .error(R.drawable.mem1)
                    .into(Play.first);

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