package com.example.usemtburn_android_instream.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.usemtburn_android_sdk.R;
import com.example.usemtburn_android_util.ImageLoader;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacer;
import com.mtburn.android.sdk.model.ADVSInstreamInfoModel;

class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    
    static class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ADVSInstreamAdPlacer adPlacer;
        ADVSInstreamInfoModel adData;
        
        TextView advertiserName;
        TextView adText;
        ImageView adImage;
        ImageView iconImage;
        TextView adSponsoredLabel;
        
        public AdViewHolder(View itemView, ADVSInstreamAdPlacer adPlacer) {
            super(itemView);
            this.adPlacer = adPlacer;
            
            itemView.setOnClickListener(this);
            
            advertiserName = (TextView) itemView.findViewById(R.id.custom_instream_advertiser_name);
            adText = (TextView) itemView.findViewById(R.id.custom_instream_ad_text);
            adImage = (ImageView) itemView.findViewById(R.id.custom_instream_ad_image);
            iconImage = (ImageView) itemView.findViewById(R.id.custom_instream_advertiser_icon);
            adSponsoredLabel = (TextView) itemView.findViewById(R.id.custom_instream_sponsor_name);
        }
        
        void setData(ADVSInstreamInfoModel adData) {
            this.adData = adData;
            
            advertiserName.setText(adData.title());
            adText.setText(adData.content());
            
            String displayedAdvertiser = adData.displayedAdvertiser();
            if (null != displayedAdvertiser && 0 < displayedAdvertiser.length()) {
                adSponsoredLabel.setText(displayedAdvertiser);
            }
            
            (new ImageLoader(adData.creative_url(), adImage)).execute();
            (new ImageLoader(adData.icon_creative_url(), iconImage)).execute();
        }
        
        @Override
        public void onClick(View view) {
            adPlacer.sendClickEvent(adData);
        }
    }
    
    static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
    
    private static final int ITEM_VIEWTYPE_AD      = 0;
    private static final int ITEM_VIEWTYPE_CONTENT = 1;

    List<Object> persons;
    private ADVSInstreamAdPlacer adPlacer;

    RVAdapter(List<Object> persons, ADVSInstreamAdPlacer adPlacer){
        this.persons = persons;
        this.adPlacer = adPlacer;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
    
    @Override
    public int getItemViewType(int position) {
        return isAd(position) ? ITEM_VIEWTYPE_AD : ITEM_VIEWTYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == ITEM_VIEWTYPE_AD) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_instream_ad_view, parent, false);
            return new AdViewHolder(v, adPlacer);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
            return new PersonViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_VIEWTYPE_AD) {
            ADVSInstreamInfoModel adInfo = (ADVSInstreamInfoModel) persons.get(position);
            AdViewHolder avh = (AdViewHolder)holder;
            avh.setData(adInfo);
            
            // インプレッションを送信する（重複排除などは SDK がやるため、毎回通信などは発生しない）
            adPlacer.measureImp(adInfo);
        } else {
            Person p = ((Person)persons.get(position));
            PersonViewHolder pvh = (PersonViewHolder)holder;
            pvh.personName.setText(p.name);
            pvh.personAge.setText(p.age);
            pvh.personPhoto.setImageResource(p.photoId);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    
    private boolean isAd(int position) {
        return (persons.get(position) instanceof ADVSInstreamInfoModel) ? true : false;
    }
}
