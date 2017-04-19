package com.example.administrator.retrofit2.thirdframework.bmob;


import com.example.administrator.retrofit2.util.Util;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2016/11/4.
 */
public abstract class SuccessListener implements BmobListener {

        @Override
        public void onError(BmobException e) {
                if (e!=null) {
                        Util.L(e.getMessage());
                }
        }
}
