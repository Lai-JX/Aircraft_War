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
    private String battleModeId;
    private boolean matchFinish = false;
    private Spinner mModelSpinner = null;
    private String cmode = null;
    private String cname = null;
    private boolean soundOpen;
    private String BATTLE_MODE_URL="http://364ja28062.zicp.vip/BattleMode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        // 获取来自主页面的信息
        Intent intentFromMain = getIntent();
        soundOpen = intentFromMain.getExtras().getBoolean("soundOpen");

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

        cname = LoginActivity.userName;

        new Thread(){
            @Override
            public void run() {

                String data="";
                data = "&username="+ cname+
                        "&mode="+ cmode+
                        "&action=match";
                if(cname!=null && !matchFinish){
                    String result = PostUtil.Post(BATTLE_MODE_URL,data);

                    int msg = 0;

                    if(result.equals("fail")){
                        msg = 1;
                    }else if(result.equals("waiting")){
                        msg = 0;
                    }else{  // 匹配成功
                        msg = 2;
                        battleModeId = result;
                        matchFinish = true;
                    }

                    hand.sendEmptyMessage(msg);
                }
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
                Intent intent;
                switch (cmode){
                    case "简单模式":
                        intent = new Intent(getApplicationContext(),EasyModeGame.class);
                        break;
                    case "普通模式":
                        intent = new Intent(getApplicationContext(),CommonModeGame.class);
                        break;
                    case "困难模式":
                        intent = new Intent(getApplicationContext(),DifficultModeGame.class);
                        break;
                    default:
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                        break;
                }
                intent.putExtra("soundOpen",soundOpen);
                intent.putExtra("isBattle",true);
                startActivity(intent);
            }

        }
    };
    public void cancel(View view){
        this.matchFinish = true;
        new Thread(){
            @Override
            public void run() {

                String data = "&username="+ cname+
                        "&mode="+ cmode+
                        "&action=cancel"+
                        "&id="+battleModeId;
                System.out.println(battleModeId);
                String result = PostUtil.Post(BATTLE_MODE_URL,data);
                System.out.println(result);
            }

        }.start();

    }
}