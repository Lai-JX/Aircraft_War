package com.aircraftWar.aircraft;

import com.aircraftWar.Observer.Subscriber;
import com.aircraftWar.bullet.BaseBullet;
import com.aircraftWar.strategy.StrategyInterface;

import java.util.List;

public class EliteEnemy extends AbstractEnemyAircraft implements Subscriber {


    //    private int direction = 1;  //子弹射击方向 (向上发射：-1，向下发射：1)

    private StrategyInterface strategy;

    public EliteEnemy(int locationX, int locationY, int speedX, double speedY, int hp, StrategyInterface shootMode) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = shootMode;
    }

    @Override
    public void bombWork(){
        vanish();
    }

    public void setStrategy(StrategyInterface strategy){
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy(){
        return strategy.shoot(this.locationX,this.locationY,this.speedY,1, 2);
    }
}
