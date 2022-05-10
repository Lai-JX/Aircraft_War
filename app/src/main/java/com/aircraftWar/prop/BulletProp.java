package com.aircraftWar.prop;

import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.application.AbstractGame;
import com.aircraftWar.application.MusicThread;
import com.aircraftWar.strategy.ScatteredShoot;

public class BulletProp extends AbstractProp{
    public BulletProp (int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void propWork(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active!");
        heroAircraft.setStrategy(new ScatteredShoot());
        if(AbstractGame.soundOpen) {
//            new MusicThread("src/videos/get_supply.wav").start();
        }
    }
}
