package com.example.administrator.retrofit2.thirdframework.retrofit;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/7/29.
 */
public interface gitapi {
    @GET("/users/{user}")
    Call<gitmodel> getFeed(@Path("user") String user);

    @GET
    public Call<ResponseBody> profilePicture(@Url String url);
}
