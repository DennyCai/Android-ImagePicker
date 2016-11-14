package com.denny.pickerlib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Cai on 2016/11/14.
 */

public class BitmapUtils {
    public static Bitmap loadBitmap(String filepath,int maxSize){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath,opt);

        int simpleScale =1;
        while(Math.max(opt.outWidth,opt.outHeight)>maxSize) {
            simpleScale <<= 1;
            opt.outWidth>>=1;
            opt.outHeight>>=1;
        }
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = simpleScale;
        return BitmapFactory.decodeFile(filepath,opt);
    }
}
