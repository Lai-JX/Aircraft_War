package com.aircraftWar.strategy;

import com.aircraftWar.bullet.BaseBullet;
import com.aircraftWar.bullet.EnemyBullet;
import com.aircraftWar.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DirectShoot implements StrategyInterface{
    private int shootNum = 1;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
//    private int direction = 1;  //子弹射击方向 (向上发射：-1，向下发射：1)
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    @Override
    public List<BaseBullet> shoot(int LocationX, int LocationY, double SpeedY, int direction,int shootNum, int AircraftType) {
        List<BaseBullet> res = new LinkedList<>();
        int x = LocationX;
        int y = LocationY + direction*2;
        int speedX = 0;
        double speedY = SpeedY + direction*5;
        BaseBullet baseBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散

            if(AircraftType == 1){ //飞机类型是英雄机
                baseBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }else {                 //飞机类型是敌机
                baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }
            res.add(baseBullet);
        }
        return res;
    }

}
