package com.membattle.api;

import com.membattle.api.req.Secret;
import com.membattle.api.res.Rate.Rate;
import com.membattle.api.res.Exres;
import com.membattle.api.req.Id;
import com.membattle.api.req.RegistrationUser;
import com.membattle.api.res.ValidToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Севастьян on 25.09.2017.
 */


/* Retrofit 2.0 */

public interface APIService {
    @POST("auth/signup")
    Call<Exres> registration(@Body RegistrationUser user);
    @POST("auth/login")
    Call<Exres> auth(@Body RegistrationUser user);
    @POST("auth/refresh-token")
    Call<Exres> refresh(@Header("Authorization") String secret, @Body Secret refreshTok);
    @POST("game/rating")
    Call<Rate> getrate(@Header("Authorization") String secret, @Body Id id);
    @GET("auth/secret")
    Call<ValidToken> getsecret(@Header("Authorization") String secret);
}