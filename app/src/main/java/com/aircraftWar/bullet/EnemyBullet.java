package com.aircraftWar.bullet;

import com.aircraftWar.Observer.Subscriber;

public class EnemyBullet extends BaseBullet implements Subscriber {

    public EnemyBullet(int locationX, int locationY, int speedX, double speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }
    @Override
    public void bombWork(){
        vanish();
    }
}
