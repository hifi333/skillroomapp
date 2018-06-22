package com.skill.room;

import android.Manifest;
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
import android.view.SurfaceView;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.skill.room.RoomApplication;
import com.skill.voice_vedio.ConstantApp;
import com.skill.voice_vedio.LocalEngineWorkerThread;
import com.skill.voice_vedio.ServerEngineEventHandler_ActivityCallBackHander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * Created by sam on 2018/5/19.
 */

public class RoomActivity extends AppCompatActivity implements ServerEngineEventHandler_ActivityCallBackHander {
    private final static Logger log = LoggerFactory.getLogger(RoomActivity.class);

    String userId ="11";
    boolean isNowVedioEnable = true;
    boolean isNowJoinroom = false;
    boolean isNowVoiceEnanble = true;

    Hashtable<Integer, SurfaceView>  userVedioList = new Hashtable();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.room);
//
//
        WebView samwebview =  ((RoomApplication)this.getApplication()).samRoomWebView;


        FrameLayout samroommain = (FrameLayout) findViewById(R.id.samroommain);


        FrameLayout.LayoutParams ddd = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);



        samwebview.setLayoutParams(ddd);
        samroommain.addView(samwebview);  //cached webView 快速的显示出来了.



        findViewById(R.id.vedio_scrollView1).bringToFront();


        getLocalEngineWorkerThread().eventHandler().addEventHandler(this);

//        SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
//        getLocalEngineWorkerThread().getRtcEngine().setupLocalVideo(new VideoCanvas(surfaceV,
//                VideoCanvas.RENDER_MODE_HIDDEN, 0));
//        surfaceV.setZOrderOnTop(false);
//        surfaceV.setZOrderMediaOverlay(false);

        log.debug("engine create local vedio view");


//        LinearLayout kk = (LinearLayout) findViewById(R.id.samvedioviewfly);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                200,LinearLayout.LayoutParams.MATCH_PARENT);
//        surfaceV.setLayoutParams(layoutParams);
//
//        kk.addView(surfaceV);
//
//        userVedioList.put(0,surfaceV);

        log.debug("layout  this local vedio view");

        //点亮头本地像
//        getLocalEngineWorkerThread().getRtcEngine().startPreview();

        log.debug("engine start preview local vedio view");
//        getLocalEngineWorkerThread().getRtcEngine().stopPreview();


        //取消视频头像
//        getLocalEngineWorkerThread().getRtcEngine().disableVideo();
//        getLocalEngineWorkerThread().getRtcEngine().enableVideo();


        //决定加入房间, 浙江触发Engine 事件啊. 本地会触发, 在远程的同学的Engine 也会触发.
//         int usernumber = Integer.parseInt(this.userId);
//
//        getLocalEngineWorkerThread().getRtcEngine().joinChannel(null,getLocalEngineWorkerThread().getEngineConfig().mChannel, "skill",usernumber);
//        log.debug("engine join channel ");



        RoomApplication.samRoomActivity = this;
    }

    protected final LocalEngineWorkerThread getLocalEngineWorkerThread() {
        return ((RoomApplication) getApplication()).getWorkerThread();
    }



    //handler interface

    @Override
    public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {

        log.debug("我也加入 channel 啦."+ uid);
        runOnUiThread(new Runnable() {  //GUI click event 线程里不能直接干重的活啊, 再起一个新的线程去做, 用户就不会体感卡死了.
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }


                if(userVedioList.get(0)!=null)

                {
                    userVedioList.put(uid, userVedioList.get(0));
                }
                updateVedioBar();


            }
        });
    }





    @Override
    public void onLeaveChannel(){
        log.debug("我自己退出啦");
        runOnUiThread(new Runnable() {  //GUI click event 线程里不能直接干重的活啊, 再起一个新的线程去做, 用户就不会体感卡死了.
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                userVedioList.clear();  //我已经退出啦, 别人的视频我也看不到了, 关闭所有本地窗口.
                isNowJoinroom = false;

//
//                int usernumber = Integer.parseInt(userId);
//
//                userVedioList.remove(usernumber);
//
                updateVedioBar();


            }
        });
    }





    @Override
    public void onUserJoined(final int uid, int elapsed){
        log.debug("远程有client  join相同的channel 啦."+ uid);
//
        runOnUiThread(new Runnable() {  //GUI click event 线程里不能直接干重的活啊, 再起一个新的线程去做, 用户就不会体感卡死了.
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }


                //判断一下, 这个远程的uid 是否已经在本地有了view了. 如何有了, 就忽略吧.
//                if(userVedioList.get(uid) !=null)
//                    return;


                SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
                surfaceV.setZOrderOnTop(false);
                surfaceV.setZOrderMediaOverlay(false);

                getLocalEngineWorkerThread().getRtcEngine().setupRemoteVideo(new VideoCanvas(surfaceV, VideoCanvas.RENDER_MODE_HIDDEN, uid));

                userVedioList.put(uid,surfaceV);

                updateVedioBar();


            }
        });
    }

    synchronized private void  updateVedioBar(){



        //这时候, 这个surfaceV  view 已经有了远程client 的头像啦.  该把这个view 布局到哪里呢?
        LinearLayout kk = (LinearLayout) findViewById(R.id.samvedioviewfly);

        kk.removeAllViews();

        Iterator iterator = this.userVedioList.keySet().iterator();
        while (iterator.hasNext()) {

            int uid = (int)iterator.next();
            if(uid!=0)  //本地preview的
            {
                SurfaceView surfaceV = this.userVedioList.get(uid);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        200, LinearLayout.LayoutParams.MATCH_PARENT);
                surfaceV.setLayoutParams(layoutParams);

                kk.addView(surfaceV);
            }

        }
