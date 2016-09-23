package com.xiaxiao.propertyanimation;

import android.animation.ValueAnimator;
import android.graphics.drawable.ClipDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 属性动画
 * 、是一种不断对值进行操作的机制，并将值赋值到指定的对象的指定属性上
 * 改变的是view的属性
 * 确切的说  是对象的属性
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img1;
    ClipDrawable clipDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        img1 = (ImageView) findViewById(R.id.img1);
        clipDrawable=(ClipDrawable)img1.getDrawable();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                //TODO implement
                ValueAnimator animator = ValueAnimator.ofInt(0, 10000);
                animator.setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                 @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int currentValue = (int) animation.getAnimatedValue();
                    Log.d("TAG", "cuurent value is " + currentValue);
                     clipDrawable.setLevel(currentValue);
                 }
                });
                animator.start();
                break;
            case R.id.btn2:
                //TODO implement
                break;
            case R.id.btn3:
                //TODO implement
                break;
            case R.id.btn4:
                //TODO implement
                break;
            case R.id.btn5:
                //TODO implement
                break;
            case R.id.btn6:
                //TODO implement
                break;
            case R.id.btn7:
                //TODO implement
                break;
        }
    }
}
