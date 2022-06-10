package com.aircraftWar.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.aircraftWar.UserDao.UserData;
import com.aircraftWar.utils.PostUtil;
import com.example.aircraftwar.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BattleActivity extends AppCompatActivity {

    private Spinner mModelSpinner = null;
    private String cmode = null;
    private String BATTLE_MODE_URL="http://364ja28062.zicp.vip/BattleMode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        mModelSpinner = (Spinner)findViewById(R.id.mode_spin);
        String[] mode = {"简单模式","普通模式","困难模式"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mode);//创建Arrayadapter适配器
        mModelSpinner.setAdapter(adapter);
        mModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = mModelSpinner.getItemAtPosition(i).toString();
                cmode = text;
                Toast.makeText(BattleActivity.this, text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void matching(View view){



        String cname = LoginActivity.userName;

        new Thread(){
            @Override
            public void run() {

                String data="";
                data = "&name="+ cname+
                        "&mode="+ cmode;

                String request = PostUtil.Post(BATTLE_MODE_URL,data);

                int msg = 0;
                if(request.equals("成功")){
                    msg = 2;
                }
                //已存在
                if(request.equals("失败")){
                    msg = 1;
                }

                hand.sendEmptyMessage(msg);

            }
        }.start();
    }
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0)
            {
                Toast.makeText(getApplicationContext(),"等待中",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 1)
            {
                Toast.makeText(getApplicationContext(),"匹配失败",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 2)
            {
                Toast.makeText(getApplicationContext(),"匹配成功",Toast.LENGTH_LONG).show();
            }

        }
    };
}