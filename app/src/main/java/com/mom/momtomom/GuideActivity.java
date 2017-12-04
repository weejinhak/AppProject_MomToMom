package com.mom.momtomom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mom.momtomom.Adapter.BackPressCloseHandler;

/**
 * Created by wee on 2017. 11. 28..
 */

public class GuideActivity extends AppCompatActivity {

    private WebView mWebView;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        backPressCloseHandler= new BackPressCloseHandler(this);

        //webView
        mWebView = findViewById(R.id.main_webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://babytree.hani.co.kr/30283");
        mWebView.setWebViewClient(new WebViewClientClass());
        mWebView.setVerticalScrollBarEnabled(true);

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
