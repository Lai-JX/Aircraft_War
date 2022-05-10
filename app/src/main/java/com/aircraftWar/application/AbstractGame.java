package com.aircraftWar.application;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.aircraftWar.aircraft.AbstractAircraft;
import com.aircraftWar.aircraft.AbstractEnemyAircraft;
import com.aircraftWar.aircraft.BossEnemy;
import com.aircraftWar.aircraft.BossEnemyFactory;
import com.aircraftWar.aircraft.EliteEnemy;
import com.aircraftWar.aircraft.EliteEnemyFactory;
import com.aircraftWar.aircraft.EnemyFactory;
import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.aircraft.MobEnemy;
import com.aircraftWar.aircraft.MobEnemyFactory;
import com.aircraftWar.basic.AbstractFlyingObject;
import com.aircraftWar.bullet.BaseBullet;
import com.aircraftWar.bullet.EnemyBullet;
import com.aircraftWar.prop.AbstractProp;
import com.aircraftWar.prop.BloodProp;
import com.aircraftWar.prop.BloodPropFactory;
import com.aircraftWar.prop.BombProp;
import com.aircraftWar.prop.BombPropFactory;
import com.aircraftWar.prop.BulletProp;
import com.aircraftWar.prop.BulletPropFactory;
import com.aircraftWar.prop.PropFactory;
import com.aircraftWar.strategy.DirectShoot;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.*;
import java.util.concurrent.*;

public class AbstractGame extends AppCompatActivity {
    protected FrameLayout.LayoutParams params;
    protected int backGroundTop = 0;
    protected GameSurfaceView mSurfaceView; // 绘制游戏画面
    public static boolean soundOpen;    // 是否开启音效

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
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<AbstractProp> props;
//    protected PropFactory propFactory;
    protected EnemyFactory enemyFactory;
    //    private RecordDao recordDao;
    protected MusicThread boss_bgm;
    protected MusicThread bgm;
    protected PropFactory propFactory;


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
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

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
    public static int getScore() {
        return score;
    }

    public boolean isGameOverFlag() {
        return gameOverFlag;
    }
    /** 产生敌机
     * 参数:精英敌机出现的概论eliteEnemyProbability,产生boss机的阈值creatBoss_Score,最大敌机数量enemyMaxnumber,boss敌机的血量boosBlood
     *    精英敌机的血量eliteEnemyBlood，精英敌机的竖直方向速度精英敌机的血量eliteEnemySpeedY,普通敌机的血量mobEnemyBlood，普通敌机的竖直方向速度精英敌机的血量mobEnemySpeedY
     *
     */
    protected void creatEnemyAircraft(int creatBoss_Score, int enemyMaxNumber,int boosBlood,
                                      int eliteEnemyBlood,int eliteEnemySpeedY,int mobEnemyBlood,int mobEnemySpeedY){
        if(eliteEnemyProbability>0.95){eliteEnemyProbability=0.95;}
        // 新敌机产生 随机产生一架普通敌机或精英敌机
        if (enemyAircrafts.size() < enemyMaxNumber) {
            enemyNumber++;
            if(Math.random()< eliteEnemyProbability) {
                enemyFactory = new EliteEnemyFactory();
                enemyAircrafts.add(enemyFactory.createEnemy(
                        (int) (Math.random() * (MainActivity.WINDOW_WIDTH )),//- ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * MainActivity.WINDOW_HEIGHT * 0.2) * 1,
                        (int)(Math.random() * 11 -5),
                        eliteEnemySpeedY*enemyImproveRate,
                        eliteEnemyBlood
                ));
            } else {
                enemyFactory = new MobEnemyFactory();
                enemyAircrafts.add(enemyFactory.createEnemy(
                        (int) (Math.random() * (MainActivity.WINDOW_WIDTH )),//- ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * MainActivity.WINDOW_HEIGHT * 0.2) * 1,
                        0,
                        mobEnemySpeedY,
                        mobEnemyBlood
                ));
            }
            // 超过阈值产生一架boss敌机,以bossBlood=0标志简单模式，不产生boss机
            if(boosBlood!=0 && BossEnemy.bossNum == 0 && counter >= creatBoss_Score){
                counter = 0;                                            // 恢复记录阶段得分
                enemyFactory = new BossEnemyFactory();
                enemyAircrafts.add(enemyFactory.createEnemy(
                        (int) (Math.random() * (MainActivity.WINDOW_WIDTH )),//- ImageManager.BOSS_ENEMY_IMAGE.getWidth()/4)) * 1,
                        (int) (Math.random() * MainActivity.WINDOW_HEIGHT * 0.05 ),//+ ImageManager.BOSS_ENEMY_IMAGE.getHeight()) * 1,
                        1,
                        0,
                        boosBlood
                ));
//                if(chooseDifficulty.isSoundOpen()){
//                    boss_bgm = new MusicThread("src/videos/bgm_boss.wav");
//                    boss_bgm.start();
//                }

            }

        }
    }

    protected boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    protected void bullutPropWorkTime(){
        // 道具持续bulletPropTime后失效
        if(BulletPropStart!=0 && time-BulletPropStart > bulletPropTime){
            heroAircraft.setStrategy(new DirectShoot());
            BulletPropStart = 0;
        }
    }

