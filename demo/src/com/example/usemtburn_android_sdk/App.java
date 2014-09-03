package com.example.usemtburn_android_sdk;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Enable to investigate remote crash logs to make our SDK more stable
        DeployGate.install(this);
    }
}
