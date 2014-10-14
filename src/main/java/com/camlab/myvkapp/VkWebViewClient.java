package com.camlab.myvkapp;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by alex on 14.10.2014.
 */
public class VkWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
