package com.aircraftWar.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.aircraftWar.aircraft.AbstractEnemyAircraft;
import com.aircraftWar.aircraft.BossEnemy;
import com.aircraftWar.aircraft.EliteEnemy;
import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.aircraft.MobEnemy;
import com.aircraftWar.basic.AbstractFlyingObject;
import com.aircraftWar.bullet.BaseBullet;
import com.aircraftWar.prop.AbstractProp;
import com.aircraftWar.prop.BloodProp;
import com.aircraftWar.prop.BombProp;
import com.aircraftWar.prop.BulletProp;
import com.example.aircraftwar.R;

import java.util.List;

public class GameSurfaceView  extends SurfaceView implements
        SurfaceHolder.Callback,Runnable{
    private Context context;
    private boolean mbLoop; // 是否绘制画面
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private Paint mPaint;   // 笔
    private String mode;    // 游戏模式

//    /**
//     * 所有需要绘制的物
//    */
    private HeroAircraft heroAircraft;
    private List<AbstractEnemyAircraft> enemyAircrafts;
    private List<BaseBullet> heroBullets;
    private List<BaseBullet> enemyBullets;
    private List<AbstractProp> props;



    public GameSurfaceView(Context context,String mode) {

        super(context);
        this.context = context;
        mbLoop = true;
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);// 回调接口
        this.setFocusable(true);    // 控制键盘是否可以获得这个按钮的焦点
        this.mode = mode;

    }

    @Override
    public void run() {
        while(mbLoop){
            Log.i("draw","draw");
            draw();
        }

    }

    public void getAllFlyingObject(HeroAircraft heroAircraft,
                                   List<AbstractEnemyAircraft> enemyAircrafts,
                                   List<BaseBullet> heroBullets,
                                   List<BaseBullet> enemyBullets,
                                   List<AbstractProp> props){
        this.heroAircraft = heroAircraft;
        this.enemyAircrafts = enemyAircrafts;
        this.heroBullets = heroBullets;
        this.enemyBullets = enemyBullets;
        this.props = props;
    }

    public void draw(){
        canvas = mSurfaceHolder.lockCanvas();
        if(mode.equals("easy")){
            drawBackGround(R.drawable.bg);   // 绘制背景
        }
        // 绘制敌机子弹
        for(int i=0; i< enemyBullets.size(); i++){
            drawImage(R.drawable.bullet_enemy,enemyBullets.get(i));
        }

//        // 绘制英雄机子弹
//        for(AbstractFlyingObject obj : heroBullets){
//            drawImage(R.drawable.bullet_hero,obj);
//        }
        for(int i=0;i<heroBullets.size();i++){
            drawImage(R.drawable.bullet_hero,heroBullets.get(i));
        }
        // 绘制敌机
        for(int i = 0; i<enemyAircrafts.size(); i++){
            AbstractEnemyAircraft obj = enemyAircrafts.get(i);
            if(obj instanceof MobEnemy){
                drawImage(R.drawable.mob,obj);
            }else if(obj instanceof EliteEnemy){
                drawImage(R.drawable.elite,obj);
            }else if(obj instanceof BossEnemy){
                drawImage(R.drawable.boss,obj);
            }
        }
        // 绘制道具
        for(int i=0; i<props.size();i++){
            AbstractProp obj = props.get(i);
            if(obj instanceof BloodProp){
                drawImage(R.drawable.prop_blood,obj);
            }else if(obj instanceof BombProp){
                drawImage(R.drawable.prop_bomb,obj);
            }else if(obj instanceof BulletProp){
                drawImage(R.drawable.prop_bullet,obj);
            }
        }
        // 绘制英雄机
        drawImage(R.drawable.hero,heroAircraft);
//				解锁
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    // 绘制背景
    public void drawBackGround(int resId){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bitm = BitmapFactory.decodeResource(getResources(), resId, opts);
//				解析图片的头文件
        opts.inJustDecodeBounds = true;
//				得到图片高、宽
        float imageH = opts.outHeight;
        float imageW = opts.outWidth;
        System.out.println("图片的高" + imageH);
        System.out.println("屏幕的高" + MainActivity.WINDOW_HEIGHT);
        PaintFlagsDrawFilter pfd= new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(pfd);//解决缩放后图片字体模糊的问题
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, mPaint);
    }

    // 绘制图片
    public void drawImage(int resId, AbstractFlyingObject flyingObject) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bitm = BitmapFactory.decodeResource(getResources(), resId, opts);
//				解析图片的头文件
        opts.inJustDecodeBounds = true;
//				得到图片高、宽
        float imageH = opts.outHeight;
        float imageW = opts.outWidth;
        System.out.println("图片的高" + imageH+" px"+DisplayUtil.sp2px(context,imageH));
//        System.out.println("屏幕的高" + MainActivity.WINDOW_HEIGHT);
//        System.out.println("屏幕的宽sp"+DisplayUtil.px2sp(context,MainActivity.WINDOW_WIDTH));
//        System.out.println("屏幕的高sp"+DisplayUtil.px2sp(context,MainActivity.WINDOW_HEIGHT));
//        System.out.println("飞机纵坐标sp"+DisplayUtil.px2sp(context,flyingObject.getLocationY())+" px"+(flyingObject.getLocationY()-DisplayUtil.sp2px(context,imageH)));
//        System.out.println("飞机横坐标sp"+DisplayUtil.px2sp(context,flyingObject.getLocationX())+" px"+flyingObject.getLocationX());


        PaintFlagsDrawFilter pfd= new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(pfd);//解决缩放后图片字体模糊的问题
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
//				设置缩放比
        Matrix matrix = new Matrix();
        // 设置位置，以px为单位
        matrix.setTranslate(flyingObject.getLocationX()-DisplayUtil.sp2px(context,imageW)/2,flyingObject.getLocationY()-(MainActivity.WINDOW_HEIGHT-canvas.getHeight()));//-DisplayUtil.sp2px(context,imageH)
//        matrix.setScale(MainActivity.WINDOW_WIDTH / imageW, MainActivity.WINDOW_HEIGHT / imageH);
        canvas.drawBitmap(bitmap, matrix, mPaint);


    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(this).start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
//        screenWidth = width;
//        screenHeight = height;
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mbLoop = false;
    }


}
