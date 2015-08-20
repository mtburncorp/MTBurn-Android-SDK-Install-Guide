package com.example.usemtburn_android_instream.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usemtburn_android_instream.DrawableCache;
import com.example.usemtburn_android_instream.ItemModel;
import com.example.usemtburn_android_sdk.R;

public class ItemAdapter extends ArrayAdapter<ItemModel> {
    private static final int CUSTOM_VIEW_TYPE = 3;
    private Activity myContext;
    private ArrayList<ItemModel> datas;

    public ItemAdapter(Context context, int textViewResourceId, ArrayList<ItemModel> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;
    }
    
    public int getItemViewType(int position) {
        return position % CUSTOM_VIEW_TYPE; // 行じ応じたViewのタイプを返す。タイプは0,1,2,3...と0始まりであること。
    }
    
    public int getViewTypeCount() {
        return CUSTOM_VIEW_TYPE; // Viewのタイプの数
    }
    
    public int getCount() {
        return datas.size()/3;
    }
    
    static class ViewHolder {
        TextView  leftContentTextView;
        ImageView leftImageView;
        TextView  rightContentTextView;
        ImageView rightImageView;
        
        public ViewHolder(View convertView) {
            // TODO Auto-generated constructor stub
            leftContentTextView = (TextView) convertView.findViewById(R.id.leftContent1x1);
            leftImageView = (ImageView) convertView.findViewById(R.id.leftImage1x1);
            rightContentTextView = (TextView) convertView.findViewById(R.id.rightContent1x1);
            rightImageView = (ImageView) convertView.findViewById(R.id.rightImage1x1);
            
            convertView.setTag(this);
        }
        
        void setDatas(ItemModel[] datas) {
            leftContentTextView.setText(datas[0].title);
            leftImageView.setImageDrawable(datas[0].imageDrawable);
            rightContentTextView.setText(datas[1].title);
            rightImageView.setImageDrawable(datas[1].imageDrawable);
        }
    }
    
    static class ViewHolder1x3 {
        TextView  leftContentTextView;
        ImageView leftImageView;
        TextView  rightTopContentTextView;
        TextView  rightMiddleContentTextView;
        TextView  rightBottomContentTextView;
        
        public ViewHolder1x3(View convertView) {
            // TODO Auto-generated constructor stub
            leftContentTextView = (TextView) convertView.findViewById(R.id.leftContent1x3);
            leftImageView = (ImageView) convertView.findViewById(R.id.leftImage1x3);
            rightTopContentTextView = (TextView) convertView.findViewById(R.id.rightTopContent1x3);
            rightMiddleContentTextView = (TextView) convertView.findViewById(R.id.rightMiddleContent1x3);
            rightBottomContentTextView = (TextView) convertView.findViewById(R.id.rightBottomContent1x3);
            
            convertView.setTag(this);
        }
        
        void setDatas(ItemModel[] datas) {
            leftContentTextView.setText(datas[0].title);
            leftImageView.setImageDrawable(datas[0].imageDrawable);
            rightTopContentTextView.setText(datas[1].title);
            rightMiddleContentTextView.setText(datas[2].title);
            rightBottomContentTextView.setText(datas[3].title);
        }
    }
    
    static class ViewHolder3x1 {
        TextView  leftContentTextView;
        ImageView leftImageView;
        TextView  rightTopContentTextView;
        TextView  rightMiddleContentTextView;
        TextView  rightBottomContentTextView;
        
        public ViewHolder3x1(View convertView) {
            // TODO Auto-generated constructor stub
            leftContentTextView = (TextView) convertView.findViewById(R.id.leftContent3x1);
            leftImageView = (ImageView) convertView.findViewById(R.id.leftImage3x1);
            rightTopContentTextView = (TextView) convertView.findViewById(R.id.rightTopContent3x1);
            rightMiddleContentTextView = (TextView) convertView.findViewById(R.id.rightMiddleContent3x1);
            rightBottomContentTextView = (TextView) convertView.findViewById(R.id.rightBottomContent3x1);
            
            convertView.setTag(this);
        }
        
        void setDatas(ItemModel[] datas) {
            leftContentTextView.setText(datas[0].title);
            leftImageView.setImageDrawable(datas[0].imageDrawable);
            rightTopContentTextView.setText(datas[1].title);
            rightMiddleContentTextView.setText(datas[2].title);
            rightBottomContentTextView.setText(datas[3].title);
        }
    }
    
