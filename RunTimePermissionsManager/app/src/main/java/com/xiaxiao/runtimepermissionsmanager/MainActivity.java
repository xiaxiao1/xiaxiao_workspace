package com.xiaxiao.runtimepermissionsmanager;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    Button btn;
    String test = "今天天气不错";
    String testPermission = Manifest.permission.READ_SMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        runtimePermissionsManager=getRuntimePermissionManager(this);
        runtimePermissionsManager.requestPermissions();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"开始请求。。。",Toast.LENGTH_SHORT).show();
                runtimePermissionsManager.workwithPermission(testPermission, new RuntimePermissionsManager.RequestCallback() {

                    @Override
                    public void requestSuccess() {
                        doSomething("被授权："+test);
                    }

                    @Override
                    public void requestFailed() {
                        Toast.makeText(MainActivity.this,"请求被拒绝了。。。",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void doSomething(String s) {
        Toast.makeText(this,"xiao: "+s,Toast.LENGTH_SHORT).show();
    }
}
