package com.skill.room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
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

        ImageView loginimageivewLogo = (ImageView)this.findViewById(R.id.loginimageivewLogo);

        loginimageivewLogo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        //loginimageivewLogo.setImageResource(R.drawable.gkttuzi);




    }



    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }

    public void onclick_newUser(View view){


        System.out.println("new user.");


        Intent i = new Intent(AppLoginActivity.this, NewUser.class);

        startActivity(i);




    }

    public void onclick_forgetpassword(View view){


        Intent i = new Intent(AppLoginActivity.this, NewUser.class);

        i.putExtra("mobilenumber", "13958003839");
        i.putExtra("tip", "请输入手机号");

        startActivity(i);



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

                        String loginSessionToken = "";

                        try {
                            JSONObject a = new JSONObject(response.body().string());

                            loginSessionToken = a.getString("loginSessionToken");

                        }catch (Exception ee) {ee.printStackTrace();}
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





    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        System.out.println("--_______________________________AooLoginAcitivity PostCreated....");


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

                System.out.println("native:--哈啊哈, 终于load bundle_app.js for room 成功了.");


            }

        });


        //注册roomActivity 的本地对象给roomwhiteboard, 必须在loadURL之前啊.
        System.out.println("native:---注册JsCallbackObject samwebview-roomview: as " + "skillroom");
        samwebview.addJavascriptInterface(new JsCallbackObject_Room(this), "skillroom");//JsCallbackObject类的一个实例,映射到js的skillroom对象, 在js的方法里就可以直接用了.


        samwebview.getSettings().setJavaScriptEnabled(true);
        samwebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        samwebview.loadUrl("http://122.152.210.96/index_app.html");
        RoomApplication.samRoomWebView = samwebview;






    }


}
