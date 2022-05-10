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

    public List<BaseBullet> executeStrategy(){
        return strategy.shoot(this.locationX,this.locationY,this.speedY,-1,1);
    }
    public void gainHp(int hp){
        this.hp += hp;
    }

}
