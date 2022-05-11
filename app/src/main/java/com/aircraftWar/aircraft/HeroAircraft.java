package com.aircraftWar.aircraft;

import com.aircraftWar.application.ImageManager;
import com.aircraftWar.application.MainActivity;
import com.aircraftWar.basic.AbstractFlyingObject;
import com.aircraftWar.bullet.BaseBullet;
import com.aircraftWar.strategy.DirectShoot;
import com.aircraftWar.strategy.StrategyInterface;

import java.util.List;

public class HeroAircraft extends AbstractAircraft {
    private volatile static HeroAircraft heroAircraft;  // 单例模式下
    private StrategyInterface strategy;
    private int shootNum = 1;     //子弹一次发射数量
    private static int power = 100;       //子弹伤害
    private int direction = -1;  //子弹射击方向 (向上发射：1，向下发射：-1)
    private int maxPower = 5;   //最大子弹数
    private int minScatterShootNum = 3;//英雄机散射时的子弹的最少数量
    private int actualPower = 1;//英雄机吃到火力道具后，实际一次能够发射的子弹数量（不考虑上限）

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, StrategyInterface strategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = strategy;
    }

    public static HeroAircraft getInstance(int blood){
        if(heroAircraft == null){
            synchronized (HeroAircraft.class){
                if(heroAircraft == null){
                    heroAircraft = new HeroAircraft(
                            MainActivity.WINDOW_WIDTH / 2,
                            MainActivity.WINDOW_HEIGHT -219,//-ImageManager.hero.getHeight() ,//550
                            0, 0, blood,
                            new DirectShoot());
//                            new DirectShoot());
                }
            }
        }
        return heroAircraft;
    }
    public void setStrategy(StrategyInterface strategy){
        this.strategy = strategy;
    }

    public int getActualPower() {
        return actualPower;
    }

    public void setActualPower(int actualPower) {
        this.actualPower = actualPower;
    }

    public int getShootNum() {
        return shootNum;
    }

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public int getMinScatterShootNum() {
        return minScatterShootNum;
    }

    public int getMaxPower() {
        return maxPower;
    }


    public List<BaseBullet> executeStrategy(){
        return strategy.shoot(this.locationX,this.locationY,this.speedY,-1,shootNum,1);
    }
    public void gainHp(int hp){
        this.hp += hp;
    }

}
