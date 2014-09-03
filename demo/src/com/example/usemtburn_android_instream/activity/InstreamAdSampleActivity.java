package com.example.usemtburn_android_instream.activity;

import com.example.usemtburn_android_instream.Tab;
import com.example.usemtburn_android_instream.adapter.TabPagerAdapter;
import com.example.usemtburn_android_instream.fragment.TabsFragment;
import com.example.usemtburn_android_sdk.R;
import com.mtburn.android.sdk.AppDavis;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.Window;

public class InstreamAdSampleActivity extends FragmentActivity {  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_instream_ad_sample);
        
        // SDK
        AppDavis.init(this, getString(R.integer.media_id));
   
        FragmentManager fragmentManager = getSupportFragmentManager();
        TabsFragment tabs = (TabsFragment) fragmentManager.findFragmentById(R.id.tabs);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        TabPagerAdapter adapter = new TabPagerAdapter(fragmentManager, getTabs());

        pager.setAdapter(adapter);
        
        tabs.setViewPager(pager);
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
    }

    private Tab[] getTabs() {
        TypedArray categoryColors = getResources().obtainTypedArray(R.array.category_colors);
        String[] categoryNames = getResources().getStringArray(R.array.category_names);
        String[] categoryUrls = getResources().getStringArray(R.array.category_urls);

        Tab[] tabs = new Tab[categoryNames.length];
        for (int i = 0; i < tabs.length; i++) {
            tabs[i] = new Tab(categoryNames[i], categoryColors.getColor(i % categoryColors.length(), 0), categoryUrls[i]);
        }

        categoryColors.recycle();
        return tabs;
    }
}
