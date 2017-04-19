package com.example.administrator.retrofit2.thirdframework.bmob;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2016/11/4.
 */
public  interface  BmobListener {
    public void onSuccess(Object object);
    public void onError(BmobException e);


}
