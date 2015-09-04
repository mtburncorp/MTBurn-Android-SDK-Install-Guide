package com.example.usemtburn_android_sdk;

import com.example.usemtburn_android_instream.activity.CustomInstreamAdSampleActivity;
import com.example.usemtburn_android_instream.activity.InstreamAdSampleActivity;
import com.example.usemtburn_android_instream.recyclerview.CustomInstreamAdSampleRecyclerActivity;
import com.example.usemtburn_android_instream.simple.SimpleInstreamAdSampleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                case 0:
                    // icon ad
                    startIconAdSample();
                    break;
                case 1:
                    // wall ad
                    startWallAdSample();
                    break;
                case 2:
                    // simple instream ad (ListView)
                    startInstreamAdSample();
                    break;
                case 3:
                    // more simple instream ad (ListView)
                    startSimpleInstreamAdSample();
                    break;
                case 4:
                    // custom instream ad (ListView)
                    startCustomInstreamAdSample();
                    break;
                case 5:
                    // custom instream ad (RecyclerView)
                    startCustomInstreamAdRecyclerSample();
                    break;
                default:
                    break;
                }
            }
        });
    }

    private void startIconAdSample() {
        Intent intent = new Intent(this, IconAdSampleActivity.class);
        startActivity(intent);
    }

    private void startWallAdSample() {
        Intent intent = new Intent(this, WallAdSampleActivity.class);
        startActivity(intent);
    }
    
    private void startInstreamAdSample() {
        Intent intent = new Intent(this, InstreamAdSampleActivity.class);
        startActivity(intent);
    }
    
    private void startSimpleInstreamAdSample() {
        Intent intent = new Intent(this, SimpleInstreamAdSampleActivity.class);
        startActivity(intent);
    }
    
    private void startCustomInstreamAdSample() {
        Intent intent = new Intent(this, CustomInstreamAdSampleActivity.class);
        startActivity(intent);
    }
    
    private void startCustomInstreamAdRecyclerSample() {
        Intent intent = new Intent(this, CustomInstreamAdSampleRecyclerActivity.class);
        startActivity(intent);
    }
}
