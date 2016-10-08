package com.viewdraghelper.xiaxiao.activityanimation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doba(View view) {
        Toast.makeText(MainActivity.this, "hahahahah", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,Activity2.class));
//        overridePendingTransition(R.anim.bottom_in,0);
    }
}
