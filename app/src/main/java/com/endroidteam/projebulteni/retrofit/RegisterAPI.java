package com.endroidteam.projebulteni.retrofit;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by NuhKoca on 28.04.2016.
 */
public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/feed/insertContact.php")
    void insertUser(
            @Field("ContactEmail") String email,
            @Field("Title") String title,
            @Field("Message") String message,
            @Field("SentDate") String sentDate,
            Callback<Response> callback);
}
