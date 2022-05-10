package com.aircraftWar.prop;

import com.aircraftWar.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {
    public AbstractProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX,locationY,speedX,speedY);
    }

//    public abstract void propWork(HeroAircraft heroAircraft);
}
