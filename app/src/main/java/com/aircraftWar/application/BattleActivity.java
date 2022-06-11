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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class BattleActivity extends AppCompatActivity {
    /**
     * Scheduled 线程池，用于任务调度
     */
    private ScheduledExecutorService executorService;
    private Intent intent;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 30;
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
        String[] mode1 = {"easy","common","difficult"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mode);//创建Arrayadapter适配器
        mModelSpinner.setAdapter(adapter);
        mModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = mModelSpinner.getItemAtPosition(i).toString();
                cmode = mode1[i];
                Toast.makeText(BattleActivity.this, text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 设置线程池
        ThreadFactory httpRequest = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("httpRequest thread");
                return t;
            }
        };
        //Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(1,httpRequest);

    }
    public void matching(View view){
        cname = LoginActivity.userName;

        // 定时任务：向服务端发送请求
        Runnable task = () -> {
            String data="";
            data = "&username="+ cname+
                    "&mode="+ cmode+
                    "&action=match"+
                    "&id="+battleModeId;
            if(cname!=null && !matchFinish){//cname!=null && !matchFinish)
                String result = PostUtil.Post(BATTLE_MODE_URL,data);
                System.out.println(result);

                int msg = -1;
                String res[] = result.split("&");
                System.out.println(res[0]+res[1]);
                battleModeId = res[0];
                System.out.println("cmode1="+cmode);
                if(res[1].equals("fail")){
                    msg = 1;
                }else if(res[1].equals("waiting")){
                    msg = 0;
                }else if(res[1].equals("Success")){  // 匹配成功
                    msg = 2;
                    battleModeId = result;
                    matchFinish = true;
                }
                System.out.println("cmode="+cmode);
                hand.sendEmptyMessage(msg);
                if(matchFinish){
                    executorService.shutdown();
                    System.out.println("cmode="+cmode);
                    switch (cmode){
                        case "easy":
                            intent = new Intent(getApplicationContext(),EasyModeGame.class);
                            break;
                        case "common":
                            intent = new Intent(getApplicationContext(),CommonModeGame.class);
                            break;
                        case "difficult":
                            intent = new Intent(getApplicationContext(),DifficultModeGame.class);
                            break;
                        default:
                            intent = new Intent(getApplicationContext(),MainActivity.class);
                            break;
                    }
                    intent.putExtra("soundOpen",soundOpen);
                    intent.putExtra("isBattle",true);
                    intent.putExtra("battleId",battleModeId);
                    startActivity(intent);
                }

            }
        };


//        new Thread(){
//            @Override
//            public void run() {
//
//
//
//            }
//        }.start();

        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0)
            {
//                Toast.makeText(getApplicationContext(),"等待中",Toast.LENGTH_LONG).show();
                Toast toast=Toast.makeText(getApplicationContext(),"等待中", Toast.LENGTH_LONG);
                showMyToast(toast,40);// 设置显示时间
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
    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
    public void cancel(View view){
        this.matchFinish = true;
        new Thread(){
            @Override
            public void run() {
                matchFinish = true;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(){
            @Override
            public void run() {
                String data = "&username="+ cname+
                        "&mode="+ cmode+
                        "&action=delete"+
                        "&id="+battleModeId;
                System.out.println(battleModeId);
                String result = PostUtil.Post(BATTLE_MODE_URL,data);
                System.out.println(result);
            }

        }.start();
    }
}
//package com.aircraftWar.application;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.aircraftWar.UserDao.UserData;
//import com.aircraftWar.utils.PostUtil;
//import com.example.aircraftwar.R;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.TimeUnit;
//
//public class BattleActivity extends AppCompatActivity {
//    /**
//     * Scheduled 线程池，用于任务调度
//     */
//    protected ScheduledExecutorService executorService;
//
//
//    /**
//     * 时间间隔(ms)，控制刷新频率
//     */
//    protected int timeInterval = 30;
//    private String battleModeId;
//    private boolean matchFinish = false;
//    private Spinner mModelSpinner = null;
//    private String cmode = null;
//    private String cname = null;
//    private boolean soundOpen;
//    private String BATTLE_MODE_URL="http://364ja28062.zicp.vip/BattleMode";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_battle);
//        // 获取来自主页面的信息
//        Intent intentFromMain = getIntent();
//        soundOpen = intentFromMain.getExtras().getBoolean("soundOpen");
//
//        mModelSpinner = (Spinner)findViewById(R.id.mode_spin);
//        String[] mode = {"简单模式","普通模式","困难模式"};
//        String[] mode1 = {"easy","common","difficult"};
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mode);//创建Arrayadapter适配器
//        mModelSpinner.setAdapter(adapter);
//        mModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String text = mModelSpinner.getItemAtPosition(i).toString();
//                cmode = mode1[i];
//                Toast.makeText(BattleActivity.this, text, Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//
//        // 设置线程池
//        ThreadFactory httpRequest = new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread t = new Thread(r);
//                t.setName("httpRequest thread");
//                return t;
//            }
//        };
//        //Scheduled 线程池，用于定时任务调度
//        executorService = new ScheduledThreadPoolExecutor(1,httpRequest);
//
//    }
//    public void matching(View view){
//        cname = LoginActivity.userName;
//
//        // 定时任务：向服务端发送请求
//        Runnable task = () -> {
//            String data="";
//            data = "&username="+ cname+
//                    "&mode="+ cmode+
//                    "&action=match"+
//                    "&id="+battleModeId;
//            if(cname!=null && !matchFinish){//cname!=null && !matchFinish)
//                String result = PostUtil.Post(BATTLE_MODE_URL,data);
//                System.out.println(result);
//
//                int msg = -1;
//                String res[] = result.split("&");
//                System.out.println(res[0]+res[1]);
//                battleModeId = res[0];
//
//                if(res[1].equals("fail")){
//                    msg = 1;
//                }else if(res[1].equals("waiting")){
//                    msg = 0;
//                }else if(res[1].equals("Success")){  // 匹配成功
//                    msg = 2;
//                    battleModeId = result;
//                    matchFinish = true;
//                }
//                if(matchFinish){
//                    executorService.shutdown();
//                }
//                hand.sendEmptyMessage(msg);
//
//            }
//        };
//
//
////        new Thread(){
////            @Override
////            public void run() {
////
////
////
////            }
////        }.start();
//
//        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
//    }
//    final Handler hand = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            if(msg.what == 0)
//            {
////                Toast.makeText(getApplicationContext(),"等待中",Toast.LENGTH_LONG).show();
//                Toast toast=Toast.makeText(getApplicationContext(),"等待中", Toast.LENGTH_LONG);
//                showMyToast(toast,30);// 设置显示时间
//            }
//            if(msg.what == 1)
//            {
//                Toast.makeText(getApplicationContext(),"匹配失败",Toast.LENGTH_LONG).show();
//
//            }
//            if(msg.what == 2)
//            {
//                Toast.makeText(getApplicationContext(),"匹配成功",Toast.LENGTH_LONG).show();
//                Intent intent;
//                switch (cmode){
//                    case "easy":
//                        intent = new Intent(getApplicationContext(),EasyModeGame.class);
//                        break;
//                    case "common":
//                        intent = new Intent(getApplicationContext(),CommonModeGame.class);
//                        break;
//                    case "difficult":
//                        intent = new Intent(getApplicationContext(),DifficultModeGame.class);
//                        break;
//                    default:
//                        intent = new Intent(getApplicationContext(),MainActivity.class);
//                        break;
//                }
//                intent.putExtra("soundOpen",soundOpen);
//                intent.putExtra("isBattle",true);
//                startActivity(intent);
//            }
//
//        }
//
//    };
//    public void showMyToast(final Toast toast, final int cnt) {
//        final Timer timer =new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.show();
//            }
//        },0,3000);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.cancel();
//                timer.cancel();
//            }
//        }, cnt );
//    }
//    public void cancel(View view){
//        this.matchFinish = true;
//        new Thread(){
//            @Override
//            public void run() {
//                matchFinish = true;
//                String data = "&username="+ cname+
//                        "&mode="+ cmode+
//                        "&action=cancel"+
//                        "&id="+battleModeId;
//                System.out.println(battleModeId);
//                String result = PostUtil.Post(BATTLE_MODE_URL,data);
//                System.out.println(result);
//            }
//
//        }.start();
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        new Thread(){
//            @Override
//            public void run() {
//                String data = "&username="+ cname+
//                        "&mode="+ cmode+
//                        "&action=delete"+
//                        "&id="+battleModeId;
//                System.out.println(battleModeId);
//                String result = PostUtil.Post(BATTLE_MODE_URL,data);
//                System.out.println(result);
//            }
//
//        }.start();
//    }
//}