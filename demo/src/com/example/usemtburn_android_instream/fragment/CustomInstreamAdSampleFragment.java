package com.example.usemtburn_android_instream.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.usemtburn_android_sdk.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//-----------------------------------------------------------------------
// SDK related package
//-----------------------------------------------------------------------
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacerListener;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacer;
import com.mtburn.android.sdk.instream.InstreamAdViewBinderImpl;
import com.mtburn.android.sdk.model.ADVSInstreamInfoModel;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

public class CustomInstreamAdSampleFragment extends Fragment implements ADVSInstreamAdPlacerListener {

    private ADVSInstreamAdPlacer adPlacer;
    
    private CustomInstreamSampleAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        adapter = new CustomInstreamSampleAdapter();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupSDK();
        
        View v = inflater.inflate(R.layout.fragment_custom_instream_ad_sample, container, false);        
        ListView listView = (ListView) v.findViewById(R.id.custom_instream_listview);
        listView.setAdapter(adapter);
        return v;
    }
    
    // AppDavis SDK を初期化して、広告案件のロードを開始する
    private void setupSDK() {
        AppDavis.init(getActivity(), getString(R.integer.media_id));
        adPlacer = AppDavis.createInstreamAdPlacer(getActivity(), getAdSpotId());
    
        //　任意の View に広告案件情報を割り当てる
        InstreamAdViewBinderImpl adViewBinder = new InstreamAdViewBinderImpl(getActivity()){
            @Override
            public void bindAdData(View v, ADVSInstreamInfoModel adData) {
                TextView advertiserName = (TextView) v.findViewById(R.id.custom_instream_advertiser_name);
                advertiserName.setText(adData.title());
                
                TextView adText = (TextView) v.findViewById(R.id.custom_instream_ad_text);
                adText.setText(adData.content());
                
                ImageView adImage = (ImageView) v.findViewById(R.id.custom_instream_ad_image);
                loadAdImage(adData, adImage, null);
                
                ImageView iconImage = (ImageView) v.findViewById(R.id.custom_instream_advertiser_icon);
                loadAdIconImage(adData, iconImage, null);
            }
        };
        adPlacer.registerAdViewBinder(adViewBinder);
        
        // 広告読み込み開始
        adPlacer.setAdListener(this);
        adPlacer.loadAd();
    }
    
    private class CustomInstreamSampleAdapter extends BaseAdapter {
        
        private static final String TAG_DEFAULT = "TAG_DEFAULT";
        private static final String TAG_ADVIEW = "TAG_ADVIEW";
        private List<Object> dataSourceList = new ArrayList<Object>(
                Arrays.asList(getResources().getStringArray(R.array.custom_instream_message_list))
        );
        
        @Override
        public int getCount() {
            return dataSourceList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSourceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Object obj = getItem(position);
            if (obj instanceof ADVSInstreamInfoModel) {
                // 広告案件を表現するカスタム View を取り出して表示する
                if (null == convertView || !TAG_ADVIEW.equals(convertView.getTag())) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.custom_instream_ad_view, parent, false);
                    convertView.setTag(TAG_ADVIEW);
                }
                convertView = adPlacer.placeAd((ADVSInstreamInfoModel) getItem(position), convertView, parent);
            } else {
                // コンテンツ View を表示する
                if (null == convertView || !TAG_DEFAULT.equals(convertView.getTag())) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent,false);
                    convertView.setTag(TAG_DEFAULT);
                }
                TextView textView = (TextView)convertView.findViewById(android.R.id.text1);
                textView.setText((String)obj);
            }
            return convertView;
        }
    }
    
    // AppDavis SDK から広告案件情報のロード進捗を伝えるためのコールバック群
    @Override
    public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
        Log.d("", "onAdsLoaded");
        
        for (ADVSInstreamInfoModel adData : items) {
            adapter.dataSourceList.add(3, adData);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onAdMainImageLoaded(String imageUrl) {
        Log.d("", "onAdMainImageLoaded:imageUrl=" + imageUrl);
    }
    @Override
    public void onAdIconImageLoaded(String imageUrl) {
        Log.d("", "onAdIconImageLoaded:imageUrl=" + imageUrl);
    }
    @Override
    public void onAdsLoadedFail(String errorString) {
        Log.d("", "onAdsLoadedFail:error=" + errorString);
    }
    @Override
    public void onAdImageLoadedFail(String imageUrl, String errorString) {
        Log.d("", "onAdImageLoadedFail:error" + errorString);
    }
    @Override
    public void onAdClicked(String redirectURL) {
        Log.d("", "onAdClicked:redirectURL" + redirectURL);
    }
    // --- ここまでで、コールバック群おわり
    
    // Utility function（広告枠 ID をリソースから取得する）
    private String getAdSpotId() {
        String name = "media_id_"+getString(R.integer.media_id)+"_adspot_custom";
        return getString(getResources().getIdentifier(name, "string", getActivity().getPackageName()));
    }
}
