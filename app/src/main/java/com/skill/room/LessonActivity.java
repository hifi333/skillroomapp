package com.skill.room;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.skill.voice_vedio.ConstantApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 * Created by eviewlake on 2018/5/25.
 */

public class LessonActivity extends AppCompatActivity {

    private final static Logger log = LoggerFactory.getLogger(LessonActivity.class);

    WebView lesstableView = null;
    WebView gtkView = null;
    WebView web3 = null;
    WebView selfview = null;


    String  loginSessionToken="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("--_______________________________-------LessonActivity--  OnCreated....");

        loginSessionToken = getIntent().getExtras().getString("loginSessionToken");


        getSupportActionBar().hide();
        setContentView(R.layout.lesson);

        FrameLayout samwebViewframeContainer = (FrameLayout)this.findViewById(R.id.webviewFrameContainer);

        SamWebviewClient samWebviewClient1 = new SamWebviewClient();
        FrameLayout.LayoutParams webviewLayout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT,1);


        lesstableView = new WebView(this.getApplicationContext());


        //注册roomActivity 的本地对象给lessionh5, 当选中课堂后, 回调回来, 进入相应的roomActivity, 必须在loadURL之前啊.
        System.out.println("---------注册JsCallbackObject 给Lessonwebview: as " + "skillroom");
        lesstableView.addJavascriptInterface(new JsCallbackObject(this), "skillroom");//JsCallbackObject类的一个实例,映射到js的skillroom对象, 在js的方法里就可以直接用了.


        lesstableView.setLayoutParams(webviewLayout);
        lesstableView.getSettings().setJavaScriptEnabled(true);
        lesstableView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        lesstableView.loadUrl("http://122.152.210.96/lessionh5.html");
        samwebViewframeContainer.addView(lesstableView);

        lesstableView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){

                //handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();  //接受证书, 这样https 都能打开了.
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                System.out.println("----------lessionh5.html load ok.");

                lesstableView.loadUrl("javascript:hi()");

                System.out.println("---------js hi ok");


                //调用js 方法
                lesstableView.loadUrl("javascript:tobeCalledfromLoginActivity('" +loginSessionToken + "')");

            }

        });





        gtkView = new WebView(this.getApplicationContext());
        gtkView.setWebViewClient(samWebviewClient1);
        gtkView.setLayoutParams(webviewLayout);
        gtkView.loadUrl("http://www.youdao.com");
        samwebViewframeContainer.addView(gtkView);


        web3 = new WebView(this.getApplicationContext());
        web3.setWebViewClient(samWebviewClient1);
        web3.setLayoutParams(webviewLayout);
        web3.loadUrl("http://www.qq.com");
        samwebViewframeContainer.addView(web3);


        selfview = new WebView(this.getApplicationContext());
        selfview.setWebViewClient(samWebviewClient1);
        selfview.setLayoutParams(webviewLayout);
        selfview.loadUrl("http://www.ifeng.com");
        samwebViewframeContainer.addView(selfview);



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String  samdatafromRoomActivity= data.getExtras().getString("data");

            System.out.println("class over, get result:" + samdatafromRoomActivity);
            //TODO
        }

    }



    public void onClick_self(View view){

        this.selfview.bringToFront();

    }



    public void onClickimageButton_lessontable(View view){

        this.lesstableView.bringToFront();

    }


    public void onClick_imageButton_gkt(View view){

        this.gtkView.bringToFront();

    }

    public void onClick_imageButton_view3(View view){

        this.web3.bringToFront();

    }







    class SamWebviewClient extends  WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }





        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){

            //handler.cancel(); 默认的处理方式，WebView变成空白页
            handler.proceed();  //接受证书, 这样https 都能打开了.
        }




    }




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        System.out.println("--_______________________________-------LessonActivity--  PostCreated....");


        final WebView  samwebview = new WebView(this.getApplicationContext());


        samwebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){

                //handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();  //接受证书, 这样https 都能打开了.
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

//                System.out.println("----------哈啊哈, 终于load 成功了.");
//
//                samwebview.loadUrl("javascript:hi()");
//
//
//                String loginUserId = "shm3";
//                String loginSessionToken = "Sshm3tempsessiontoken1526995290285";
//                String loginRole = "0";
//                String loginClassName = "alessioid001a";
//                String workmodel = "1";
//
//                samwebview.loadUrl("javascript:appcallthis('"
//                        +loginUserId + "','"
//                        +loginSessionToken + "','"
//                        +loginRole + "','"
//                        +loginClassName + "','"
//                        +workmodel
//                        + "')");
//
//                System.out.println("-------------- to call js method ..ok");
//


            }

      });


        //注册roomActivity 的本地对象给roomwhiteboard, 必须在loadURL之前啊.
        System.out.println("---------注册JsCallbackObject 给Lessonwebview: as " + "skillroom");
        samwebview.addJavascriptInterface(new JsCallbackObject(this), "skillroom");//JsCallbackObject类的一个实例,映射到js的skillroom对象, 在js的方法里就可以直接用了.


        samwebview.getSettings().setJavaScriptEnabled(true);
        samwebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        samwebview.loadUrl("http://122.152.210.96/index_app.html");

//        samwebview.loadUrl("http://www.yahoo.com");

        ((RoomApplication)this.getApplication()).samRoomWebView = samwebview;

        System.out.println("-------------- cached webiew ok");


        //请求权限啊...
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                boolean checkPermissionResult = checkSelfPermissions();

                if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
                    // so far we do not use OnRequestPermissionsResultCallback
                }
            }
        }, 500);





    }



    private boolean checkSelfPermissions() {
        return checkSelfPermission(Manifest.permission.RECORD_AUDIO, ConstantApp.PERMISSION_REQ_ID_RECORD_AUDIO) &&
                checkSelfPermission(Manifest.permission.CAMERA, ConstantApp.PERMISSION_REQ_ID_CAMERA) &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, ConstantApp.PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        log.debug("checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }

        if (Manifest.permission.CAMERA.equals(permission)) {
            ((RoomApplication) getApplication()).initWorkerThread();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        log.debug("onRequestPermissionsResult " + requestCode + " " + Arrays.toString(permissions) + " " + Arrays.toString(grantResults));
        switch (requestCode) {
            case ConstantApp.PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, ConstantApp.PERMISSION_REQ_ID_CAMERA);
                } else {
                    finish();
                }
                break;
            }
            case ConstantApp.PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, ConstantApp.PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
                    ((RoomApplication) getApplication()).initWorkerThread();
                } else {
                    finish();
                }
                break;
            }
            case ConstantApp.PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    finish();
                }
                break;
            }
        }
    }






}