    private Drawable getDrawableFromImageResIdStr(String imageResIdStr)
    {
        // if you want to customize to display images, comment out this line and put image files in res/drawable-xxhdpi/rss_[0-8]_[1-4].xxx
        //  tab: range [0-8] (see res/values/main.xml category_names)
        //  rotate: range [1-4] (rotation of position value in `public View getView(int position, View convertView, ViewGroup parent)`)
        //  http://gyazo.com/3e16a27b4c9170121cba78ff183f1206
        imageResIdStr = "placeholder";
        Drawable imageDrawable = DrawableCache.getImage(imageResIdStr);
        if (imageDrawable == null) {
            int imageResource = myContext.getResources().getIdentifier(imageResIdStr, "drawable", myContext.getPackageName());
            imageDrawable = ContextCompat.getDrawable(myContext, imageResource);
            
            DrawableCache.setImage(imageResIdStr, imageDrawable);
        }
        return imageDrawable;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    {
        int numberOfArticles = (int)(position / 4) * 12;
        
        switch (getItemViewType(position)) {
        case 1:
            if ((position-1)==0 || (position-1)%4 == 0) {
                numberOfArticles += 4;
            } else {
                numberOfArticles += 10;
            }
            
            ViewHolder viewHolder;
            
            if(convertView == null) {
                LayoutInflater inflater = myContext.getLayoutInflater();
                convertView = inflater.inflate(R.layout.item1x1, null);
                
                viewHolder = new ViewHolder(convertView);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            
            if (numberOfArticles+1 < datas.size()) {
                ItemModel data = datas.get(numberOfArticles);
                if (data.imageDrawable == null) {
                    data.imageDrawable = getDrawableFromImageResIdStr(data.imageResId + "_1");
                }
                ItemModel data1 = datas.get(++numberOfArticles);
                if (data1.imageDrawable == null) {
                    data1.imageDrawable = getDrawableFromImageResIdStr(data1.imageResId + "_2");
                }
                viewHolder.setDatas(new ItemModel[]{data, data1});
            }
            break;
        case 0:
            ViewHolder1x3 viewHolder1x3;
            
            if(convertView == null) {
                LayoutInflater inflater = myContext.getLayoutInflater();
                convertView = inflater.inflate(R.layout.item1x3, null);
                
                viewHolder1x3 = new ViewHolder1x3(convertView);
            } else {
                viewHolder1x3 = (ViewHolder1x3) convertView.getTag();
            }
            
            if (numberOfArticles+3 < datas.size()) {
                ItemModel data1x3 = datas.get(numberOfArticles);
                if (data1x3.imageDrawable == null) {
                    data1x3.imageDrawable = getDrawableFromImageResIdStr(data1x3.imageResId + "_3");
                }
                ItemModel data1x3_1 = datas.get(++numberOfArticles);
                ItemModel data1x3_2 = datas.get(++numberOfArticles);
                ItemModel data1x3_3 = datas.get(++numberOfArticles);
                viewHolder1x3.setDatas(new ItemModel[]{data1x3, data1x3_1, data1x3_2, data1x3_3});
            }
            
            break;
        case 2:
            numberOfArticles += 6;
            
            ViewHolder3x1 viewHolder3x1;
            
            if(convertView == null) {
                LayoutInflater inflater = myContext.getLayoutInflater();
                convertView = inflater.inflate(R.layout.item3x1, null);
                
                viewHolder3x1 = new ViewHolder3x1(convertView);
            } else {
                viewHolder3x1 = (ViewHolder3x1) convertView.getTag();
            }
            
            if (numberOfArticles+3 < datas.size()) {
                ItemModel data3x1 = datas.get(numberOfArticles);
                if (data3x1.imageDrawable == null) {
                    data3x1.imageDrawable = getDrawableFromImageResIdStr(data3x1.imageResId + "_4");
                }
                ItemModel data3x1_1 = datas.get(++numberOfArticles);
                ItemModel data3x1_2 = datas.get(++numberOfArticles);
                ItemModel data3x1_3 = datas.get(++numberOfArticles);
                viewHolder3x1.setDatas(new ItemModel[]{data3x1, data3x1_1, data3x1_2, data3x1_3});
            }
            
            break;
        }
        return convertView;
    }
}