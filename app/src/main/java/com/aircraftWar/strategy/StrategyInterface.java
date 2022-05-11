package com.aircraftWar.strategy;

import com.aircraftWar.bullet.BaseBullet;

import java.util.List;

public interface StrategyInterface {
    List<BaseBullet> shoot(int LocationX, int LocationY, double SpeedY, int direction,int shootNum, int AircraftType);
}
