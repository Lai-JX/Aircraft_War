package com.aircraftWar.strategy;

import com.aircraftWar.aircraft.AbstractAircraft;
import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.bullet.BaseBullet;
import com.aircraftWar.bullet.EnemyBullet;
import com.aircraftWar.bullet.HeroBullet;
import com.aircraftWar.strategy.StrategyInterface;



import java.util.LinkedList;
import java.util.List;

/**
 * @author 200110501符悦泽
 * @create 2022/4/15
 */
public class ScatterShoot implements StrategyInterface {

    private int power = 30;       //子弹伤害

    @Override
    public List<BaseBullet> shoot(int LocationX, int LocationY, double SpeedY, int direction,int shootNum, int AircraftType){
        List<BaseBullet> res = new LinkedList<>();
        BaseBullet baseBullet;
        int x = LocationX;
        int y = LocationY + direction*2;
        int speedX = 1;
        SpeedY = direction*5;
        for(int i = 0;i<shootNum;i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if(AircraftType == 1) {
                baseBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX * (i * 2 - shootNum + 1), SpeedY, power);
            }
            else {
                baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX * (i * 2 - shootNum + 1), SpeedY, power);
            }
            res.add(baseBullet);
        }
        return res;
    }

}
