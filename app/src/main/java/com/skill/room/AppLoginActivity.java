package com.skill.room;

import android.Manifest;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skill.voice_vedio.ConstantApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import io.agora.openvcall.R;

/**
 * Created by eviewlake on 2018/5/21.
 */

public class AppLoginActivity extends AppCompatActivity {

    private final static Logger log = LoggerFactory.getLogger(AppLoginActivity.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.applogin);



    }

    public void onclick_button(View view){

        Intent i = new Intent(AppLoginActivity.this, RoomActivity.class);
        i.putExtra("userId", "abc");

        startActivity(i);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

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

                System.out.println("----------哈啊哈, 终于load 成功了.");

                samwebview.loadUrl("javascript:hi()");


                String loginUserId = "shm3";
                String loginSessionToken = "Sshm3tempsessiontoken1526995290285";
                String loginRole = "0";
                String loginClassName = "alessioid001a";
                String workmodel = "1";

                samwebview.loadUrl("javascript:appcallthis('"
                        +loginUserId + "','"
                        +loginSessionToken + "','"
                        +loginRole + "','"
                        +loginClassName + "','"
                        +workmodel
                        + "')");

                System.out.println("-------------- to call js method ..ok");



            }

      });

        samwebview.getSettings().setJavaScriptEnabled(true);
        samwebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        samwebview.loadUrl("https://192.168.1.6:8443/index_app.html");

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
