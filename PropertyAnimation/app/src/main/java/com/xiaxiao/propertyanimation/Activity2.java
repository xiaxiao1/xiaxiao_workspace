package com.xiaxiao.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 属性动画
 * 、是一种不断对值进行操作的机制，并将值赋值到指定的对象的指定属性上
 * 改变的是view的属性
 * 确切的说  是对象的属性
 *
 */
public class Activity2 extends Activity implements View.OnClickListener{

    private RelativeLayout rootRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        rootRl = (RelativeLayout) findViewById(R.id.root_rl);
        rootRl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final TextView t=new TextView(this);
        t.setText(R.string.add_text);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        t.setLayoutParams(params);
        t.setGravity(Gravity.CENTER);
        t.setBackgroundColor(Color.parseColor("#dddddd"));
        rootRl.addView(t);
        ViewTreeObserver observer=t.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.i("xx","getWidth:"+t.getMeasuredWidth());
                t.startAnimation(AnimationUtils.loadAnimation(Activity2.this,R.anim.home_banner));
                return true;
            }
        });
        Log.i("xx","getWidth:"+t.getMeasuredWidth());
    }
}