package com.aircraftWar.prop;

import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.application.MusicThread;

public class BloodProp extends AbstractProp{
    public BloodProp (int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }

//    @Override
//    public void propWork(HeroAircraft heroAircraft) {
//        System.out.println("HpSupply active!");
//        heroAircraft.gainHp(30);
////        if(chooseDifficulty.isSoundOpen()){
////            new MusicThread("src/videos/get_supply.wav").start();
////        }
//    }
}
