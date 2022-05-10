package com.aircraftWar.bullet;

import com.aircraftWar.application.MainActivity;
import com.aircraftWar.basic.AbstractFlyingObject;

public class BaseBullet extends AbstractFlyingObject {
    private double power = 10;

    public BaseBullet(int locationX, int locationY, int speedX, double speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= MainActivity.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= MainActivity.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getPower() {
        return power;
    }
}
