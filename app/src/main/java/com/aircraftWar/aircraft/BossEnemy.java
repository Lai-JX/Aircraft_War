package com.aircraftWar.aircraft;

import com.aircraftWar.bullet.BaseBullet;
import com.aircraftWar.strategy.StrategyInterface;

import java.util.List;

public class BossEnemy extends AbstractEnemyAircraft{
    private StrategyInterface strategy;
    public static int bossNum = 0;        //boss敌机数量

    public BossEnemy(int locationX, int locationY, int speedX, double speedY, int hp, StrategyInterface strategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = strategy;
        bossNum++;
    }

    public void setStrategy(StrategyInterface strategy){
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy(){
        return strategy.shoot(this.locationX,this.locationY,this.speedY,1, 2);
    }


    @Override
    public void decreaseHp(double decrease){
        hp -= decrease;
        if(hp <= 0) {
            hp = 0;
            vanish();
            bossNum--;
        }
    }
}
