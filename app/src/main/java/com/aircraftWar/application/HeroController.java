package com.aircraftWar.application;


import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.aircraftWar.aircraft.HeroAircraft;

public class HeroController extends SurfaceView implements
        SurfaceHolder.Callback,Runnable  {
    private boolean mbLoop = false; //控制英雄线程的标志位
    private double screenWidth = MainActivity.WINDOW_WIDTH;
    private double screenHeight = MainActivity.WINDOW_HEIGHT;
    private SurfaceHolder mSurfaceHolder;
    // 英雄机
    private HeroAircraft heroAircraft;
    // 英雄机横坐标
    public double x = MainActivity.WINDOW_WIDTH/2;
    // 英雄机纵坐标
    public double y = 0;

    public HeroController(Context context, HeroAircraft heroAircraft){
        super(context);
        mbLoop = true;
        this.heroAircraft = heroAircraft;
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);// 回调接口
        this.setFocusable(true);    // 控制键盘是否可以获得这个按钮的焦点
    }

//    public void setY(double y) {
//        this.y = y;
//    }
//
//    public void setX(double x) {
//        this.x = x;
//    }

    @Override
    public void run() {
        //设置一个循环来绘制，通过标志位来控制开启绘制还是停止
        while (mbLoop){
            synchronized (mSurfaceHolder){
//                setVisibility(INVISIBLE);
//                Log.i("x2",String.valueOf(heroAircraft.getLocationX()));
//                Log.i("y2",String.valueOf(heroAircraft.getLocationY()));
                heroAircraft.setLocation(x,y);
//                Log.i("x3",String.valueOf(heroAircraft.getLocationX()));
//                Log.i("y3",String.valueOf(heroAircraft.getLocationY()));
            }
            try {
                Thread.sleep(80);
            }catch (Exception e){}
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(this).start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mbLoop = false;
    }
}
