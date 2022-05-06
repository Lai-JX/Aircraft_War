package com.aircraftWar.application;

import android.content.Context;
import android.widget.ImageView;

import com.example.aircraftwar.R;

public class ImageManager {
    public static ImageView bg1;
    public static ImageView bg2;
    public static ImageView hero;

    public ImageManager(Context context){
        bg1 = new ImageView(context);
        bg2 = new ImageView(context);
        bg2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        hero = new ImageView(context);
        bg1.setImageResource(R.drawable.bg);
        bg2.setImageResource(R.drawable.bg2);
        hero.setImageResource(R.drawable.hero);
    }

}
