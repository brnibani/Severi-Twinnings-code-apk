package com.app.gabriele.severitwinnings;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import severitwinnings.gabriele.app.com.severitwinnings.R;

public class DetailsActivity extends AppCompatActivity {

    private static boolean showspinner;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView = null;
    private FrameLayout mCustomViewContainer;
    public WebView mWebView;
    private ProgressBar progressBar;
    private ProgressDialog loading_message;
    private Toolbar toolbar;
    protected DownloadManager mgr;
    protected String dest = null;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_details);


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

        //Bundle bundle = this.getIntent().getExtras();
        //String postContent = bundle.getString("content");

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

        //String link_comment = (String) getIntent().getExtras().get("wfw:commentRss");
        //String num_comment = (String) getIntent().getExtras().get("slash:comments");
        String o_content = (String) getIntent().getExtras().get("content:encoded");

        String category = (String) getIntent().getExtras().get("category");
        String content = "<html> <body> <h2> <font color = DarkBlue >" + title + "</font> </h2>" + o_content + "</body> </html>";
        if (content.indexOf("<a rel=\"nofollow\"") != -1) {
            int content_length = content.length();
            int content_index_substring = content.indexOf("<a rel=\"nofollow\"");
            int remove_chars = content_length - content_index_substring;
            content = content.substring(0, content.length() - remove_chars);
        }
//        Log.d("MYFIRST LOG PRINT",content);
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
                super.onHideCustomView();
                if (mCustomView == null)
                    return;

                mWebView.setVisibility(View.VISIBLE);
                mCustomViewContainer.setVisibility(View.GONE);

                mCustomView.setVisibility(View.GONE);

                mCustomViewContainer.removeView(mCustomView);
                customViewCallback.onCustomViewHidden();

                mCustomView = null;
                toolbar.setVisibility(View.VISIBLE);
            }


        });

        String new_content = content.replace("<img", "<img height=\"auto\" width=\"100%\" border=\"5\" style=\"border:3px solid white\"");
        //String new_new_content = new_content.replace("<img height=\"auto\" width=\"100%\"  alt=\"\" border=\"0\" src=\"http://feeds.wordpress.com/1.0/comments/severitwinnings.wordpress.com/310/\" />", "  ");

       // System.out.println(new_new_content);
        WebViewClient wvc = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s_url) {
/**
                String type = null;
                if(s_url.lastIndexOf(".") != -1) {
                    String ext = s_url.substring(s_url.lastIndexOf(".")+1);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    type = mime.getMimeTypeFromExtension(ext);
                } else {
                    type = null;
                }
                if(type.equals("image/jpg") | type.equals("image/png") | type.equals("image/bmp")){
                    //Intent i = new Intent(DetailsActivity.this, ImageFullScreen.class);
                    //i.putExtra("urlfullscreen", s_url);
                    //startActivity(i);
                    downloadFile(s_url);
                    return true;
                }
**/
                downloadFile(s_url);

                return true;
            }

            public void openImage(String s){
                File file = new File(getDataFolder(DetailsActivity.this) + "/" ,s);
                Uri path = Uri.fromFile(file);
                Intent imgOpenintent = new Intent(Intent.ACTION_VIEW);
                imgOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                imgOpenintent.setDataAndType(path, "image/*");
                try {
                    startActivity(imgOpenintent);
                }
                catch (ActivityNotFoundException e) {

                }
            }

            public String downloadFile(String url_DF) {

                //long myDownloadReference;
                //BroadcastReceiver receiverDownloadComplete;
                //BroadcastReceiver receiverNotificationClicked;

                getDataFolder(DetailsActivity.this);

                mgr = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);

                dest = url_DF.substring(url_DF.lastIndexOf('/') + 1, url_DF.length());

                Uri downloadUri = Uri.parse(url_DF);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);

                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false).setTitle("Severi Twinnings")
                        .setDescription("Download dell'immagine in corso...")
                        .setDestinationInExternalPublicDir("/SeveriTwinnings", dest);

                try {
                    loading_message= new ProgressDialog(DetailsActivity.this);
                    loading_message.setMessage("Download dell'immagine in corso...");
                    loading_message.show();
                } catch (Exception e) {
                    Log.e("Severi Twinnings", e.getMessage());
                }

                mgr.enqueue(request);

                BroadcastReceiver onComplete=new BroadcastReceiver() {
                    public void onReceive(Context ctxt, Intent intent) {
                        loading_message.dismiss();
                        Toast.makeText(getBaseContext(), "Download completato", Toast.LENGTH_LONG).show();
                        openImage(dest);
                    }
                };
                registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));





                //   Toast.makeText(getBaseContext(), "Download immagine in corso...", Toast.LENGTH_LONG).show();
/**
                IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                receiverDownloadComplete = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
                        if (myDownloadReference == reference){
                            DownloadManager.Query query = new DownloadManager.Query();
                            query.setFilterById(reference);
                            Cursor cursor = mgr.query(query);
                            cursor.moveToFirst();

                            //get the status of the download
                            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            int status = cursor.getInt(columnIndex);
                            int fileNameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                            String savedFilePath = cursor.getString(fileNameIndex);

                            //get the reason - more detail on the status
                            int columnreason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                            int reason = cursor.getInt(columnreason);

                            switch (status){
                                case DownloadManager.STATUS_SUCCESSFUL:
                                    Toast.makeText(getBaseContext(), "Download Completato", Toast.LENGTH_LONG).show();
                                    break;
                                case DownloadManager.STATUS_FAILED:
                                    Toast.makeText(getBaseContext(), "Download Fallito", Toast.LENGTH_LONG).show();
                                    break;
                                case DownloadManager.STATUS_PAUSED:
                                    Toast.makeText(getBaseContext(), "Download in pausa", Toast.LENGTH_LONG).show();
                                    break;
                                case DownloadManager.STATUS_PENDING:
                                    Toast.makeText(getBaseContext(), "Download pendente", Toast.LENGTH_LONG).show();
                                    break;
                                case DownloadManager.STATUS_RUNNING:
                                    Toast.makeText(getBaseContext(), "Download in corso", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            cursor.close();
                        }
                    }
                };
                registerReceiver(receiverDownloadComplete,intentFilter);
**/

                return dest;
            }
            public File getDataFolder(Context context) {
                File dataDir = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    dataDir = new File(Environment.getExternalStorageDirectory(), "SeveriTwinnings");
                    if(!dataDir.isDirectory()) {
                        dataDir.mkdirs();
                    }
                }

                if(!dataDir.isDirectory()) {
                    dataDir = context.getFilesDir();
                }

                return dataDir;
            }


        };
        mWebView.setWebViewClient(wvc);
        mWebView.loadDataWithBaseURL("", new_content, "text/html", "UTF-8", null);

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
