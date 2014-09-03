package com.example.usemtburn_android_instream;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;

public class DrawableCache {
    private static HashMap<String, SoftReference<Drawable>> cache = new HashMap<String, SoftReference<Drawable>>();
    
    public static Drawable getImage(String key) {
        SoftReference<Drawable> ref = cache.get(key);
        if (ref != null) {
            return ref.get();
        }
        return null;
    }

    public static void setImage(String key, Drawable image) {
        cache.put(key, new SoftReference<Drawable>(image));
    }
}
