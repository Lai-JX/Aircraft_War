package com.aircraftWar.application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.aircraftwar.R;


public class MusicService extends Service{
    private static  final String TAG = "MusicService";

    public MusicService() {
    }
    //创建播放器对象
    private MediaPlayer player_bgm;
    private MediaPlayer player_bgm_boss;
    private MediaPlayer player_bomb_explosion;
    private MediaPlayer player_bullet_hit;
    private MediaPlayer player_game_over;
    private MediaPlayer player_get_supply;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "==== MusicService onStartCommand ===");
        String music = intent.getStringExtra("music");
        if("bullet_hit".equals(music)){
            player_bullet_hit = MediaPlayer.create(this,R.raw.bullet_hit);
            player_bullet_hit.start();
        }else if("bgm_boss".equals(music)){
            player_bgm_boss = MediaPlayer.create(this,R.raw.bgm_boss);
            player_bgm_boss.start();
            player_bgm_boss.setLooping(true);
        }else if("bomb_explosion".equals(music)){
            player_bomb_explosion = MediaPlayer.create(this,R.raw.bomb_explosion);
            player_bomb_explosion.start();
        }else if("game_over".equals(music)){
            player_game_over = MediaPlayer.create(this,R.raw.game_over);
            player_game_over.start();
            stopMusic(player_bgm);
//            stopAllMusic();
        }else if("get_supply".equals(music)){
            player_get_supply = MediaPlayer.create(this,R.raw.get_supply);
            player_get_supply.start();
        }else if("bgm_boss_close".equals(music)){
            stopMusic(player_bgm_boss);
        }else if("bgm".equals(music)){
            player_bgm = MediaPlayer.create(this,R.raw.bgm);
            player_bgm.start();
            player_bgm.setLooping(true);
        }
        return super.onStartCommand(intent, flags, startId);
//        return Service.START_NOT_STICKY;
    }

    /**
     * 停止播放
     */
    public void stopAllMusic() {
        player_bgm = stopMusic(player_bgm);
        player_bgm_boss = stopMusic(player_bgm_boss);
        player_bomb_explosion = stopMusic(player_bomb_explosion);
        player_bullet_hit = stopMusic(player_bullet_hit);
        player_game_over = stopMusic(player_game_over);
        player_get_supply = stopMusic(player_get_supply);
    }
    public MediaPlayer stopMusic(MediaPlayer player) {
        if (player != null) {
            player.stop();
            player.reset();//重置
            player.release();//释放
//            player = null;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "==== MusicService onCreate ===");

        player_bgm = MediaPlayer.create(this,R.raw.bgm);
        player_bgm.setLooping(true);
        player_bgm.start();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        return new Binder();        // 返回Binder实例
        throw new UnsupportedOperationException("Not yet implemented");
    }
//
//    // 创建一个类继承Binder，来对data数据进行更新
//    public class Binder extends android.os.Binder{
//        public void setData(String data){
//            MusicService.this.data = data;
//        }
//    }

    @Override
    public void onDestroy() {
        stopAllMusic();
        super.onDestroy();

    }



}