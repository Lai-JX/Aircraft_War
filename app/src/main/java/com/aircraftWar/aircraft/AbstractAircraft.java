package com.aircraftWar.aircraft;

import com.aircraftWar.basic.AbstractFlyingObject;

public class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected double maxHp;
    protected double hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, double speedY, double hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(double decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public double getHp() {
        return hp;
    }
}
