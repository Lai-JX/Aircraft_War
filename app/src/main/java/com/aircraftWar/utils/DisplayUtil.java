package com.aircraftWar.utils;

import android.content.Context;

public class DisplayUtil {
    public static float sp2px(Context context,float spValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale+0.5f);
    }

    public static float px2sp(Context context,float pxValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue/fontScale+0.5f);
    }


}
