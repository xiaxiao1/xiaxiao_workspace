package com.example.administrator.retrofit2.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.retrofit2.R;
import com.example.administrator.retrofit2.bean.MyUser;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class Util {
    private static String[] colorNumber={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    private static final String TAG = "xiaxiao";
    public  static void L(String message) {
        Log.i(TAG, message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    public static String getTimeStr(long l) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        return s.format(l);
    }

    public static String getRandomColor() {
        int i;
        StringBuffer sb = new StringBuffer();
        sb.append("#");
        for (int a=0;a<6;a++) {
            i= (int) (Math.random() * 16);
            sb.append(colorNumber[i]);
        }
        return sb.toString();
    }

    public static boolean isLogin(){
        MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        return (bmobUser!=null&&bmobUser.getObjectId()!=null)||GlobalData.bmobUser!=null;
    }

    public static MyUser getUser2() {
        if (isLogin()) {
            return BmobUser.getCurrentUser(MyUser.class);
        }
        return null;
    }



    public static String getUserId() {
        return GlobalData.userId;
    }
    public static void setUserId(String userId) {
        GlobalData.userId=userId;
    }

    public static void setUser(MyUser bmobUser) {
        GlobalData.bmobUser=bmobUser;
    }

    public static MyUser getUser() {
        MyUser myUser= BmobUser.getCurrentUser(MyUser.class);
        if (myUser==null||myUser.getObjectId()==null||myUser.getObjectId().equals("")) {
            return GlobalData.bmobUser;
        }
        return myUser;
    }

    public static void hideSoftInput(Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken() , 0);
    }



    public static UIDialog takePhoto(final Context context) {
       UIDialog a=  new UIDialog(context);
        a.showTakePhotoDialog(new UIDialog.CustomDialogListener() {
            @Override
            public void onItemClick(int index) {
                switch (index) {
                    case 0:
                        //paizhao
                        BitmapUtil.doTakePhoto((Activity) context);
                        break;
                    case 1:
                        //xiangce
                        BitmapUtil.doPickPhotoFromGallery((Activity) context);
                        break;
                    case 2:
                        //quxiao
//                        uiDialog.dismissTakePhotoDialog();
                        break;
                }
            }
        });
        return a;
    }

    public static <T extends BmobObject> T findObject(T obj, List<T> objs) {
        for (T o:objs) {
            if (o.getObjectId().equals(obj.getObjectId())) {
                return o;
            }
        }
        return null;
    }




    public static int getSelfAdaptionHeight(int fixedWidth, Bitmap bitmap) {
        return fixedWidth*bitmap.getHeight()/bitmap.getWidth();

    }

    public static Point getDisplay(Activity context) {
        Display display=context.getWindowManager().getDefaultDisplay();

        Point point=new Point();
        display.getSize(point);
        return point;
    }

}
