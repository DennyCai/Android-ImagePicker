package com.denny.pickerlib.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.denny.pickerlib.support.LruCache;
import com.denny.pickerlib.utils.BitmapUtils;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cai on 2016/10/11.
 */

public class ImageLoader {

    private static final int TAG_KEY = 0x02ffffff;
    private static ExecutorService THREAD_POLL;
    private static LruCache<String,Bitmap> CACHE_POLL;
    private static Handler MAIN_HANDLER;

    public static void init(){
        THREAD_POLL = newThreadPool();
        MAIN_HANDLER = new Handler(Looper.getMainLooper());

        int cacheSize = (int) (Runtime.getRuntime().maxMemory()>>3);
        CACHE_POLL = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    private static ExecutorService newThreadPool(){
        int threadCount = Runtime.getRuntime().availableProcessors()>>1;
        return new ThreadPoolExecutor(threadCount,threadCount,0, TimeUnit.MINUTES,new StackBlockingDeque());
    }

    public static void reset(){
        THREAD_POLL.shutdownNow();
        THREAD_POLL = newThreadPool();
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
                setBitmap(imageView,key,bitmap,defId);
            }
        });
        setBitmap(imageView,key,bitmap,defId);
    }

    private static void setBitmap(ImageView imageView, String key, Bitmap bitmap, @DrawableRes int defId){
        if(bitmap!=null){
            if(key.equals(imageView.getTag(TAG_KEY))) {
                imageView.setImageBitmap(bitmap);
            }
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
                final Bitmap real = BitmapUtils.loadBitmap(key,maxSize);
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

    /**
     * LIFO模式
     */
    public static class StackBlockingDeque extends LinkedBlockingDeque<Runnable> {

        @Override
        public boolean offer(Runnable o) {
            return super.offerFirst(o);
        }

        @Override
        public Runnable remove() {
            return super.removeFirst();
        }
    }
}
