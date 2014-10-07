package com.example.usemtburn_android_sdk;

import com.mtburn.android.sdk.wall.WallAdActivity;

public class CustomWallAdActivity extends WallAdActivity {
    @Override
    protected void didPresentWallAd() {
        super.didPresentWallAd();
        App.logd("didPresentWallAd");
    }

    @Override
    protected void willDismissWallAd() {
        super.willDismissWallAd();
        App.logd("willDismissWallAd");
    }

    @Override
    protected void didDismissWallAd() {
        super.didDismissWallAd();
        App.logd("didDismissWallAd");
    }
}
