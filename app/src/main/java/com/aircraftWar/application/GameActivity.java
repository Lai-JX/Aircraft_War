package com.aircraftWar.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aircraftwar.R;

public class GameActivity extends AppCompatActivity {

    private AbstractGame game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");    // 获取上一个页面传递过来的参数（游戏模式）
//        TextView text = findViewById(R.id.text);
//        text.setText(mode);
        if(mode.equals("1")){
//            game = new EasyModeGame(this);
            setContentView(R.layout.activity_game);
        }
    }
}