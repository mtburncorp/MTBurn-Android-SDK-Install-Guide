package com.example.usemtburn_android_sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.appimp.ADVSAppImpLoadListener;
import com.mtburn.android.sdk.appimp.ADVSAppImpLoader;
import com.mtburn.android.sdk.wall.ADVSWallAdLoadListener;
import com.mtburn.android.sdk.wall.ADVSWallAdLoader;
import com.mtburn.android.sdk.wall.WallAdActivity;

@SuppressWarnings("deprecation")
public class WallAdSampleActivity extends Activity implements ADVSWallAdLoadListener, ADVSAppImpLoadListener {

    private ADVSAppImpLoader appImpLoader;
    private ADVSWallAdLoader wallAdLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_ad_sample);

        // init
        AppDavis.init(this.getApplicationContext(), getString(R.integer.media_id));

        // notify
        appImpLoader = AppDavis.createAppImpLoader(getApplicationContext());
        appImpLoader.notifyAppImp();
        appImpLoader.setOnAppImpLoadListener(this);

        // wallAd
        wallAdLoader = AppDavis.createWallAdLoader(this);
        wallAdLoader.setOnWallAdLoadListener(this);
        this.findViewById(R.id.button_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallAdLoader.show(WallAdActivity.class);
            }
        });
        this.findViewById(R.id.button_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallAdLoader.show(CustomWallAdActivity.class);
            }
        });
    }

    /*
     * ADVSWallAdLoadListener Interface
     */
    @Override
    public void wallAdLoaderWillPresentWallAd() {
        App.logd("wallAdLoaderWillPresentWallAd");
    }

    @Override
    public void wallAdLoaderDidFailToLoadAdError(String errorString) {
        App.logd("wallAdLoaderDidFailToLoadAdError:" + errorString);
    }

    /*
     * ADVSAppImpLoadListener Interface
     */
    @Override
    public void appImpLoaderDidFinishNotifyingApp() {
        App.logd("appImpLoaderDidFinishNotifyingApp");
    }

    @Override
    public void appImpLoaderDidFailToNotifyingAppError(String errorString) {
        App.logd("appImpLoaderDidFailToNotifyingAppError:" + errorString);
    }
}
