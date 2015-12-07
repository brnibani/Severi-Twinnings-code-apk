package com.app.gabriele.severitwinnings;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import severitwinnings.gabriele.app.com.severitwinnings.R;

public class DetailsActivity extends AppCompatActivity {
    private static boolean showspinner;
    private WebChromeClient.CustomViewCallback customViewCallback;
//    private ProgressDialog download_image;
    private View mCustomView = null;
    private FrameLayout mCustomViewContainer;
    public WebView mWebView;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_details);

        // Makes Progress bar Visible
        //   getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        String postContent = bundle.getString("content");

        final String link = (String)getIntent().getExtras().get("link");

        String title = (String) getIntent().getExtras().get("title");
        final String new_title = title.toString();

        //Share Button on Toolbar
        ImageButton share_button = (ImageButton) findViewById(R.id.share);
        share_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, new_title);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, link);
                startActivity(Intent.createChooser(sharingIntent, "Condividi"));
            }
        });

        String link_comment = (String) getIntent().getExtras().get("wfw:commentRss");
        String num_comment = (String) getIntent().getExtras().get("slash:comments");
        //System.out.println("ECCO il LINK " + link_comment);
       // System.out.println(num_comment);
        String o_content = (String) getIntent().getExtras().get("content:encoded");
        String category = (String) getIntent().getExtras().get("category");
        String content = "<html> <body> <h2> <font color = DarkBlue >" + title + "</font> </h2>" + o_content + "</body> </html>";
        getSupportActionBar().setTitle(category);

        mWebView = (WebView) this.findViewById(R.id.webview);

        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setSaveFormData(true);
        mWebView.getSettings().setJavaScriptEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }


        final Activity activity = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mCustomViewContainer = (FrameLayout)findViewById(R.id.fullscreen_custom_content);

        mWebView.setWebChromeClient(new WebChromeClient() {
            FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

            @Override
            public void onProgressChanged(WebView view, int progress) {

                progressBar.setProgress(0);
                activity.setProgress(progress * 1000);
                progressBar.incrementProgressBy(progress);

            }

            //I metodi onShowCustomView e onHideCustomView servono per fare andare a tutto schermo
            //il video nella webview (es: video vimeo di Corning)
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) { //added last version

                // if a view already exists then immediately terminate the new one
                if (mCustomView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                mCustomView = view;
                mWebView.setVisibility(View.GONE);
                mCustomViewContainer.setVisibility(View.VISIBLE);
                mCustomViewContainer.addView(view);
                customViewCallback = callback;
                toolbar.setVisibility(View.INVISIBLE);
            }


            @Override
            public void onHideCustomView() { //added last version
                super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
                if (mCustomView == null)
                    return;

                mWebView.setVisibility(View.VISIBLE);
                mCustomViewContainer.setVisibility(View.GONE);

                // Hide the custom view.
                mCustomView.setVisibility(View.GONE);

                // Remove the custom view from its container.
                mCustomViewContainer.removeView(mCustomView);
                customViewCallback.onCustomViewHidden();

                mCustomView = null;
                toolbar.setVisibility(View.VISIBLE);
            }



        });

        String new_content = content.replace("<img", "<img height=\"auto\" width=\"100%\" ");
        String new_new_content = new_content.replace("<img height=\"auto\" width=\"100%\"  alt=\"\" border=\"0\" src=\"http://feeds.wordpress.com/1.0/comments/severitwinnings.wordpress.com/310/\" />", "  ");

        System.out.println(new_new_content);
        WebViewClient wvc = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s_url) {
                String type = null;
                if(s_url.lastIndexOf(".") != -1) {
                    String ext = s_url.substring(s_url.lastIndexOf(".")+1);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    type = mime.getMimeTypeFromExtension(ext);
                } else {
                    type = null;
                }
                if(type.equals("image/jpeg")){
                    Intent i = new Intent(DetailsActivity.this, ImageFullScreen.class);
                    i.putExtra("urlfullscreen", s_url);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        };
        mWebView.setWebViewClient(wvc);

        mWebView.loadDataWithBaseURL("", new_new_content, "text/html", "UTF-8", null);
    }


    public void onPause()
    {
        super.onPause();
        this.mWebView.onPause();
    }



    protected void onRestoreInstanceState(Bundle paramBundle)
    {
        super.onRestoreInstanceState(paramBundle);
        this.mWebView.restoreState(paramBundle);
    }

    public void onResume()
    {
        super.onResume();
        this.mWebView.onResume();
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        this.mWebView.saveState(paramBundle);
    }

    protected void onStart()
    {
        super.onStart();
    }

    public void spinnerOff()
    {
        showspinner = false;
        setProgressBarIndeterminateVisibility(false);
    }

    public void spinnerOn()
    {
        showspinner = true;
        setProgressBarIndeterminateVisibility(true);
    }

}
