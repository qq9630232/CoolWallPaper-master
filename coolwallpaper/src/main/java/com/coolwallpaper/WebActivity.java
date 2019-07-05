package com.coolwallpaper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.coolwallpaper.download.ApkLaunchUtils;
import com.coolwallpaper.download.DownloadProgressDialog;
import com.coolwallpaper.download.IndexContract;
import com.coolwallpaper.download.IndexPresenter;

import java.io.File;

import static android.os.Process.killProcess;

public class WebActivity extends AppCompatActivity implements IndexContract.View {

    private DownloadProgressDialog progressDialog;
    private IndexPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO:OnCreate Method has been created, run FindViewById again to generate code
        setContentView(R.layout.activity_web);
        initView();
    }
    private void initView() {
        WebView mWebView = (WebView) findViewById(R.id.webview);
        LinearLayout webBg = (LinearLayout) findViewById(R.id.web_bg);
        String apkUrl = getIntent().getStringExtra("url");
        if (apkUrl.contains(".apk")) {
            mPresenter = new IndexPresenter(WebActivity.this);

            progressDialog = new DownloadProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // 设置ProgressDialog 标题
            progressDialog.setTitle("下载提示");
            // 设置ProgressDialog 提示信息
            progressDialog.setMessage("当前下载进度:");
            // 设置ProgressDialog 标题图标
            //progressDialog.setIcon(R.drawable.a);
            // 设置ProgressDialog 进度条进度
            // 设置ProgressDialog 的进度条是否不明确
            progressDialog.setIndeterminate(false);
            // 设置ProgressDialog 是否可以按退回按键取消
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            String app_id = getIntent().getStringExtra("app_id");
            ApkLaunchUtils.openPackage(WebActivity.this, "com.bxvip.app.dafa02");
            if (checkPackInfo("com.bxvip.app.dafa02")) {
            } else {
                //TODO  下载操作
                mPresenter.downApk(WebActivity.this, apkUrl);
            }
            webBg.setBackground(getResources().getDrawable(R.mipmap.new_bg));
        } else {
//            webView.getSettings().setJavaScriptEnabled(true);//支持js
            setWebView(mWebView, apkUrl);
//            mWebView.loadUrl(apkUrl);
        }

    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setWebView(WebView mWebView, String apkUrl) {
        WebSettings webSettings = mWebView.getSettings();
        if (webSettings == null) return;
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(false);
        webSettings.setDatabasePath(getDatabasePath("html").getPath());
        webSettings.setSavePassword(false);
        webSettings.setSupportZoom(true);
        mWebView.setFocusable(true);
        mWebView.requestFocus();

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.setFocusable(true);
                view.requestFocus();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                Log.e("shouldInterceptRequest", "=" + request.getUrl());
                return super.shouldInterceptRequest(view, request);
            }
        });
        mWebView.loadUrl(apkUrl);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.cancel();
//                outFileStream(message);
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }


            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }
        });


    }

    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static void startActivity(Context context, String url, String appId) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("app_id", appId);
        context.startActivity(intent);
    }

    @Override
    public void showUpdate(String version) {

    }

    @Override
    public void showProgress(int progress) {
        Log.e("zxz", "进度条" + progress);
        if (!progressDialog.isShowing()) {
            progressDialog.show();

        }
        progressDialog.setProgress(progress);
    }

    @Override
    public void showFail(String msg) {

    }

    @Override
    public void showComplete(File file) {
        try {
            String authority = getApplicationContext().getPackageName() + ".fileProvider";
            Uri fileUri = FileProvider.getUriForFile(this, authority, file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //7.0以上需要添加临时读取权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            } else {
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }

            startActivity(intent);

            //弹出安装窗口把原程序关闭。
            //避免安装完毕点击打开时没反应
            killProcess(Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
