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
import android.widget.EditText;
import android.widget.TextView;

import com.skill.voice_vedio.ConstantApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    public void onclick_login(View view){

        final String userid =((EditText)this.findViewById(R.id.editText_username)).getText().toString();
        String password =((EditText)this.findViewById(R.id.editText_password)).getText().toString();

        if(userid.length()>0) {  //检验合法

            OkHttpClient client = new OkHttpClient();



//            HttpsCustomTrust ct = new HttpsCustomTrust();

//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(chain -> {
//                        Request request = chain.request().newBuilder()
//                                .addHeader("X-CRM-Application-Id", RequestConstants.APPLICATION_ID)
//                                .addHeader("X-CRM-Version", RequestConstants.VERSION)
//                                .addHeader("Content-Type", RequestConstants.JSON_TYPE)
//                                .build();
//                        return chain.proceed(request);
//                    })
//                    .sslSocketFactory(ct.sslSocketFactory, ct.trustManager)
//                    .hostnameVerifier((s, sslSession) -> true)
//                    .build();



            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("userid", userid);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

//            FormBody.Builder formBody = new RequestBody().Builder();//创建表单请求体,  Content-Type=application/x-www-form-urlencoded
//            formBody.add("userId",userId);//传递键值对参数
//            formBody.add("password",password);//传递键值对参数

            Request request = new Request.Builder()
//                    .url("https://122.152.210.96:8443/login")
                    .url("http://122.152.210.96/login")
                    .post(formBody)//传递POST请求体, 同时决定用POST
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    //((EditText)findViewById(R.id.editText_username)).setText("login error");

                    System.out.println("login failed:" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {//回调的方法执行在子线程。

                        String loginSessionToken = response.body().string();

                        System.out.println("loginSessionToken" + loginSessionToken);

                        if(loginSessionToken.length() >0) {
                            Intent i = new Intent(AppLoginActivity.this, LessonActivity.class);
                            i.putExtra("loginSessionToken", loginSessionToken);


                            RoomApplication.loginUserId = userid;
                            RoomApplication.loginRole = "0";
                            RoomApplication.loginSessionToken = loginSessionToken;


                            startActivity(i);
                        }else
                        {
                            System.out.println( userid + " login没有拿到token, 请坚持用户名或密码是否正确.");

                        }

                    }else
                        System.out.println("response.isSuccessful() failed:" + response.isSuccessful());

                }
            });


        }



    }


}
