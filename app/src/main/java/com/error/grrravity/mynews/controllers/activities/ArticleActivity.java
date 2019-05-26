package com.error.grrravity.mynews.controllers.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        String URL = getIntent().getStringExtra(APIResult.TOPSTORIES_EXTRA);

        updateUI(URL);
        configureToolbar();
        onConfigureWebView();
        onPageFinished();
    }

    //TOOLBAR
    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void onConfigureWebView() {
        //Configure webview to use javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Allows windows opening
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // Allows Document Object Model Storage
        webSettings.setDomStorageEnabled(true);
    }

    private void onPageFinished() {
        mWebView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //UPDATE UI

    protected void updateUI(String url) {
        mWebView.loadUrl(url);
    }
}
