package com.aircraftWar.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aircraftwar.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册点击监听器
        findViewById(R.id.btn_easy_mode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.btn_easy_mode){
            Intent intent = new Intent(this,EasyModeGame.class);
//            intent.putExtra("mode","1");      // 添加要传递给游戏界面的参数
            startActivity(intent);
        }
    }
}