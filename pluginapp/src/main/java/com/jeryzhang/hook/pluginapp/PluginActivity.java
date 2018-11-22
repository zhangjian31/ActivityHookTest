package com.jeryzhang.hook.pluginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PluginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        Toast.makeText(this,"PluginActivity",Toast.LENGTH_SHORT).show();
    }
}
