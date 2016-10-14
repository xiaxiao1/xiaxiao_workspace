package com.xiaxiao.visituiinthread;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 可以成功的访问UI..
 * 这是因为thread 运行的太早，这时候程序还没有运行到对线程检测的初始化完成的阶段，
 * 所以这里没有对修改UI的代码进行判断，如果thread睡眠一段时间的话，以下代码就会报错了。
 */
public class MainActivity extends AppCompatActivity {
    TextView tv1;
    Button btn;
    ImageView img;
    RelativeLayout root;
    float currentRotation;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv);
        btn=(Button) findViewById(R.id.run_btn);
        img = (ImageView) findViewById(R.id.img);
        root = (RelativeLayout) findViewById(R.id.root_rl);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        i=(int) event.getY();
                        Log.i("xx","dowm "+i);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("xx","up ");
                        currentRotation=img.getRotationX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x= (int) event.getY();
                        Log.i("xx","move "+x);
                        img.setRotationX(currentRotation-(x-i)/10);
                        break;
                }
                return true;
            }
        });
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                tv1.setText("Thread is visiting UI");
            }
        }).start();*/
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           /* if (i % 2 == 0) {
                tv1.animate().x(700).setDuration(2000);
            } else {
                tv1.animate().x(40).alpha(1);
            }
            i++;*/
            ObjectAnimator a = ObjectAnimator.ofFloat(img, "rotationX", img.getRotationX(), (img.getRotationX()+360));
//            ObjectAnimator a2 = ObjectAnimator.ofFloat(img, "rotationX", img.getRotationX(), (img.getRotationX()+180));
            AnimatorSet set=new AnimatorSet();
//            a2.setRepeatCount(-1);
//            set.play(a).with(a2);
            set.setDuration(1400);
            set.play(a);
            set.start();
        }
    });


        ValueAnimator a;
        ViewPropertyAnimator c;

    }
}
