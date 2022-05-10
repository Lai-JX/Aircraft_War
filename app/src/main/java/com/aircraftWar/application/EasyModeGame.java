package com.aircraftWar.application;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.aircraftWar.aircraft.HeroAircraft;
import com.example.aircraftwar.R;

import java.util.concurrent.TimeUnit;

public class EasyModeGame extends AbstractGame{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_game);
        mSurfaceView = new GameSurfaceView(this,"easy");
        heroAircraft = HeroAircraft.getInstance(1000);
        setContentView(mSurfaceView);
        super.onCreate(savedInstanceState);
        ImageManager imageManager = new ImageManager(this);
//        Log.i("h",String.valueOf(ImageManager.hero.getHeight()));
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
//        init();
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
//            Log.i("time",String.valueOf(time));
//            // bgm和boss_bgm线程是否失效，失效则重新添加，以实现循环播放
//            if(chooseDifficulty.isSoundOpen() && !bgm.isAlive()){
//                bgm = new MusicThread("src/videos/bgm.wav");
//                bgm.start();
//            }
//            if(chooseDifficulty.isSoundOpen() && BossEnemy.bossNum==1 && !boss_bgm.isAlive()){
//                boss_bgm = new MusicThread("src/videos/boss_bgm.wav");
//                boss_bgm.start();
//            }
//
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
//            propMoveAction();
//
//            // 撞击检测
            crashCheckAction(10,20,40);
//
//            // 后处理
            postProcessAction();
//
            // surfaceView获取需要绘制的所有飞行物
            mSurfaceView.getAllFlyingObject(heroAircraft,enemyAircrafts,heroBullets,enemyBullets,props);
//            //每个时刻重绘界面
//            repaint();




            // 游戏结束检查
//            if (heroAircraft.getHp() <= 0) {
//                // 游戏结束音乐
//                if(chooseDifficulty.isSoundOpen()){
//                    new MusicThread("src/videos/game_over.wav").start();
//                    bgm.setStop(true);
//                    if(BossEnemy.bossNum == 1){
//                        boss_bgm.setStop(true);
//                    }
//                }
//
//                // 游戏结束
//                gameOverFlag = true;
//                executorService.shutdown();
//
//                System.out.println("Game Over!");
//            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

//    // 添加背景和hero机
//    public void init(){
//        // 设置图片位置
//        params = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//        );
//        params.gravity = backGroundTop | Gravity.NO_GRAVITY;
//        // 添加背景图片
//        addContentView(ImageManager.bg2,params);
////        addContentView(ImageManager.hero,params);
//
//        // 添加英雄机控制
//        heroController = new HeroController(this,heroAircraft);
//        addContentView(heroController,new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT));
//        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//        addContentView(ImageManager.hero,params);
//
//        draw();
//    }
//
//    protected void draw(){
//        // 绘制英雄机
//        Log.i("x1",String.valueOf(heroAircraft.getLocationX()));
//        Log.i("y1",String.valueOf(heroAircraft.getLocationY()));
//            ImageManager.hero.setTranslationY(-1*heroAircraft.getLocationY());
//            ImageManager.hero.setTranslationX(heroAircraft.getLocationX());
//
//    }




}
