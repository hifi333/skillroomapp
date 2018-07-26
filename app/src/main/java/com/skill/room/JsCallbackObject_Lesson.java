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

public class JsCallbackObject_Lesson extends Object {

    LessonActivity parent =null;

    public JsCallbackObject_Lesson(LessonActivity parent) {
        super();

        this.parent = parent;

    }

    @JavascriptInterface
    public void joinRoom(final  String  loginClassName1, final String workmodel1) {
        System.out.println("native: LessionH5 JS调用了Android的 joinRoom 方法:" + loginClassName1 + " workmode:" + workmodel1);

//        String loginClassName = "alessioid001a";
//                String workmodel = "1";


        RoomApplication.loginClassName = loginClassName1;
        RoomApplication.workmodel = workmodel1;


//
//                String loginUserId = "shm3";
//                String loginSessionToken = "Sshm3tempsessiontoken1526995290285";
//                String loginRole = "0";
//                String loginClassName = "alessioid001a";
//                String workmodel = "1";


        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // refresh ui 的操作代码

                String makefresh = "makefresh" + new Date().getTime();


                RoomApplication.samRoomWebView.loadUrl("javascript:tobecallfromnaive_joinroom('"
                        + loginUserId + "','"
                        + loginSessionToken + "','"
                        + loginRole + "','"
                        + loginClassName + "','"
                        + workmodel+ "','"
                        + makefresh
                        + "')");

                System.out.println("native:--- to call bundle_app.js tobecallfromnaive_joinroom()  ..ok: __________" + loginClassName);


                //bring up room Activity

                System.out.println("-----start room Activiy by Intent(parent, RoomActivity.class);");

                Intent i = new Intent(parent, RoomActivity.class);


                parent.startActivityForResult(i, 0);



                //i.setAction("com.skill.room.singleroom");

//                parent.startActivity(i);

            }
        });


    }




}
