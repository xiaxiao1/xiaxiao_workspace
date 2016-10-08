package com.viewdraghelper.xiaxiao.activityanimation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends Activity implements View.OnTouchListener{
    TextView ttv;
     private String ss;
    ImageView img;
    ScrollView sc;
    RelativeLayout.LayoutParams params;
    int imgW;
    int imgH;
    int tempH;
    LinearLayout ll;
    RelativeLayout.LayoutParams rlparams;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        ttv = (TextView) findViewById(R.id.ttv);
        img = (ImageView) findViewById(R.id.img);
        sc = (ScrollView) findViewById(R.id.sc_root);
        ll = (LinearLayout) findViewById(R.id.ll);
        rlparams=(RelativeLayout.LayoutParams)ll.getLayoutParams();
        sc.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int
                    oldScrollY) {
                Log.i("xx","down  "+scrollX+"   "+scrollY+"   "+oldScrollX+"  "+oldScrollY);

                if (scrollY<=imgH) {
                    Log.i("xx","ccc   "+(-scrollY/10));
                    rlparams.setMargins(0,-scrollY/5,0,0);
                    ll.setLayoutParams(rlparams);
                }
            }
        });
        ttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity2.this.finish();
                Activity2.this.startActivity(new Intent(Activity2.this,MainActivity.class));
            }
        });
        ss=null;
        Toast.makeText(this,ss,Toast.LENGTH_SHORT).show();
    }

    float downY=0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {

       /* imgW=img.getMeasuredWidth();
        imgH=img.getMeasuredHeight();
        if (event.getAction() == event.ACTION_DOWN) {
            downY=event.getY();
            Log.i("xx","down  "+event.getY());
        }
        if (event.getAction()==event.ACTION_MOVE) {
            Log.i("xx","move  "+event.getY()+"   "+(event.getY()-downY));
            tempH=(int)((1-Math.abs(event.getY()-downY)*10/imgH)*imgH);
            params.height=tempH;
            params.width=imgW;
            img.setLayoutParams(params);
        }*/
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        params=(RelativeLayout.LayoutParams) img.getLayoutParams();
        imgW=img.getMeasuredWidth();
        imgH=img.getMeasuredHeight();
    }
}
