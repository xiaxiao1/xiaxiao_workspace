package com.xiaxiao.rxandroid_test;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/22.
 */
public interface RetrofitHelper {
    @POST("activity/order_detail")
    public Observable<LSMyActivity> getResult(@Query("user_id") String userId, @Query("orderid") int orderid);

    @POST("activity/order_detail")
    public Call<String> getResult2(@Query("user_id") String userId, @Query("orderid") int orderid);

    @GET("api/lore/classify")
    Observable<ClassifyBean> getClassifyBean();
}
