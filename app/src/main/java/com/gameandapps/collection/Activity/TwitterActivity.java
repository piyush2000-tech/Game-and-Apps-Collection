package com.gameandapps.collection.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.gameandapps.collection.Connection.ConnectionManager;
import com.gameandapps.collection.R;

public class TwitterActivity extends AppCompatActivity {
    WebView twitterWebView;
    SwipeRefreshLayout twitterSwipe;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        getSupportActionBar().hide();

        twitterWebView = findViewById(R.id.twitterWebView);
        twitterSwipe = findViewById(R.id.twitterSwipe);
        relativeLayout = findViewById(R.id.relativeLayout);
        progressBar = findViewById(R.id.progress);

        relativeLayout.setVisibility(View.VISIBLE);

        if(ConnectionManager.checkConnectivity(this)){
            twitterSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    twitterWebView.reload();
                    twitterSwipe.setRefreshing(false);
                }
            });

            twitterWebView.getSettings().setJavaScriptEnabled(true);
            twitterWebView.setWebViewClient(new Browser_Home());
            twitterWebView.setWebChromeClient(new Twitter());
            WebSettings webSettings = twitterWebView.getSettings();

            //webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            twitterWebView.setBackgroundColor(Color.BLACK);
            twitterWebView.loadUrl("https://www.twitter.com");
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Internet connection not found!");
            builder.setIcon(R.drawable.ic_about_app);
            builder.setTitle("Alert!");
            builder.setCancelable(false);

            builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    //ActivityCompat.finishAffinity(YouTubeActivity.class);
                }
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }
    }
    private class Browser_Home extends WebViewClient {
        Browser_Home(){}

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url==null||url.startsWith("http://")||url.startsWith("https://"))
                return false;
            try{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }catch (Exception e){
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            relativeLayout.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
    private class Twitter extends WebChromeClient {
        private View mCustomerView;
        private WebChromeClient.CustomViewCallback customViewCallback;
        protected FrameLayout mFullScreen;
        private int mOriginal;
        private int mOriginalSystemUiVisibility;

        Twitter(){}


        public Bitmap getDefaultVideoPoster(){
            if(mCustomerView==null){
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(),2130837573);
        }
        public void onHideCustomView(){

            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomerView);
            this.mCustomerView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginal);
            this.customViewCallback.onCustomViewHidden();
            this.customViewCallback = null;
        }
        public void onShowCustomView(View paramView,WebChromeClient.CustomViewCallback paramCustomViewCallBack) {
            try {
                if (this.mCustomerView != null) {
                    onHideCustomView();
                    return;
                }
                this.mCustomerView = paramView;
                this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                this.mOriginal = getRequestedOrientation();
                this.customViewCallback = paramCustomViewCallBack;
                ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomerView, new FrameLayout.LayoutParams(-1, -1));
                getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(twitterWebView.canGoBack()){
            twitterWebView.goBack();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to Exit Twitter?");
            builder.setIcon(R.drawable.ic_about_app);
            builder.setTitle("Alert!");
            builder.setCancelable(false);

            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    //ActivityCompat.finishAffinity(YouTubeActivity.class);
                }
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }
    }
}
