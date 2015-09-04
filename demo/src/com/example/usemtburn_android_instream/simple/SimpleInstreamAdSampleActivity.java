package com.example.usemtburn_android_instream.simple;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.usemtburn_android_sdk.App;
import com.example.usemtburn_android_sdk.R;

//-----------------------------------------------------------------------
//SDK related package
//-----------------------------------------------------------------------
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdAdapter;
import com.mtburn.android.sdk.instream.ADVSInstreamAdLoadListener;

public class SimpleInstreamAdSampleActivity extends Activity implements ADVSInstreamAdLoadListener {
    
    private static final String[] textViews = {
        "0", "1", "2", "3", "4",
        "5", "6", "7", "8", "9", 
        "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19",
        "20", "21", "22", "23", "24",
        "25", "26", "27", "28", "29",
        "30", "31", "32", "33", "34",
        "35", "36", "37", "38", "39",
        "40", "41", "42", "43", "44",
        "45", "46", "47", "48", "49"
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_instream_ad_sample);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textViews);
        
        // SDK を初期化する
        AppDavis.init(this.getApplicationContext(), getString(R.integer.media_id));
        
        // 広告アダプターを生成する
        List<Integer> positionList = new ArrayList<Integer>(Arrays.asList(15,31,52));
        ADVSInstreamAdAdapter<ArrayAdapter<String>> advsAdapter = AppDavis.createInstreamAdAdapter(getApplicationContext(), adapter, getAdSpotId(), 3, positionList);
        
        // ビューにアダプターをセットする
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(advsAdapter);
        
        // 広告情報のロードを開始する
        advsAdapter.setAdListener(this);
        advsAdapter.loadAd();
    }

    // Utility function（広告枠 ID をリソースから取得する）
    private String getAdSpotId() {
        String name = "media_id_"+getString(R.integer.media_id)+"_adspot";
        int resId = getResources().getIdentifier(name, "array", getPackageName());
        String[] adSpotIds = getResources().getStringArray(resId);
        return adSpotIds[0];
    }
    
    // AppDavis SDK から広告案件情報のロード進捗を伝えるためのコールバック群
    @Override
    public void instreamAdLoaderDidStartLoadingAd()
    {
        App.logd("instreamAdLoaderDidStartLoadingAd");
    }
    @Override
    public void instreamAdLoaderDidFinishLoadingAds()
    {
        App.logd("instreamAdLoaderDidFinishLoadingAds");
    }
    @Override
    public void instreamAdLoaderDidFinishLoadingMainImage(String imageUrl)
    {
        App.logd("instreamAdLoaderDidFinishLoadingMainImage:image=" + imageUrl);
    }
    @Override
    public void instreamAdLoaderDidFinishLoadingIconImage(String imageUrl)
    {
        App.logd("instreamAdLoaderDidFinishLoadingIconImage:image=" + imageUrl);
    }
    @Override
    public void instreamAdLoaderDidFailToLoadAdError(String errorString)
    {
        App.logd("instreamAdLoaderDidFailToLoadAdError:error=" + errorString);
    }
    @Override
    public void instreamAdLoaderDidFailToLoadImageError(String errorString)
    {
        App.logd("instreamAdLoaderDidFailToLoadImageError:error=" + errorString);
    }
    @Override
    public void instreamAdLoaderDidClick(String redirectUrl)
    {
        App.logd("instreamAdLoaderDidClick:tabPosition=:redirectUrl=" + redirectUrl);
    }
    // --- ここまでで、コールバック群おわり
}

