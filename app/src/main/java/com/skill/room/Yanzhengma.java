package com.skill.room;

/**
 * Created by eviewlake on 2018/6/29.
 */


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
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

public class Yanzhengma extends AppCompatActivity implements View.OnClickListener{


     String mobilenumber;
     String yanzhenmayougot;


    String guimessage ="";
    final Handler cwjHandler = new Handler();

    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateUI();
        }
    };

    private void updateUI() {

        ((TextView)findViewById(R.id.textView_mobile2)).setText(guimessage);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanzhengma);

        getSupportActionBar().hide();


        if(getIntent().getExtras() !=null) {
            final String mobilenumber = getIntent().getExtras().getString("mobilenumber");

            this.mobilenumber = mobilenumber;

            //togo to call httpclient to send sms

            OkHttpClient client = new OkHttpClient();

            JSONObject meta = new JSONObject();
            try {
                meta.put("mobilenumber", mobilenumber);
            }catch (Exception ee) {ee.printStackTrace();}


            MediaType media = MediaType.parse("application/json; charset=utf-8");
            RequestBody formBody = RequestBody.create(media, meta.toString());
            Request request = new Request.Builder()
                    .url("https://gkt6.com/sendsms")
                    .post(formBody)//传递POST请求体, 同时决定用POST
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(" sendsms failed:" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {//回调的方法执行在子线程。
                        //服务器异步发送， 这里http resposne返回没有意思。

                         guimessage = "验证码已经发送至手机:" + mobilenumber;

                        cwjHandler.post(mUpdateResults); //高速UI线程可以更新结果了


                    }else
                        System.out.println("response.isSuccessful() failed:" + response.isSuccessful());

                }
            });


        }





        TextView textView_solutions =  (TextView)findViewById(R.id.textView_solutions);
        textView_solutions.setOnClickListener(this);




        final EditText editText_Yanzhengma_1 =  (EditText)findViewById(R.id.editText_Yanzhengma_1);
        final EditText editText_Yanzhengma_2 =  (EditText)findViewById(R.id.editText_Yanzhengma_2);
        final EditText editText_Yanzhengma_3 =  (EditText)findViewById(R.id.editText_Yanzhengma_3);
        final EditText editText_Yanzhengma_4 =  (EditText)findViewById(R.id.editText_Yanzhengma_4);



        editText_Yanzhengma_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){

                    System.out.println("onTextChanged1:" + s.toString());
                    if(getYanzhenma().length()==4)
                        nextstep();
                    else
                        editText_Yanzhengma_2.requestFocus();

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editText_Yanzhengma_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){

                    System.out.println("onTextChanged2:" + s.toString());
                    if(getYanzhenma().length()==4)
                        nextstep();
                    else
                        editText_Yanzhengma_3.requestFocus();

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText_Yanzhengma_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){

                    System.out.println("onTextChanged3:" + s.toString());
                    if(getYanzhenma().length()==4)
                        nextstep();
                    else
                        editText_Yanzhengma_4.requestFocus();


                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText_Yanzhengma_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){

                    System.out.println("onTextChanged4:" + s.toString());

                    if(getYanzhenma().length()==4)
                         nextstep();



                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void onclick_shoubudaoyanzhengma(View view){


        Intent i = new Intent(Yanzhengma.this, ShoubudaoYanzhengma.class);

        startActivity(i);




    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.textView_solutions:
                //todo

                Intent i = new Intent(Yanzhengma.this, ShoubudaoYanzhengma.class);
                startActivity(i);

                break;
        }
    }


    private String getYanzhenma(){
        String Yanzhengma_1 =  ((EditText)findViewById(R.id.editText_Yanzhengma_1)).getText().toString();
        String Yanzhengma_2 =  ((EditText)findViewById(R.id.editText_Yanzhengma_2)).getText().toString();
        String Yanzhengma_3 =  ((EditText)findViewById(R.id.editText_Yanzhengma_3)).getText().toString();
        String Yanzhengma_4 =  ((EditText)findViewById(R.id.editText_Yanzhengma_4)).getText().toString();


        String  yanzhenmayougot = Yanzhengma_1 + Yanzhengma_2 + Yanzhengma_3 + Yanzhengma_4;

        return yanzhenmayougot;
    }

    private void nextstep(){
        //todo


        String  yougot = getYanzhenma();

        System.out.println("yanzhenmayougot:" + yougot);

        this.yanzhenmayougot = yougot;

        //httpclient to verify this yanzhenma
        OkHttpClient client = new OkHttpClient();

        JSONObject meta = new JSONObject();
        try {
            meta.put("yanzhenma", this.yanzhenmayougot);
            meta.put("mobilenumber", this.mobilenumber);

        }catch (Exception ee) {ee.printStackTrace();}


        MediaType media = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(media, meta.toString());
        Request request = new Request.Builder()
                .url("https://gkt6.com/verifysms")
                .post(formBody)//传递POST请求体, 同时决定用POST
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(" sendsms failed:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//回调的方法执行在子线程。


                    try {
                        JSONObject a = new JSONObject(response.body().string());
                        boolean status = a.getBoolean("status");

                        if(status){  //验证成功

                            Intent i = new Intent(Yanzhengma.this, SetPassword.class);
                            i.putExtra("mobilenumber", mobilenumber);
                            i.putExtra("yanzhenma", yanzhenmayougot);

                            startActivity(i);


                        }else //验证失败， 看看短信， 是不是输入错误。
                        {
                            TextView aa =  (TextView)findViewById(R.id.textView_mobile2);
                           // aa.setText("验证码已经发送至手机:" + "验证码输入错误。");
                        }
                    }catch (Exception ee) {ee.printStackTrace();}




                }else
                    System.out.println("response.isSuccessful() failed:" + response.isSuccessful());

            }
        });






    }

}