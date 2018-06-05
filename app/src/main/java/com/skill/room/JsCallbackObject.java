package com.skill.room;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import static com.skill.room.RoomApplication.loginRole;
import static com.skill.room.RoomApplication.loginSessionToken;
import static com.skill.room.RoomApplication.loginUserId;
import static com.skill.room.RoomApplication.loginClassName;
import static com.skill.room.RoomApplication.workmodel;

/**
 * Created by eviewlake on 2018/5/29.
 */

public class JsCallbackObject extends Object {

    LessonActivity parent =null;

    public JsCallbackObject( LessonActivity parent) {
        super();

        this.parent = parent;

    }

    @JavascriptInterface
    public void joinRoom(final  String  loginClassName, final String workmodel) {
        System.out.println("JS调用了Android的hello方法:" + loginClassName + " workmode:"  + workmodel);

//        String loginClassName = "alessioid001a";
//                String workmodel = "1";


        RoomApplication.loginClassName = loginClassName;
        RoomApplication.workmodel =workmodel;


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


                RoomApplication.samRoomWebView.loadUrl("javascript:appcallthis('"
                        +loginUserId + "','"
                        +loginSessionToken + "','"
                        +loginRole + "','"
                        +loginClassName + "','"
                        +workmodel
                        + "')");

                System.out.println("-------------- to call whiteboards js api..ok");



                //bring up room Activity

                System.out.println("-----start room Activiy by Singlemodel Aciton: com.skill.room.singleroom");

                Intent i = new Intent(parent,RoomActivity.class);

                //i.setAction("com.skill.room.singleroom");

                parent.startActivity(i);

            }
        });






    }

}
