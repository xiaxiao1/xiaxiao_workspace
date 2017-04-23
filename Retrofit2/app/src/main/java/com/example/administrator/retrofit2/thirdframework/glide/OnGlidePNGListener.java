package com.example.administrator.retrofit2.thirdframework.glide;

import android.graphics.Bitmap;

import com.example.administrator.retrofit2.MyListener;

import java.io.File;

/**
 * Created by xiaxiao on 2017/1/12.
 */

public abstract class OnGlidePNGListener implements MyListener {
//    public abstract  void success(byte[] resource);
    public abstract  void success(Bitmap bitmap);
    public abstract  void failed();
}
