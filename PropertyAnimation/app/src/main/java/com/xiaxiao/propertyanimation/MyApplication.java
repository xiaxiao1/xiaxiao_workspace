package com.xiaxiao.propertyanimation;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by xx on 2016/9/27.
 */
public class MyApplication extends Application{
    public static int i=9;
    public static int getI(){
        return i;
    }
    @Override
    public void onCreate() {
        Log.i("xx", "[ExampleApplication] onCreate");
        super.onCreate();
    }
}
