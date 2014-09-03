package com.example.usemtburn_android_instream.activity;

import com.example.usemtburn_android_sdk.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ItemViewActivity extends Activity {
    
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_item);
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
        
        webView = (WebView)this.findViewById(R.id.webview);
        //webView.loadData(postContent, "text/html; charset=utf-8","utf-8");
        webView.loadUrl(url);
    }
}