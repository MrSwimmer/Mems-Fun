package com.membattle;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by Севастьян on 08.11.2017.
 */

public class JWTUtils {

    public static void decoded(String JWTEncoded, String Body) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Body = getJson(split[1]);
            Log.i("code", "Header: " + getJson(split[0]));
            Log.i("code", "Body: " + getJson(split[1]));
        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }
    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}