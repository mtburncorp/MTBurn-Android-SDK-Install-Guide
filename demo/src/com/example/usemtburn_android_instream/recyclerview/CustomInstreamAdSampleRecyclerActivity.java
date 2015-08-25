package com.example.usemtburn_android_instream.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.usemtburn_android_sdk.App;
import com.example.usemtburn_android_sdk.R;

//-----------------------------------------------------------------------
//SDK related package
//-----------------------------------------------------------------------
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacerListener;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacer;
import com.mtburn.android.sdk.model.ADVSInstreamInfoModel;

public class CustomInstreamAdSampleRecyclerActivity extends Activity implements ADVSInstreamAdPlacerListener {
    
    private ADVSInstreamAdPlacer adPlacer;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        
        setupSDK();
        
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        initializeData();

        adapter = new RVAdapter(persons, adPlacer);
        rv.setAdapter(adapter);
    }

    private List<Object> persons;

    private void initializeData() {
        persons = new ArrayList<>();
        persons.add(new Person("M.T.Burn 1", "We offer In-Feed Ads service", R.drawable.appdavis));
        persons.add(new Person("M.T.Burn 2", "This service is implemented by either simple or custom one", R.drawable.appdavis));
        persons.add(new Person("M.T.Burn 3", "This view is implemented by custom one", R.drawable.appdavis));
    }
    
    // AppDavis SDK を初期化して、広告案件のロードを開始する
    private void setupSDK() {
        AppDavis.init(this.getApplicationContext(), getString(R.integer.media_id));
        adPlacer = AppDavis.createInstreamAdPlacer(this.getApplicationContext(), getAdSpotId());
        adPlacer.setAdListener(this);
        
        // 広告読み込み開始
        adPlacer.loadAd();
    }
    
    // Utility function（広告枠 ID をリソースから取得する）
    private String getAdSpotId() {
        String name = "media_id_"+getString(R.integer.media_id)+"_adspot_custom";
        return getString(getResources().getIdentifier(name, "string", this.getPackageName()));
    }
    
    // AppDavis SDK から広告案件情報のロード進捗を伝えるためのコールバック群
    @Override
    public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
        App.logd("onAdsLoaded");
        
        int i = 1;
        for (ADVSInstreamInfoModel adData : items) {
            persons.add(i, adData);
            i += 2;
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onAdMainImageLoaded(String imageUrl) {
        // not invoked
    }
    @Override
    public void onAdIconImageLoaded(String imageUrl) {
        // not invoked
    }
    @Override
    public void onAdImageLoadedFail(String imageUrl, String errorString) {
        // not invoked
    }
    @Override
    public void onAdsLoadedFail(String errorString) {
        App.logd("onAdsLoadedFail:error=" + errorString);
    }
    @Override
    public void onAdClicked(String redirectURL) {
        App.logd("onAdClicked:redirectURL" + redirectURL);
    }
    // --- ここまでで、コールバック群おわり
}

