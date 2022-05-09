package com.aircraftWar.application;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.aircraftWar.aircraft.AbstractEnemyAircraft;
import com.aircraftWar.aircraft.EnemyFactory;
import com.aircraftWar.aircraft.HeroAircraft;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class AbstractGame extends AppCompatActivity {
    protected FrameLayout.LayoutParams params;
    protected int backGroundTop = 0;
    protected GameSurfaceView mSurfaceView; // 绘制游戏画面

    /**
     * Scheduled 线程池，用于任务调度
     */
    protected final ScheduledExecutorService executorService;
    protected HeroController heroController;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 30;

    protected HeroAircraft heroAircraft;

    protected final List<AbstractEnemyAircraft> enemyAircrafts;
//    protected final List<BaseBullet> heroBullets;
//    protected final List<BaseBullet> enemyBullets;
//    protected final List<AbstractProp> props;
//    protected PropFactory propFactory;
    protected EnemyFactory enemyFactory;
    //    private RecordDao recordDao;
    protected MusicThread boss_bgm;
    protected MusicThread bgm;


    protected int enemyNumber = 0;

    protected boolean gameOverFlag = false;



    protected static int score = 0;
    protected int time = 0;
    protected int counter = 0;// 标志产生敌机的阈值
    protected int BulletPropStart = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射
     */
    protected int cycleDuration = 500;
    private int cycleTime = 0;
    /**
     * 周期（ms)
     * 指示敌机的产生频率
     */
    protected int enemyCycleDuration = 400;
    protected int enemyCycleTime = 0;
    // 敌机速度提升倍率
    protected double enemyImproveRate = 1.0;
    // 不产生道具的概率
    protected double noPropProbability = 0.1;
    // 子弹道具持续时间
    protected int bulletPropTime = 8000;
    // 产生精英敌机的概率
    protected double eliteEnemyProbability = 0.3;




    public AbstractGame() {


        enemyAircrafts = new LinkedList<>();
//        heroBullets = new LinkedList<>();
//        enemyBullets = new LinkedList<>();
//        props = new LinkedList<>();

        ThreadFactory gameThread = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("game thread");
                return t;
            }
        };

        //Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(1,gameThread);


    }

}
