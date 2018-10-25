package com.adasplus.proadas.common.util;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhangyapeng on 18-5-4.
 */

public class WebViewUtil {
    public static void initWebView(WebView mWebView){
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
    }
    public static class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }
}
