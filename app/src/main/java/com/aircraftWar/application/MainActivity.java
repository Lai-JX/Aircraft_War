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
    public static int difficulty;
    private boolean soundOpen = true;


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
        findViewById(R.id.btn_common_mode).setOnClickListener(this);
        findViewById(R.id.btn_difficult_mode).setOnClickListener(this);
        findViewById(R.id.sound_switch).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_battle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.sound_switch){
            soundOpen = !soundOpen;
            System.out.println("sound "+soundOpen);
        }
        if(v.getId()==R.id.btn_easy_mode){
            difficulty = 1;
            Intent intent = new Intent(this,EasyModeGame.class);
            intent.putExtra("soundOpen",soundOpen);      // 添加要传递给游戏界面的参数
            startActivity(intent);
        }
        else if(v.getId()==R.id.btn_common_mode){
            difficulty = 2;
            System.out.println("开始选择游戏模式");
            Intent intent = new Intent(this,CommonModeGame.class);
            System.out.println("选择游戏模式完毕");
            intent.putExtra("soundOpen",soundOpen);      // 添加要传递给游戏界面的参数
            System.out.println("设置intent完毕");
            startActivity(intent);
            System.out.println("startActivity完毕");
        }
        else if(v.getId()==R.id.btn_difficult_mode){
            difficulty = 3;
            Intent intent = new Intent(this,DifficultModeGame.class);
            intent.putExtra("soundOpen",soundOpen);      // 添加要传递给游戏界面的参数
            startActivity(intent);
        }else if(v.getId()==R.id.btn_login){
            Intent intent = new Intent(this,LoginActivity.class);
//            intent.putExtra("soundOpen",soundOpen);
            startActivity(intent);
        }
        else if(v.getId()==R.id.btn_battle){
            Intent intent = new Intent(this,BattleActivity.class);
            intent.putExtra("soundOpen",soundOpen);
            startActivity(intent);
        }
    }
}