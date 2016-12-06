package com.xiaxiao.rxandroid_test;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/22.
 */
public interface RetrofitService {
    @POST("activity/order_detail")
    public Observable<LSMyActivity> getResult(@Query("user_id") String userId, @Query("orderid") int orderid);

    @POST("activity/order_detail")
    public Call<String> getResult2(@Query("user_id") String userId, @Query("orderid") int orderid);

    @GET("api/lore/classify")
    Observable<ClassifyBean> getClassifyBean();

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String ,String> params, @Header("Cache-Time") String time);

    @POST
    Call<String> post(@Url String url,  @FieldMap Map<String,String> params, @Header("Cache-Time") String time);

    @GET()
    Observable<LSMyActivity> Obget(@Url String url, @QueryMap Map<String, Integer> params, @Header("Cache-Time") String time);

//    @GET()
//    Observable<String> Obget(@Url String url, @QueryMap Map<String, String> params, @Header("Cache-Time") String time);

    @POST()
    Observable<String> Obpost(@Url String url, @FieldMap Map<String, String> params, @Header("Cache-Time") String time);
}
