package com.lwh.jackknife.demo.xpermission;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lwh.jackknife.permission.XPermission;
import com.lwh.jackknife.permission.aop.RuntimeRationale;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SETTING = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XPermission.with(this)
                .runtime()
                .permission(com.lwh.jackknife.permission.runtime.Permission.CALL_PHONE)
//                .rationale(new RuntimeRationale())
                .onGranted(new com.lwh.jackknife.permission.Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        try {
                            test();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                })
                .onDenied(new com.lwh.jackknife.permission.Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (XPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XPermission.with(MainActivity.this).runtime().setting().start(REQUEST_CODE_SETTING);
                        }
                    }
                })
                .start();
    }

    private void test() {
        Toast.makeText(this, "我要打电话了", Toast.LENGTH_SHORT).show();
    }
}
