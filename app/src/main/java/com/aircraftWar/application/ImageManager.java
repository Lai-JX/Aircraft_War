package com.aircraftWar.application;

import android.content.Context;
import android.widget.ImageView;

import com.example.aircraftwar.R;

public class ImageManager {

    private static Context context;

    public static ImageView bg1;
    public static ImageView bg2;
    public static ImageView bg3;
    public static ImageView bg4;
    public static ImageView bg5;
    public static ImageView hero;
//    public static ImageView boss;
//    public static ImageView bullet_enemy;
//    public static ImageView bullet_hero;
//    public static ImageView elite;
//    public static ImageView mob;
//    public static ImageView prop_blood;
//    public static ImageView prop_bomb;
//    public static ImageView prop_bullet;

    public ImageManager(Context context){
        this.context = context;

        bg1 = new ImageView(context);
        bg2 = new ImageView(context);
        bg3 = new ImageView(context);
        bg4 = new ImageView(context);
        bg5 = new ImageView(context);
        hero = new ImageView(context);
//        boss = new ImageView(context);
//        bullet_enemy = new ImageView(context);
//        bullet_hero = new ImageView(context);
//        elite = new ImageView(context);
//        mob = new ImageView(context);
//        prop_blood = new ImageView(context);
//        prop_bomb = new ImageView(context);
//        prop_bullet = new ImageView(context);

        bg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bg2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bg3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bg4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bg5.setScaleType(ImageView.ScaleType.CENTER_CROP);

        bg1.setImageResource(R.drawable.bg);
        bg2.setImageResource(R.drawable.bg2);
        bg3.setImageResource(R.drawable.bg3);
        bg4.setImageResource(R.drawable.bg4);
        bg5.setImageResource(R.drawable.bg5);
        hero.setImageResource(R.drawable.hero);
//        boss.setImageResource(R.drawable.boss);
//        bullet_hero.setImageResource(R.drawable.bullet_hero);
//        bullet_enemy.setImageResource(R.drawable.bullet_enemy);
//        elite.setImageResource(R.drawable.elite);
//        mob.setImageResource(R.drawable.mob);
//        prop_bullet.setImageResource(R.drawable.prop_bullet);
//        prop_bomb.setImageResource(R.drawable.prop_bomb);
//        prop_blood.setImageResource(R.drawable.prop_blood);
    }

    // 获取boss图片
    public static ImageView gainBossImage(){
        ImageView boss = new ImageView(context);
        boss.setImageResource(R.drawable.boss);
        return boss;
    }
    // 获取elite图片
    public static ImageView gainEliteImage(){
        ImageView elite = new ImageView(context);
        elite.setImageResource(R.drawable.elite);
        return elite;
    }
    // 获取Mob图片
    public static ImageView gainMobImage(){
        ImageView mob = new ImageView(context);
        mob.setImageResource(R.drawable.mob);
        return mob;
    }
    // 获取prop_Bullet图片
    public static ImageView gainPropBulletImage(){
        ImageView prop_Bullet = new ImageView(context);
        prop_Bullet.setImageResource(R.drawable.prop_bullet);
        return prop_Bullet;
    }
    // 获取prop_blood图片
    public static ImageView gainPropBloodImage(){
        ImageView prop_blood = new ImageView(context);
        prop_blood.setImageResource(R.drawable.prop_blood);
        return prop_blood;
    }
    // 获取prop_bomb图片
    public static ImageView gainPropBombImage(){
        ImageView prop_bomb = new ImageView(context);
        prop_bomb.setImageResource(R.drawable.prop_bomb);
        return prop_bomb;
    }
    // 获取bullet_hero图片
    public static ImageView gainBulletHeroImage(){
        ImageView bullet_hero = new ImageView(context);
        bullet_hero.setImageResource(R.drawable.bullet_hero);
        return bullet_hero;
    }
    // 获取bullet_enemy图片
    public static ImageView gainBulletEnemyImage(){
        ImageView bullet_enemy = new ImageView(context);
        bullet_enemy.setImageResource(R.drawable.bullet_enemy);
        return bullet_enemy;
    }

}
