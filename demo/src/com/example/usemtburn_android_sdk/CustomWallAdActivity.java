package com.example.usemtburn_android_sdk;

import android.util.Log;

import com.mtburn.android.sdk.wall.WallAdActivity;

public class CustomWallAdActivity extends WallAdActivity {
    @Override
    protected void didPresentWallAd() {
        super.didPresentWallAd();
        Log.d("WallAdActivity", "didPresentWallAd");
    }

    @Override
    protected void willDismissWallAd() {
        super.willDismissWallAd();
        Log.d("WallAdActivity", "willDismissWallAd");
    }

    @Override
    protected void didDismissWallAd() {
        super.didDismissWallAd();
        Log.d("WallAdActivity", "didDismissWallAd");
    }
}
