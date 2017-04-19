package com.example.administrator.retrofit2;

import android.app.Application;

import com.example.administrator.retrofit2.thirdframework.bmob.BmobIniter;


/**
 * Created by xiaxiao on 2016/11/7.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Util.L("start application");
        BmobIniter.init(this);
    }
}
