package com.example.administrator.retrofit2.thirdframework.glide;

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.Target;
import com.example.administrator.retrofit2.MyListener;

import java.io.File;

/**
 * Created by xiaxiao on 2017/1/12.
 */

public abstract class OnGlideGIFListener implements MyListener {
//    public abstract  void success(byte[] resource);
    public abstract  void success(File gifFile);
    public abstract  void failed();
}
