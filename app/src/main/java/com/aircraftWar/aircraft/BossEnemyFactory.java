package com.aircraftWar.aircraft;

import com.aircraftWar.strategy.ScatteredShoot;

public class BossEnemyFactory implements EnemyFactory{
    @Override
    public AbstractEnemyAircraft createEnemy(int locationX, int locationY, int speedX, double speedY, int hp){
        return new BossEnemy(locationX,locationY,speedX,speedY,hp,new ScatteredShoot());
    }
}
