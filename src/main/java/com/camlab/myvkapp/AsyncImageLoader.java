package com.camlab.myvkapp;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 15.10.2014.
 */
public class AsyncImageLoader {
    private HashMap <String, SoftReference<Drawable>> imageCache;
    private final HashMap <String, SoftReference<Drawable>> drawableMap;

    public AsyncImageLoader() {
        drawableMap = new HashMap<String, SoftReference<Drawable>>();
        imageCache = new HashMap<String, SoftReference<Drawable>>();
    }

    public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
        if (drawableMap.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }

    public static Drawable loadImageFromUrl(String url) {
        InputStream inputStream;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Drawable.createFromStream(inputStream, "src");
    }

    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable, String imageUrl);
    }
}
