package com.skill.room;

/**
 * Created by eviewlake on 2018/6/29.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sam on 2018/6/29.
 */

public class SetPassword extends AppCompatActivity implements View.OnClickListener{

    String mobilenumber;
    String yanzhenma;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpassword);

        getSupportActionBar().hide();



        Button button_nextstop = (Button) findViewById(R.id.button_nextstep);

        button_nextstop.setOnClickListener(this);


        if(getIntent().getExtras() !=null) {
            mobilenumber = getIntent().getExtras().getString("mobilenumber");
            yanzhenma =  getIntent().getExtras().getString("yanzhenma");

            System.out.println("yanzhenma pass:" + yanzhenma);

        }



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_nextstep:
                nextstep();
                break;

        }
    }


    private void nextstep(){

        //todo
        EditText editText_password =(EditText) findViewById(R.id.editText_password);
        String password = editText_password.getText().toString();

        //httpclient create new user.


        OkHttpClient client = new OkHttpClient();

        JSONObject meta = new JSONObject();
        try {
            meta.put("password", password);
            meta.put("userid", mobilenumber);
            meta.put("yanzhenma", yanzhenma);


        }catch (Exception ee) {ee.printStackTrace();}


        MediaType media = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(media, meta.toString());
        Request request = new Request.Builder()
                .url("http://www.gkt6.com/createuser")
                .post(formBody)//传递POST请求体, 同时决定用POST
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(" sendsms failed:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {  //回调的方法执行在子线程。
                        //创建用户成功， 返回loginTokenSession,  之间进入啦。 启动lessonView
                        String loginSessionToken = "";
                       JSONObject result=null ;
                    try {
                              result = new JSONObject(response.body().string());
                             loginSessionToken = result.getString("loginSessionToken");

                        }catch (Exception ee) {ee.printStackTrace();}

                        System.out.println("loginSessionToken" + loginSessionToken);

                        if(loginSessionToken.length() >0) {
                            Intent i = new Intent(SetPassword.this, LessonActivity.class);
                            i.putExtra("loginSessionToken", loginSessionToken);


                            RoomApplication.loginUserId = mobilenumber;
                            RoomApplication.loginRole = "0";  //缺省自己注册的都是学生啊。。。。 校长和老师的角色，要重新设定，需要流程。
                            RoomApplication.loginSessionToken = loginSessionToken;

                            startActivity(i);

                        }else {  //http 创建用户失败了。 估计是该手机号码已经存在， 要在GUI 上提示。

                            boolean userexsitalready = false;

                            try {
                                userexsitalready = result.getBoolean("userexsitalready");
                            }catch (Exception ee) {ee.printStackTrace();}


                            if(userexsitalready){
                                //todo, 提示给用户。。




                            }



                        }

                }else
                    System.out.println("response.isSuccessful() failed:" + response.isSuccessful());

            }
        });






    }

}