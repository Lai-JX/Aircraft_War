package com.aircraftWar.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.aircraftWar.GameDataDao.GameData;
import com.aircraftWar.aircraft.BossEnemy;
import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.utils.PostUtil;
import com.example.aircraftwar.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DifficultModeGame extends AbstractGame{

    private int bossBlood = 200;
    private int creatBossScore = 200;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        score = 0;
        // 敌机产生周期
        enemyCycleDuration = 400;
        // 不产生道具的概率
        noPropProbability = 0.3;
        // 子弹道具持续时间
        bulletPropTime = 4000;
        // 产生精英敌机的概率
        eliteEnemyProbability = 0.4;
        setContentView(R.layout.activity_game);
        mSurfaceView = new GameSurfaceView(this,"difficult");
        System.out.println("生成背景成功");
        heroAircraft = HeroAircraft.getInstance(10000,gameOverFlag);
//        heroAircraft.setHp(10000);

        setContentView(mSurfaceView);
        super.onCreate(savedInstanceState);
        super.intent = new Intent(this,MusicService.class);
//        ImageManager imageManager = new ImageManager(this);
//        Log.i("h",String.valueOf(ImageManager.hero.getHeight()));
        System.out.println("获取intent");
        // 获取来自主页面的信息
        Intent gameIntent = getIntent();
        soundOpen = gameIntent.getBooleanExtra("soundOpen",true);
        isBattle = gameIntent.getExtras().getBoolean("isBattle");
        battleModeId = gameIntent.getExtras().getString("battleId");
        System.out.println("isBattle="+isBattle);

        System.out.println("soundOpen="+soundOpen);
        System.out.println("获取intent成功，游戏开始");
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
            if(gameOverFlag){
                intent.putExtra("music","bgm");
                gameOverFlag = false;
            }
            startService(intent);
        }

        System.out.println("困难模式：");
        System.out.println("\t产生boss敌机的初始阈值:200\t最大敌机数:10\tboss敌机初始血量:200" +
                "\n\t英雄机子弹伤害:30\t敌机子弹初始伤害:10" +
                "\n\t精英敌机初始速度:14\t" + "精英敌机血量:60\t普通敌机初始速度:11\t普通敌机血量:30" +
                "\n\t击落boss敌机得分:45\t击落精英敌机得分:10\t击落普通敌机得分:5" +
                "\n\t除boss机外提升难度的时间间隔:4s" +
                "\n\t精英敌机概率初始值为:0.4\t敌机产生周期初始值为:400ms\t不产生道具的概率初始值为:0.3\t子弹道具持续时间初始值为10s");

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            if(isBattle){
                new Thread(()->{
                    String data="";
                    data = "&username="+ LoginActivity.userName+
                            "&id="+battleModeId+
                            "&action=score" +
                            "&score="+AbstractGame.score +
                            "&life="+heroAircraft.getHp();
                    System.out.println("发送同步得分请求");
                    String result = PostUtil.Post(BATTLE_MODE_URL,data);
                    String res[] = result.split("&");
                    if(res.length==2){
                        competitor_life = res[1];
                        competitor_score = res[0];
                    }

                }).start();

            }
            // 每隔4秒提升难度
            if(time % 3990 == 0){
                eliteEnemyProbability += 0.02;
                System.out.print("提升难度！精英敌机概率:"+Double.parseDouble(String.format("%.2f",eliteEnemyProbability)));
                enemyCycleDuration -= 20;
                System.out.print("!\t敌机产生周期:"+enemyCycleDuration+"ms");
                enemyImproveRate += 0.02;
                System.out.print("!\t新增敌机属性提升倍率:"+Double.parseDouble(String.format("%.2f",enemyImproveRate)));
                noPropProbability += 0.02;
                System.out.print("!\t击落精英敌机或boss敌机不产生道具的概率:"+Double.parseDouble(String.format("%.2f",noPropProbability)));
                bulletPropTime -= 150;
                System.out.println("!\t子弹道具持续时间:"+bulletPropTime+"ms");
            }

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {

                // 飞机射出子弹
                shootAction();
            }
            // 每隔enemyCycleDuration产生敌机
            if(enemy_timeCountAndNewCycleJudge()){
                if(BossEnemy.bossNum == 0 && counter>creatBossScore){
                    bossBlood += 50;
                    creatBossScore -= 5;
                    System.out.println("提升难度！boss敌机血量:"+bossBlood+"\t产生boss机的阈值:"+creatBossScore);
                }
                // 产生敌机
                // 参数:精英敌机出现的概论eliteEnemyProbability，产生boss机的阈值
                creatEnemyAircraft(creatBossScore,10,bossBlood,
                        60,14,30,11);
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propMoveAction();

            mSurfaceView.getAllFlyingObject(heroAircraft,enemyAircrafts,heroBullets,enemyBullets,props);
            // 撞击检测
            crashCheckAction(5,10,45);

            // 后处理
            postProcessAction();

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
                GameData gameData = GameData.gameData.getUserData();
                gameData.setDate(new Date());
                gameData.setScore(score);
                Intent intent = new Intent(this, EnterNameActivity.class);
                System.out.println("xxx");
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