package com.denny.pickerlib.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import com.denny.pickerlib.support.LruCache;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Cai on 2016/10/11.
 */

public class ImageLoader {

    private static final int TAG_KEY = 0x02ffffff;
    private static ExecutorService THREAD_POLL;
    private static LruCache<String,Bitmap> CACHE_POLL;
    private static Handler MAIN_HANDLER;

    public static void init(){
        THREAD_POLL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()>>1);
        MAIN_HANDLER = new Handler(Looper.getMainLooper());

        int cacheSize = (int) (Runtime.getRuntime().maxMemory()>>3);
        CACHE_POLL = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    public static void destroy(){
        THREAD_POLL.shutdownNow();
        MAIN_HANDLER.removeCallbacksAndMessages(null);
        CACHE_POLL.evictAll();
        THREAD_POLL = null;
        MAIN_HANDLER = null;
        CACHE_POLL = null;
    }


    static void loadImage(final String key, final ImageView imageView, int maxSize, final int defId){
        imageView.setTag(TAG_KEY,key);
        Bitmap bitmap = loadBitmap(key, maxSize, new LoadedCallback() {
            @Override
            public void onLoaded(Bitmap bitmap) {
                if(imageView.getTag(TAG_KEY).equals(key)&&bitmap!=null)
                    imageView.setImageBitmap(bitmap);
                else
                    imageView.setImageResource(defId);
            }
        });
        if(bitmap!=null&&imageView.getTag(TAG_KEY).equals(key)){
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(defId);
        }
    }

    private static Bitmap loadBitmap(final String key, final int maxSize, final LoadedCallback callback) {
        Bitmap image = CACHE_POLL.get(key);
        if(image!=null){
            return image;
        }
        THREAD_POLL.submit(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(key,opt);

                int simpleScale =1;
                while(Math.max(opt.outWidth,opt.outHeight)>maxSize) {
                    simpleScale <<= 1;
                    opt.outWidth>>=1;
                    opt.outHeight>>=1;
                }
                opt.inJustDecodeBounds = false;
                opt.inSampleSize = simpleScale;
                final Bitmap real = BitmapFactory.decodeFile(key,opt);
                save(key,real);
                MAIN_HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoaded(real);
                    }
                });
            }
        });
        return null;
    }

    private static void save(String key, Bitmap bitmap) {
        CACHE_POLL.put(key,bitmap);
    }

    interface LoadedCallback{
        void onLoaded(Bitmap bitmap);
    }

}
