package com.skill.room;

        import android.app.Application;
        import android.content.Intent;
        import android.webkit.JavascriptInterface;

        import java.util.Date;

        import static com.skill.room.RoomApplication.loginRole;
        import static com.skill.room.RoomApplication.loginSessionToken;
        import static com.skill.room.RoomApplication.loginUserId;
        import static com.skill.room.RoomApplication.loginClassName;
        import static com.skill.room.RoomApplication.workmodel;

/**
 * Created by eviewlake on 2018/5/29.
 */

public class JsCallbackObject_Room extends Object {

    AppLoginActivity parent =null;

    public JsCallbackObject_Room(AppLoginActivity parent) {
        super();

        this.parent = parent;

    }


    @JavascriptInterface
    public void joinVideo(final String channel, final String uid) {

        System.out.println("JS调用了AndroidAPP: joinVedio:" + channel + "," + uid);

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // refresh ui 的操作代码
                RoomApplication.samRoomActivity.onclick_leaveroom(channel,uid);
            }
        });
    }



    @JavascriptInterface
    public void muteSpeaker() {

        System.out.println("JS调用了AndroidAPP: muteSpeaker" );

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // refresh ui 的操作代码
                RoomApplication.samRoomActivity.onclick_mutevoice();
            }
        });
    }


    @JavascriptInterface
    public void mutecam() {

        System.out.println("JS调用了AndroidAPP: mutecam");

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // refresh ui 的操作代码
                RoomApplication.samRoomActivity.onclick_mutecamara();
            }
        });
    }

    @JavascriptInterface
    public void hidevideowin() {

        System.out.println("JS调用了AndroidAPP: hidevideowin");

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // refresh ui 的操作代码
                RoomApplication.samRoomActivity.onclick_hidevideowin();
            }
        });
    }

    @JavascriptInterface
    public void quiteclassroom() {

        System.out.println("JS调用了AndroidAPP: hidevideowin");

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // refresh ui 的操作代码
                RoomApplication.samRoomActivity.onclick_quiteclassroom();
            }
        });
    }




}
