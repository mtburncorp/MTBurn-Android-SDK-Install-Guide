package com.example.usemtburn_android_instream.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.usemtburn_android_sdk.App;
import com.example.usemtburn_android_sdk.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        AppDavis.init(getActivity().getApplicationContext(), getString(R.integer.media_id));
        adPlacer = AppDavis.createInstreamAdPlacer(getActivity().getApplicationContext(), getAdSpotId());
    
        //　任意の View に広告案件情報を割り当てる
        InstreamAdViewBinderImpl adViewBinder = new InstreamAdViewBinderImpl(getActivity().getApplicationContext()){
            @Override
            public View createView(ViewGroup parent, int layoutId)
            {
                View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.custom_instream_ad_view, parent, false);
                AdViewHolder holder = new AdViewHolder(view);
                view.setTag(holder);
                return view;
            }
            
            @Override
            public void bindAdData(View v, ADVSInstreamInfoModel adData) {
                AdViewHolder holder = (AdViewHolder)v.getTag();
                holder.setData(adData);
                
                loadAdImage(adData, holder.adImage, null);
                loadAdIconImage(adData, holder.iconImage, null);
            }
        };
        adPlacer.registerAdViewBinder(adViewBinder);
        
        // 広告読み込み開始
        adPlacer.setAdListener(this);
        adPlacer.loadAd();
    }
    
    private class CustomInstreamSampleAdapter extends BaseAdapter {
        
        private static final String TAG_DEFAULT = "TAG_DEFAULT";
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
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return isAd(position) ? 0 : 1;
        }
        
        private boolean isAd(int position) {
            return (getItem(position) instanceof ADVSInstreamInfoModel) ? true : false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (isAd(position)) {
                // 広告案件を表現するカスタム View を取り出して表示する
                convertView = adPlacer.placeAd((ADVSInstreamInfoModel) getItem(position), convertView, parent);
            } else {
                // コンテンツ View を表示する
                if (null == convertView || !TAG_DEFAULT.equals(convertView.getTag())) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent,false);
                    convertView.setTag(TAG_DEFAULT);
                }
                TextView textView = (TextView)convertView.findViewById(android.R.id.text1);
                textView.setText((String)getItem(position));
            }
            return convertView;
        }
    }
    
    // AppDavis SDK から広告案件情報のロード進捗を伝えるためのコールバック群
    @Override
    public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
        App.logd("onAdsLoaded");
        
        for (ADVSInstreamInfoModel adData : items) {
            adapter.dataSourceList.add(3, adData);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onAdMainImageLoaded(String imageUrl) {
        App.logd("onAdMainImageLoaded:imageUrl=" + imageUrl);
    }
    @Override
    public void onAdIconImageLoaded(String imageUrl) {
        App.logd("onAdIconImageLoaded:imageUrl=" + imageUrl);
    }
    @Override
    public void onAdsLoadedFail(String errorString) {
        App.logd("onAdsLoadedFail:error=" + errorString);
    }
    @Override
    public void onAdImageLoadedFail(String imageUrl, String errorString) {
        App.logd("onAdImageLoadedFail:error" + errorString);
    }
    @Override
    public void onAdClicked(String redirectURL) {
        App.logd("onAdClicked:redirectURL" + redirectURL);
    }
    // --- ここまでで、コールバック群おわり
    
    // Utility function（広告枠 ID をリソースから取得する）
    private String getAdSpotId() {
        String name = "media_id_"+getString(R.integer.media_id)+"_adspot_custom";
        return getString(getResources().getIdentifier(name, "string", getActivity().getPackageName()));
    }
    
    static class AdViewHolder {
        TextView advertiserName;
        TextView adText;
        ImageView adImage;
        ImageView iconImage;
        
        public AdViewHolder(View convertView) {
            advertiserName = (TextView) convertView.findViewById(R.id.custom_instream_advertiser_name);
            adText = (TextView) convertView.findViewById(R.id.custom_instream_ad_text);
            adImage = (ImageView) convertView.findViewById(R.id.custom_instream_ad_image);
            iconImage = (ImageView) convertView.findViewById(R.id.custom_instream_advertiser_icon);
        }
        
        void setData(ADVSInstreamInfoModel adData) {
            advertiserName.setText(adData.title());
            adText.setText(adData.content());
        }
    }
}
