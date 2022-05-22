package com.aircraftWar.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.aircraftWar.Dao.UserData;
import com.aircraftWar.aircraft.BossEnemy;
import com.aircraftWar.aircraft.HeroAircraft;
import com.example.aircraftwar.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EasyModeGame extends AbstractGame{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_game);
        mSurfaceView = new GameSurfaceView(this,"easy");
        heroAircraft = HeroAircraft.getInstance(1000);
        setContentView(mSurfaceView);
        super.onCreate(savedInstanceState);
        super.intent = new Intent(this,MusicService.class);
//        ImageManager imageManager = new ImageManager(this);
//        Log.i("h",String.valueOf(ImageManager.hero.getHeight()));

        // 获取来自主页面的信息
        Intent intentFromMain = getIntent();
        soundOpen = intentFromMain.getExtras().getBoolean("soundOpen");
//        System.out.println(soundOpen);

        // 游戏开始
        action();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
            heroAircraft.setLocation(event.getX(),event.getY()-150);
        }
        return true;
    }

    public void action(){
        if(soundOpen){
            startService(intent);
        }

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {

                // 飞机射出子弹
                shootAction();
            }
            // 每隔enemyCycleDuration产生敌机
            if(enemy_timeCountAndNewCycleJudge()) {
                // 产生敌机
                // 参数:精英敌机出现的概论eliteEnemyProbability，产生boss机的阈值
                creatEnemyAircraft(600, 5, 0, 30,
                        10, 30, 10);
            }
//
//            // 子弹移动
            bulletsMoveAction();
//
//            // 飞机移动
            aircraftsMoveAction();
//
//            // 子弹道具失效
//            bullutPropWorkTime();
//
//            // 道具移动
            propMoveAction();
//
            // surfaceView获取需要绘制的所有飞行物
            mSurfaceView.getAllFlyingObject(heroAircraft,enemyAircrafts,heroBullets,enemyBullets,props);
//            // 撞击检测
            crashCheckAction(10,20,40);
//
//            // 后处理
            postProcessAction();
//





            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束音乐
                if(soundOpen){
                    if(BossEnemy.bossNum == 1){

                        intent.putExtra("music","bgm_boss_close");
                        startService(intent);
                    }
                    intent.putExtra("music","game_over");
                    startService(intent);
                }

                // 游戏结束
                gameOverFlag = true;
                executorService.shutdown();
                System.out.println("Game Over!");
                String userID = "hi";
                UserData userData = UserData.userData.getUserData();
                userData.setDate(new Date());
                userData.setScore(score);
                Intent intent = new Intent(this, EnterNameActivity.class);
//                System.out.println("xxx");
                startActivity(intent);

            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }



}
