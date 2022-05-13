package com.aircraftWar.prop;

import static java.lang.Math.min;

import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.application.AbstractGame;
//import com.aircraftWar.application.MusicThread;
import com.aircraftWar.strategy.DirectShoot;
import com.aircraftWar.strategy.ScatterShoot;

import java.util.concurrent.TimeUnit;

public class BulletProp extends AbstractProp{
    private HeroAircraft heroAircraft;
    /**
     * 火力增幅变量firePower，用于设置使用火力道具后子弹增加的数量
     */
    private int firePower = 2;
    private Thread effect;
    private boolean inEffect = false;

    public BulletProp (int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void propWork(HeroAircraft heroAircraft) {
        this.heroAircraft = heroAircraft;
        heroAircraft.setStrategy(new ScatterShoot());

        if(heroAircraft.getShootNum()+firePower <= heroAircraft.getMaxPower()) {
            heroAircraft.setShootNum(min(heroAircraft.getMaxPower(),heroAircraft.getShootNum() + firePower));
           // Game.bulletAudioFlag = true;
        }
        heroAircraft.setActualPower(heroAircraft.getActualPower() + firePower);
        Runnable r = () -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(heroAircraft.getShootNum() == heroAircraft.getMinScatterShootNum()) {
                heroAircraft.setStrategy(new DirectShoot());
               // Game.bulletAudioFlag = false;
            }
            heroAircraft.setShootNum(min((heroAircraft.getMaxPower()),heroAircraft.getActualPower()-firePower));
            heroAircraft.setActualPower(heroAircraft.getActualPower()-firePower);
            inEffect = false;
        };
        if(inEffect) {
            effect.stop();
        }
        inEffect = true;
        effect = new Thread(r);
        effect.start();
        System.out.println("FireSupply active!\n");
    }
}
