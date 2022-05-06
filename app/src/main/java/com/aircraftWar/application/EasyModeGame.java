package com.aircraftWar.application;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.aircraftwar.R;

public class EasyModeGame extends AbstractGame{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ImageManager imageManager = new ImageManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_game);
        init();
    }

    // 添加背景和hero机
    public void init(){
        // 设置图片位置
        params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = backGroundTop | Gravity.NO_GRAVITY;
        addContentView(ImageManager.bg2,params);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        addContentView(ImageManager.hero,params);
    }


}
