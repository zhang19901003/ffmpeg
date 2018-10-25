package com.adasplus.proadas.business.about.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.proadas.business.R;
import com.adasplus.proadas.common.Constants;

/**
 * Created by zhangyapeng on 18-5-4.
 */

public class WebViewActivity extends Activity{
    private String mUrl;
    private WebView mWebView;
    private static final String DESC = "private.html" ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mUrl = getIntent().getStringExtra(Constants.HTML_PATH);
        TextView tvTitle = (TextView) findViewById(R.id.tv_main_title_textview);
        if(mUrl.contains(DESC)){
            tvTitle.setText(getResources().getString(R.string.private_policy));
        }else {
            tvTitle.setText(getResources().getString(R.string.term_of_service));
        }
        LinearLayout lvLeftView = findViewById(R.id.ll_left_layout);
        lvLeftView.setVisibility(View.VISIBLE);
        lvLeftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(TextUtils.isEmpty(mUrl)){
            return;
        }
        mWebView = (WebView) findViewById(R.id.web);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        if (android.os.Build.VERSION.SDK_INT > 11) {
            webSettings.setAllowContentAccess(true);
        }
        webSettings.setBuiltInZoomControls(false);
        mWebView.setWebViewClient(new WebClient());
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.clearCache(true);
        mWebView.destroy();
    }
    public class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }
}
