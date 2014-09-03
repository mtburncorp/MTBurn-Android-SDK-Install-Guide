package com.example.usemtburn_android_sdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.appimp.ADVSAppImpLoadListener;
import com.mtburn.android.sdk.appimp.ADVSAppImpLoader;
import com.mtburn.android.sdk.icon.ADVSIconAdLoadListener;
import com.mtburn.android.sdk.icon.ADVSIconAdLoader;
import com.mtburn.android.sdk.icon.ADVSIconAdViewListener;
import com.mtburn.android.sdk.icon.IconAdView;

public class IconAdSampleActivity extends Activity implements ADVSIconAdLoadListener, ADVSIconAdViewListener, ADVSAppImpLoadListener {

    private ADVSAppImpLoader appImpLoader;
    private ADVSIconAdLoader iconAdLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_ad_sample);

        // init
        AppDavis.init(this, getString(R.integer.media_id));

        // notify
        appImpLoader = AppDavis.createAppImpLoader(getApplicationContext());
        appImpLoader.notifyAppImp();
        appImpLoader.setOnAppImpLoadListener(this);

        // iconAdLoader
        iconAdLoader = AppDavis.createIconAdLoader(this);
        iconAdLoader.setOnIconAdLoadListener(this);

        IconAdView iconView = (IconAdView) this.findViewById(R.id.iconAd1);
        iconView.setOnADVSIconAdViewListener(this);
        iconAdLoader.addIconView((IconAdView) iconView);
        iconAdLoader.addIconView((IconAdView) this.findViewById(R.id.iconAd2));
        iconAdLoader.addIconView((IconAdView) this.findViewById(R.id.iconAd3));
        iconAdLoader.addIconView((IconAdView) this.findViewById(R.id.iconAd4));
        iconAdLoader.addIconView((IconAdView) this.findViewById(R.id.iconAd5));
        iconAdLoader.addIconView((IconAdView) this.findViewById(R.id.iconAd6));
        iconAdLoader.loadAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iconAdLoader.startAutoRefreshing();
    }

    @Override
    protected void onPause() {
        super.onPause();
        iconAdLoader.stopAutoRefreshing();
    }

    /*
     * ADVSIconAdLoadListener Interface
     */
    @Override
    public void iconAdLoaderDisStartLoadingAd() {
        Log.d("ADVSIconAdLoadListener", "iconAdLoaderDidStartLoadingAd");
    }

    @Override
    public void iconAdLoaderDidFinishLoadingAd() {
        Log.d("ADVSIconAdLoadListener", "iconAdLoaderDidFinishLoadingAd");
    }

    @Override
    public void iconAdLoaderDidFailToLoadAdError(String errorString) {
        Log.d("ADVSIconAdLoadListener", "iconAdLoaderDidFailToLoadAdError:" + errorString);
    }

    /*
     * ADVSIconAdViewListener Interface
     */
    @Override
    public void iconAdLoaderDidReceiveIconAdView() {
        Log.d("ADVSIconAdViewListener", "didReceiveIconAdView");
    }

    @Override
    public void iconAdLoaderDidFailedToReceiveIconViewError(String erroString) {
        Log.d("ADVSIconAdViewListener", "didFailedToReceiveIconView:" + erroString);
    }

    @Override
    public void iconAdLoaderDidClickIconAdView() {
        Log.d("ADVSIconAdViewListener", "didClickIconAdView");
    }

    /*
     * ADVSAppImpLoadListener Interface
     */
    @Override
    public void appImpLoaderDidFinishNotifyingApp() {
        Log.d("ADVSAppImpLoadListener", "appImpLoaderDidFinishNotifyingApp");
    }

    @Override
    public void appImpLoaderDidFailToNotifyingAppError(String errorString) {
        Log.d("ADVSAppImpLoadListener", "appImpLoaderDidFailToNotifyingAppError:" + errorString);
    }
}
