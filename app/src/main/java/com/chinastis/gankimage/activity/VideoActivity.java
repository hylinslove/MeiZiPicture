package com.chinastis.gankimage.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chinastis.gankimage.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        WebView videoWeb = (WebView) findViewById(R.id.web_video);

        videoWeb.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.finish();
    }
}
