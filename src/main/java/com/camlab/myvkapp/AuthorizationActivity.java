package com.camlab.myvkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


public class AuthorizationActivity extends Activity implements View.OnClickListener {
    private WebView wv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        findViewById(R.id.test).setOnClickListener(this);
        wv = (WebView) findViewById(R.id.webview);

        wv.setWebViewClient(new VkWebViewClient());
        //get access token
        wv.loadUrl("http://oauth.vk.com/authorize?" +
                "client_id=4520252&" +
                "scope=wall,notify,docs&" +
                "redirect_uri=https://oauth.vk.com/blank.html&" +
                "display=wap&" +
                "v=5.25&" +
                "response_type=token");
    }

    @Override
    public void onClick(View v) {
        if (wv.getUrl().substring(31, 43).equals("access_token")) {
            String access_token = wv.getUrl().split("#")[1].split("&")[0].split("=")[1];
            Intent intent = new Intent(AuthorizationActivity.this, FriendsListActivity.class);
            intent.putExtra("VKAccessToken", access_token);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Authorization error. access_denied. Please try again. ", Toast.LENGTH_LONG).show();
        }
    }
}