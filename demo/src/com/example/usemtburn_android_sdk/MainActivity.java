package com.example.usemtburn_android_sdk;

import com.example.usemtburn_android_instream.activity.CustomInstreamAdSampleActivity;
import com.example.usemtburn_android_instream.activity.InstreamAdSampleActivity;
import com.example.usemtburn_android_instream.recyclerview.CustomInstreamAdSampleRecyclerActivity;

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
                    // instream ad (ListView)
                    startInstreamAdSample();
                    break;
                case 3:
                    // custom instream ad (ListView)
                    startCustomInstreamAdSample();
                    break;
                case 4:
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
    
    private void startCustomInstreamAdSample() {
        Intent intent = new Intent(this, CustomInstreamAdSampleActivity.class);
        startActivity(intent);
    }
    
    private void startCustomInstreamAdRecyclerSample() {
        Intent intent = new Intent(this, CustomInstreamAdSampleRecyclerActivity.class);
        startActivity(intent);
    }
}
