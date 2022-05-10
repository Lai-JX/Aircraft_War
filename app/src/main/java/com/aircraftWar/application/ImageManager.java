package com.aircraftWar.application;

import android.content.Context;
import android.widget.ImageView;

import com.aircraftWar.aircraft.BossEnemy;
import com.aircraftWar.aircraft.EliteEnemy;
import com.aircraftWar.aircraft.HeroAircraft;
import com.aircraftWar.aircraft.MobEnemy;
import com.aircraftWar.bullet.EnemyBullet;
import com.aircraftWar.bullet.HeroBullet;
import com.aircraftWar.prop.BloodProp;
import com.aircraftWar.prop.BombProp;
import com.aircraftWar.prop.BulletProp;
import com.example.aircraftwar.R;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private static Context context;

//    public static ImageView bg1;
//    public static ImageView bg2;
//    public static ImageView bg3;
//    public static ImageView bg4;
//    public static ImageView bg5;
private static final Map<String, ImageView> CLASSNAME_IMAGE_MAP = new HashMap<>();
    public static ImageView hero;
    public static ImageView boss;
    public static ImageView bullet_enemy;
    public static ImageView bullet_hero;
    public static ImageView elite;
    public static ImageView mob;
    public static ImageView prop_blood;
    public static ImageView prop_bomb;
    public static ImageView prop_bullet;

    public ImageManager(Context context){
        this.context = context;

        hero = new ImageView(context);
        boss = new ImageView(context);
        bullet_enemy = new ImageView(context);
        bullet_hero = new ImageView(context);
        elite = new ImageView(context);
        mob = new ImageView(context);
        prop_blood = new ImageView(context);
        prop_bomb = new ImageView(context);
        prop_bullet = new ImageView(context);

//        bg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        bg2.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        bg3.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        bg4.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        bg5.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//        bg1.setImageResource(R.drawable.bg);
//        bg2.setImageResource(R.drawable.bg2);
//        bg3.setImageResource(R.drawable.bg3);
//        bg4.setImageResource(R.drawable.bg4);
//        bg5.setImageResource(R.drawable.bg5);
        hero.setImageResource(R.drawable.hero);
        boss.setImageResource(R.drawable.boss);
        bullet_hero.setImageResource(R.drawable.bullet_hero);
        bullet_enemy.setImageResource(R.drawable.bullet_enemy);
        elite.setImageResource(R.drawable.elite);
        mob.setImageResource(R.drawable.mob);
        prop_bullet.setImageResource(R.drawable.prop_bullet);
        prop_bomb.setImageResource(R.drawable.prop_bomb);
        prop_blood.setImageResource(R.drawable.prop_blood);

        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), hero);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), mob);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), elite);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), boss);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), bullet_hero);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), bullet_enemy);

        CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(), prop_blood);
        CLASSNAME_IMAGE_MAP.put(BombProp.class.getName(), prop_bomb);
        CLASSNAME_IMAGE_MAP.put(BulletProp.class.getName(), prop_bullet);
    }
//
//    // 获取boss图片
//    public static ImageView gainBossImage(){
//        ImageView boss = new ImageView(context);
//        boss.setImageResource(R.drawable.boss);
//        return boss;
//    }
//    // 获取elite图片
//    public static ImageView gainEliteImage(){
//        ImageView elite = new ImageView(context);
//        elite.setImageResource(R.drawable.elite);
//        return elite;
//    }
//    // 获取Mob图片
//    public static ImageView gainMobImage(){
//        ImageView mob = new ImageView(context);
//        mob.setImageResource(R.drawable.mob);
//        return mob;
//    }
//    // 获取prop_Bullet图片
//    public static ImageView gainPropBulletImage(){
//        ImageView prop_Bullet = new ImageView(context);
//        prop_Bullet.setImageResource(R.drawable.prop_bullet);
//        return prop_Bullet;
//    }
//    // 获取prop_blood图片
//    public static ImageView gainPropBloodImage(){
//        ImageView prop_blood = new ImageView(context);
//        prop_blood.setImageResource(R.drawable.prop_blood);
//        return prop_blood;
//    }
//    // 获取prop_bomb图片
//    public static ImageView gainPropBombImage(){
//        ImageView prop_bomb = new ImageView(context);
//        prop_bomb.setImageResource(R.drawable.prop_bomb);
//        return prop_bomb;
//    }
//    // 获取bullet_hero图片
//    public static ImageView gainBulletHeroImage(){
//        ImageView bullet_hero = new ImageView(context);
//        bullet_hero.setImageResource(R.drawable.bullet_hero);
//        return bullet_hero;
//    }
//    // 获取bullet_enemy图片
//    public static ImageView gainBulletEnemyImage(){
//        ImageView bullet_enemy = new ImageView(context);
//        bullet_enemy.setImageResource(R.drawable.bullet_enemy);
//        return bullet_enemy;
//    }
    public static ImageView get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }
    public static ImageView get(Object obj){
        if(obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }
}
