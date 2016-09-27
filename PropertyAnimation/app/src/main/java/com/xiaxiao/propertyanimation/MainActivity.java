package com.xiaxiao.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Interpolator;
import android.graphics.drawable.ClipDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 属性动画
 * 、是一种不断对值进行操作的机制，并将值赋值到指定的对象的指定属性上
 * 改变的是view的属性
 * 确切的说  是对象的属性
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
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
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
//        clipDrawable=(ClipDrawable)img1.getDrawable();
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
                    Log.d("TAG", "cuurent value is a " + currentValue);
                     //这是通过裁剪来实现的，和动画无关
                     clipDrawable.setLevel(currentValue);

                 }
                });
                animator.start();
                break;
            case R.id.btn2:
                //TODO implement
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(img1, "alpha", 1f, 0f, 1f);
                animator2.setDuration(2000);
                animator2.start();
                break;
            case R.id.btn3:
                //TODO implement
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(img1, "rotation", 0f, 360f);
                animator3.setDuration(2000);
                animator3.setInterpolator(new BounceInterpolator());
                animator3.start();
                break;
            case R.id.btn4:
                //TODO implement
                float curTranslationX = img1.getTranslationX();
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(img1, "translationX", curTranslationX, 300f);
                animator4.setDuration(1000);
                animator4.setInterpolator(new AnticipateOvershootInterpolator());
                animator4.start();
                break;
            case R.id.btn5:
                //TODO implement
                AnimatorSet animSet=new AnimatorSet();
                float X = img1.getTranslationX();
                float Y =img1.getTranslationY();
                ObjectAnimator manimator1 = ObjectAnimator.ofFloat(img1, "scaleX", 1f,0f,1f);
                ObjectAnimator manimator11 = ObjectAnimator.ofFloat(img1, "scaleY", 1f,0f,1f);
                ObjectAnimator manimator12 = ObjectAnimator.ofFloat(img2, "scaleX", 1f,0f,1f);
                ObjectAnimator manimator122 = ObjectAnimator.ofFloat(img2, "scaleY", 1f,0f,1f);
                ObjectAnimator manimator2 = ObjectAnimator.ofFloat(img2, "scaleX", 1f,0f,1f);
//                manimator2.setStartDelay(500);
                manimator1.setRepeatCount(-1);
                manimator11.setRepeatCount(-1);
                manimator12.setRepeatCount(-1);
                manimator122.setRepeatCount(-1);
                manimator12.setStartDelay(500);
                manimator122.setStartDelay(500);
                ObjectAnimator manimator3 = ObjectAnimator.ofFloat(img3, "scaleX", 1f,0f,1f);
                ObjectAnimator manimator4 = ObjectAnimator.ofFloat(img4, "scaleX", 1f,0f,1f);
//                manimator3.setStartDelay(1000);
//                animSet.play(manimator1).after(manimator2).after(manimator3);
                animSet.play(manimator1).with(manimator11).with(manimator12).with(manimator122);
                animSet.play(manimator3).with(manimator4);
//                animSet.play(manimator3).before(manimator4);
//                animSet.play(manimator3).with(manimator1).after(manimator2);
                animSet.setDuration(1000);
                animSet.start();
                Log.i("xx","hsadhsadhsiad");
                Toast.makeText(this,"sdsdsds",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn6:
                //TODO implement
                Log.i("xx","hsadhsadhsiad");
                Toast.makeText(this,MyApplication.getI()+"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn7:
                //TODO implement
                break;
        }
    }
}
