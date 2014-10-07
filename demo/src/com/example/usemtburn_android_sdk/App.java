package com.example.usemtburn_android_sdk;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.mtburn.android.sdk.AppDavis;

public class App extends Application {
    
    public static final boolean MUTE_LOG_OUTPUT = true;
    public static final boolean DEVELOPER_MODE = false;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Control AppDavis log output
        AppDavis.setMuteLogOutput(MUTE_LOG_OUTPUT);
        
        if (DEVELOPER_MODE) {
            
            // Enable StrictMode
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                setStrictMode();
            }
        }
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build());
        
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .penaltyDeath()
        .build());
    }
    
    public static final void logd(String msg) {
        if (!AppDavis.muteLogOutput()) {
            Log.d("DemoApp", msg);
        }
    }
}
