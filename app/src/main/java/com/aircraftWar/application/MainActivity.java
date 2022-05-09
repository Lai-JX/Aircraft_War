package com.aircraftWar.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

import com.example.aircraftwar.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取屏幕宽高
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(outSize);
        WINDOW_HEIGHT = outSize.y;
        WINDOW_WIDTH = outSize.x;

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