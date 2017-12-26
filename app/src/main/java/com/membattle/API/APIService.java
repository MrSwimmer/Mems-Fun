package com.membattle.API;

import com.membattle.API.SupportClasses.Responses.Rate.Rate;
import com.membattle.API.SupportClasses.Responses.Exres;
import com.membattle.API.SupportClasses.Requests.Id;
import com.membattle.API.SupportClasses.Requests.RegistrationUser;

import okhttp3.RequestBody;
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
    Call<Exres> refresh(@Header("Authorization") String secret, @Body RequestBody refreshTok);
    @POST("game/rating")
    Call<Rate> getrate(@Header("Authorization") String secret, @Body Id id);
    @GET("auth/secret")
    Call<Exres> getsecret(@Header("Authorization") String secret);
}