    // 产生敌机的周期
    protected boolean enemy_timeCountAndNewCycleJudge() {
        enemyCycleTime += timeInterval;
        if (enemyCycleTime >= enemyCycleDuration && enemyCycleTime - timeInterval < enemyCycleTime) {
            // 跨越到新的周期
            enemyCycleTime %= enemyCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    // 飞机射出子弹
    protected void shootAction() {
        // TODO 敌机射击
        for(AbstractEnemyAircraft obj : enemyAircrafts){
            if(obj instanceof EliteEnemy){
                enemyBullets.addAll(((EliteEnemy)obj).executeStrategy());
            }
            if(obj instanceof BossEnemy){
                enemyBullets.addAll(((BossEnemy)obj).executeStrategy());
            }
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.executeStrategy());
    }

    // 子弹移动
    protected void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }
    // 飞机移动
    protected void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }
    // 道具移动
    protected void propMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     * 需要根据模式对应改变
     * 参数：击落Mod敌机增加的分数mobScore，击落Elite敌机增加的分数eliteScore，击落boss敌机增加的分数，子弹道具持续时间：bulletPropTime
     *      不产生道具的概率：noPropProbability
//     */
    protected void crashCheckAction(int mobScore, int eliteScore, int bossScore) {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet enemyBullet : enemyBullets) {
            if (enemyBullet.notValid()) {
                continue;
            }
            if (heroAircraft.notValid()) {
                // 英雄机已被其他子弹击毁不再检测
                // 避免多个子弹重复击毁英雄机的判定
                continue;
            }
            if (heroAircraft.crash(enemyBullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(enemyBullet.getPower()*enemyImproveRate);
                enemyBullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    if(soundOpen){
//                        new MusicThread("src/videos/bullet_hit.wav").start();
                    }
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        score += mobScore;
                        counter += mobScore;
                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof BossEnemy){
                            //如果击落的是精英敌机
                            if(enemyAircraft instanceof EliteEnemy){
                                score += eliteScore-mobScore;
                                counter += eliteScore-mobScore;
                            }
                            // 击落Boss敌机
                            if(enemyAircraft instanceof BossEnemy){
                                score += bossScore-mobScore;
                                counter += bossScore-mobScore;
                                if(soundOpen){
//                                    boss_bgm.setStop(true);
                                }
                            }
                            // 如果被击落的是精英敌机或boss，则随机产生道具或不产生道具
                            double x = Math.random();
                            double step = (1-noPropProbability)/3;
                            if(x >= 0 && x < step){//获得加血道具
                                propFactory = new BloodPropFactory();
                                props.add(propFactory.createProp(
                                        enemyAircraft.getLocationX(),
                                        enemyAircraft.getLocationY(),
                                        0,
                                        5));
                            }
                            else if( x >= step && x < 2*step){//获得爆炸道具
                                propFactory = new BombPropFactory();
                                BombProp bombProp = (BombProp) propFactory.createProp(
                                        enemyAircraft.getLocationX(),
                                        enemyAircraft.getLocationY(),
                                        0,
                                        5);
                                props.add(bombProp);
                            }
                            else if( x >= 2*step && x < 3*step){//获得子弹道具
                                propFactory = new BulletPropFactory();
                                props.add(propFactory.createProp(
                                        enemyAircraft.getLocationX(),
                                        enemyAircraft.getLocationY(),
                                        0,
                                        5));
                            }
                            else{//未获得道具
                                System.out.println("No prop generated!");
                            }

                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
//                    boss_bgm.setStop(true);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for(AbstractProp prop : props){
            if(prop.crash(heroAircraft) || heroAircraft.crash(prop)){
                if(!prop.notValid()){
                    prop.vanish();
                    if(prop instanceof BloodProp){  //获得加血道具，增加30血
                        ((BloodProp)prop).propWork(heroAircraft);
                    }else if(prop instanceof BombProp){
                        // 为炸弹道具增加观察者（子弹和非boss敌机）,并增加得分
                        addEnemyBulletSubscribe((BombProp) prop,mobScore,eliteScore);
                        ((BombProp)prop).propWork(heroAircraft);
                        // 移除所有观察者
                        ((BombProp)prop).unSubscriber();
                    }else{
                        ((BulletProp)prop).propWork(heroAircraft);
                        BulletPropStart = time;
                    }
                }

            }
        }
    }

    // 为炸弹道具增加观察者（子弹和非boss敌机）
    protected void addEnemyBulletSubscribe(BombProp bombProp,int mobScore, int eliteScore){
        // 添加观察者（道具生效需要销毁的敌机）
        for(AbstractEnemyAircraft enemy : enemyAircrafts){
            if(enemy instanceof EliteEnemy){
                bombProp.addSubscriber((EliteEnemy)enemy);
                score += eliteScore;
                counter +=eliteScore;
            }else if(enemy instanceof MobEnemy){
                bombProp.addSubscriber((MobEnemy)enemy);
                score += mobScore;
                counter += mobScore;
            }
        }

        // 添加观察者（道具生效需要销毁的敌机子弹）
        for(BaseBullet baseBullet : enemyBullets){
            bombProp.addSubscriber((EnemyBullet)baseBullet);
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     *    删除无效的道具
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */

    protected void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

}
