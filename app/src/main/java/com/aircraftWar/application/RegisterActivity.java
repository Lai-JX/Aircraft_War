package com.aircraftWar.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aircraftwar.R;
import com.aircraftWar.UserDao.UserData;
import com.aircraftWar.utils.PostUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {
    EditText name = null;
    EditText username = null;
    EditText password = null;
    private String REGISTER_URL="http://364ja28062.zicp.vip/RegisterMsg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }


    public void register(View view){



        String cname = name.getText().toString();
        String cusername = username.getText().toString();
        String cpassword = password.getText().toString();



        if(cname.length() < 2 || cusername.length() < 2 || cpassword.length() < 2 ){
            Toast.makeText(getApplicationContext(),"输入信息不符合要求请重新输入",Toast.LENGTH_LONG).show();
            return;

        }


        UserData userData = new UserData();

        userData.setName(cname);
        userData.setUsername(cusername);
        userData.setPassword(cpassword);

        new Thread(){
            @Override
            public void run() {

                String data="";
                try {
                    data = "&name="+ URLEncoder.encode(userData.getName(), "UTF-8")+
                            "&username="+ URLEncoder.encode(userData.getUsername(), "UTF-8")+
                            "&password="+ URLEncoder.encode(userData.getPassword(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String request = PostUtil.Post(REGISTER_URL,data);

                int msg = 0;
                if(request.equals("成功")){
                    msg = 2;
                }
                //已存在
                if(request.equals("已存在")){
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
                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 1)
            {
                Toast.makeText(getApplicationContext(),"该账号已经存在，请换一个账号",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 2)
            {
                //startActivity(new Intent(getApplication(),MainActivity.class));
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                //将想要传递的数据用putExtra封装在intent中
                intent.putExtra("a","註冊");
                setResult(RESULT_CANCELED,intent);
                finish();
            }

        }
    };

    /**返回登录界面**/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
        return true;
    }

}
