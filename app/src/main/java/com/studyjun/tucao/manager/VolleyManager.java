package com.studyjun.tucao.manager;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by hn on 2015/1/31.
 *
 * @TODO
 */
public class VolleyManager {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static  VolleyManager mVolleyManager;
    private ImageLoader.ImageCache imageCache;
    private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private static int DISK_IMAGECACHE_QUALITY = 30;  //PNG is lossless so quality is ignored but must be provided
    private StringCache stringCache;

    private VolleyManager(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
        imageCache = new DiskLruImageCache(context,"thumb",DISK_IMAGECACHE_SIZE,DISK_IMAGECACHE_COMPRESS_FORMAT,DISK_IMAGECACHE_QUALITY);
        stringCache = new DiskLruStringCache(context,"data",1000*1000*3);
        mImageLoader = new ImageLoader(mRequestQueue,imageCache);

       // UI.toastLongTime(context, context.getExternalCacheDir().getAbsolutePath());
    }

    public static StringCache getStringCache(){
        return mVolleyManager.stringCache;
    }

    /**
     *
     * @param context
     * @return
     */
    public  static VolleyManager getInstance(Context context){

            if (mVolleyManager==null){ //双重检查
                synchronized (VolleyManager.class){
                    if (mVolleyManager==null){
                        mVolleyManager = new VolleyManager(context);
                    }
                }
            }
        return  mVolleyManager;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
