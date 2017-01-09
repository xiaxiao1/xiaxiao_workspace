package com.xiaxiao.listview_editview;

import android.util.Log;

/**
 * Created by Administrator on 2016/11/29.
 */
public class Fan {

    public  <E extends Student> void  print(E s){
        Log.i("xx", "E: " + s.getName());
    }
}
