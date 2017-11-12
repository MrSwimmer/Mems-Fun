package com.membattle.API;

import com.membattle.API.SupportClasses.Responses.Exres;
import com.membattle.API.SupportClasses.Requests.RefreshTok;
import com.membattle.API.SupportClasses.Requests.RegistrationUser;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Exres> refresh(@Body RefreshTok refreshTok);
    @POST("auth/refresh-token")
    Call<Exres> refreshbody(@Body RequestBody refreshTok);
}