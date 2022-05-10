package com.aircraftWar.aircraft;

import com.aircraftWar.Observer.Subscriber;

public class MobEnemy extends AbstractEnemyAircraft implements Subscriber {

    public MobEnemy(int locationX, int locationY, int speedX, double speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void bombWork(){
        vanish();
    }
}
