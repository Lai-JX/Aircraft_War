package com.aircraftWar.aircraft;

public class MobEnemyFactory implements EnemyFactory{
    @Override
    public AbstractEnemyAircraft createEnemy(int locationX, int locationY, int speedX, double speedY, int hp){
        return new MobEnemy(locationX,locationY,speedX,speedY,hp);
    }
}
