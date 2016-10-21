package com.xiaxiao.runtimepermissionsmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public RuntimePermissionsManager runtimePermissionsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public RuntimePermissionsManager getRuntimePermissionManager(Activity activity) {
        if (runtimePermissionsManager==null) {
            runtimePermissionsManager = new RuntimePermissionsManager(activity);
        }
        return runtimePermissionsManager;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (runtimePermissionsManager!=null) {
            runtimePermissionsManager.handle(requestCode, permissions, grantResults);
        }
    }
}
