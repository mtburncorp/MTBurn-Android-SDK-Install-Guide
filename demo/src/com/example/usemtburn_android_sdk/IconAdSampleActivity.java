package com.example.usemtburn_android_sdk;

import android.app.Activity;
import android.os.Bundle;

import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.appimp.ADVSAppImpLoadListener;
import com.mtburn.android.sdk.appimp.ADVSAppImpLoader;
import com.mtburn.android.sdk.icon.ADVSIconAdLoadListener;
import com.mtburn.android.sdk.icon.ADVSIconAdLoader;
import com.mtburn.android.sdk.icon.ADVSIconAdViewListener;
import com.mtburn.android.sdk.icon.IconAdView;

@SuppressWarnings("deprecation")
public class IconAdSampleActivity extends Activity implements ADVSIconAdLoadListener, ADVSIconAdViewListener, ADVSAppImpLoadListener {

    private ADVSAppImpLoader appImpLoader;
    private ADVSIconAdLoader iconAdLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_ad_sample);

        // init
        AppDavis.init(this.getApplicationContext(), getString(R.integer.media_id));

        // notify
        appImpLoader = AppDavis.createAppImpLoader(getApplicationContext());
        appImpLoader.notifyAppImp();
        appImpLoader.setOnAppImpLoadListener(this);

        // iconAdLoader
        iconAdLoader = AppDavis.createIconAdLoader(getApplicationContext());
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
        App.logd("iconAdLoaderDidStartLoadingAd");
    }

    @Override
    public void iconAdLoaderDidFinishLoadingAd() {
        App.logd("iconAdLoaderDidFinishLoadingAd");
    }

    @Override
    public void iconAdLoaderDidFailToLoadAdError(String errorString) {
        App.logd("iconAdLoaderDidFailToLoadAdError:" + errorString);
    }

    /*
     * ADVSIconAdViewListener Interface
     */
    @Override
    public void iconAdLoaderDidReceiveIconAdView() {
        App.logd("didReceiveIconAdView");
    }

    @Override
    public void iconAdLoaderDidFailedToReceiveIconViewError(String erroString) {
        App.logd("didFailedToReceiveIconView:" + erroString);
    }

    @Override
    public void iconAdLoaderDidClickIconAdView() {
        App.logd("didClickIconAdView");
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
