package com.jeryzhang.hook.activityhooktest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.startPlugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String apkPath = "file:///android_asset/pluginapp.apk";
//                String apkPath = "android.resource://" + getPackageName() + "/" + R.raw.pluginapp;
                try {
                    InputStream is = getAssets().open("pluginapp.apk");
                    FileOutputStream fos = new FileOutputStream(new File(getCacheDir().getPath() + File.separator + "pluginapp.apk"));
                    byte[] buffer = new byte[1024];
                    int byteCount = 0;
                    while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                        // buffer字节
                        fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                    }

                    fos.flush();// 刷新缓冲区
                    is.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String apkPath = getCacheDir() + File.separator + "pluginapp.apk";
                String classz = "com.jeryzhang.hook.pluginapp.PluginActivity";
                Class cls = loadClass(apkPath, classz);
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
            }
        });
    }


    private Class loadClass(String apkPath, String classz) {

        String dexOutput = getCacheDir() + File.separator + "DEX";

        File file = new File(dexOutput);
        if (!file.exists()) {
            file.mkdirs();
        }

        DexClassLoader loader = new DexClassLoader(apkPath, dexOutput, null, getClass().getClassLoader());
        Class returnClass = null;
        try {
            returnClass = loader.loadClass(classz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnClass;
    }
}
