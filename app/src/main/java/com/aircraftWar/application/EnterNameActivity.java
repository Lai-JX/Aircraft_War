package com.aircraftWar.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aircraftWar.GameDataDao.GameData;
import com.aircraftWar.myIOFunction.MyObjectOutputStream;
import com.example.aircraftwar.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EnterNameActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText edit;//文件编辑框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        findViewById(R.id.确认).setOnClickListener(this);
        findViewById(R.id.取消).setOnClickListener(this);
        edit = (EditText) findViewById(R.id.et_username);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.确认){
            String userID = edit.getText().toString();
            GameData.gameData.setPlayerID(userID);
            GameData gameData = GameData.gameData.getUserData();
            try {
                FileOutputStream out = null;
                if(MainActivity.difficulty == 1) {
                    System.out.println("try out");
                    out = openFileOutput("easyMode_GameData", Context.MODE_APPEND);
                    System.out.println("try success");
                }
                else if(MainActivity.difficulty == 2){
                    out = openFileOutput("commonMode_GameData", Context.MODE_APPEND);
                }
                else if(MainActivity.difficulty == 3){
                    out = openFileOutput("hardMode_GameData", Context.MODE_APPEND);
                }
                ObjectOutputStream oos = new MyObjectOutputStream(out);
                oos.writeObject(gameData);
                oos.close();
                System.out.println("try new intent");
                Intent intent = new Intent(this, RankActivity.class);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == R.id.取消){
            Intent intent = new Intent(this, RankActivity.class);
            startActivity(intent);
        }
    }
}