//        LinearLayout kk = (LinearLayout) findViewById(R.id.samvedioviewfly);
//        kk.removeView(remoteUser);


    }


    @Override
    public void onUserOffline(final int uid, int reason) {
        log.debug("远程有client  leave 的channel 啦."+ uid);

        runOnUiThread(new Runnable() {  //GUI click event 线程里不能直接干重的活啊, 再起一个新的线程去做, 用户就不会体感卡死了.
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }



                SurfaceView remoteUser =  userVedioList.get(uid);
                if(remoteUser!=null)
                {
                    userVedioList.remove(uid);

                    updateVedioBar();

                }
            }
        });
    }



    @Override
    public void onExtraCallback(int type, Object... data) {

    }

    public void onclick_leaveroom(String channel, String uid){
        //决定加入房间, 浙江触发Engine 事件啊. 本地会触发, 在远程的同学的Engine 也会触发.

        if(isNowJoinroom) {
//            getLocalEngineWorkerThread().getRtcEngine().disableVideo();
            getLocalEngineWorkerThread().getRtcEngine().leaveChannel();
            this.userVedioList.clear();  //我已经退出啦, 别人的视频我也看不到了, 关闭所有本地窗口.
            this.isNowJoinroom = false;
        }
        else {
//            getLocalEngineWorkerThread().getRtcEngine().enableVideo();

            SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
            getLocalEngineWorkerThread().getRtcEngine().setupLocalVideo(new VideoCanvas(surfaceV,
                    VideoCanvas.RENDER_MODE_HIDDEN, 0));
            surfaceV.setZOrderOnTop(false);
            surfaceV.setZOrderMediaOverlay(false);

            this.userVedioList.put(0,surfaceV);

            int usernumber = Integer.parseInt(uid);
            getLocalEngineWorkerThread().getRtcEngine().joinChannel(null,channel, "skill",usernumber);

            this.isNowJoinroom = true;

        }


    }



    public void onclick_mutecamara() {


        if(isNowVedioEnable) {
            getLocalEngineWorkerThread().getRtcEngine().disableVideo();

            this.isNowVedioEnable = false;
        }
        else {
            getLocalEngineWorkerThread().getRtcEngine().enableVideo();
            this.isNowVedioEnable = true;

        }


    }



    public void onclick_mutevoice() {


        if(isNowVoiceEnanble) {
            getLocalEngineWorkerThread().getRtcEngine().muteLocalAudioStream(true);

            this.isNowVoiceEnanble = false;
        }
        else {
            getLocalEngineWorkerThread().getRtcEngine().muteLocalAudioStream(false);
            this.isNowVoiceEnanble = true;

        }


    }



}
