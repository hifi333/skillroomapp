package com.skill.room;

import android.app.Application;
import android.support.annotation.WorkerThread;
import android.webkit.WebView;

import com.skill.voice_vedio.LocalEngineWorkerThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by eviewlake on 2018/5/21.
 */

public class RoomApplication extends Application {

    private final static Logger log = LoggerFactory.getLogger(RoomApplication.class);

    public WebView samRoomWebView = null;


    private LocalEngineWorkerThread mWorkerThread;

    public synchronized void initWorkerThread() {
        if (mWorkerThread == null) {
            mWorkerThread = new LocalEngineWorkerThread(getApplicationContext());
            mWorkerThread.start();

            log.debug("initWorkerThread ..ok");
            mWorkerThread.waitForReady();
        }
    }

    public synchronized LocalEngineWorkerThread getWorkerThread() {
        return mWorkerThread;
    }

    public synchronized void deInitWorkerThread() {
        mWorkerThread.exit();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWorkerThread = null;
    }

}
