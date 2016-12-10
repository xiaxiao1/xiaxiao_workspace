package com.xiaxiao.showpopupwindow;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    PopupWindow pop;
    Button showPop_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showPop_btn = (Button) findViewById(R.id.btn_main);
        showPop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(showPop_btn);
            }
        });
    }


    public void showPop(View anchor) {
        View view = View.inflate(this, R.layout.popup_view, null);
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "how are you", Toast.LENGTH_SHORT).show();
            }
        });
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
//        pop.setHeight(800);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAsDropDown(showPop_btn);
    }
}
