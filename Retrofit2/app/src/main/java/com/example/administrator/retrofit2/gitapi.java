package com.example.administrator.retrofit2;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Url;

/**
 * Created by Administrator on 2016/7/29.
 */
public interface gitapi {
    @GET("/users/{user}")
    Call<gitmodel> getFeed(@Path("user") String user);

    @GET
    public Call<ResponseBody> profilePicture(@Url String url);
}
