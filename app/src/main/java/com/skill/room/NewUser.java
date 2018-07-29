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

/**
 * Created by sam on 2018/6/29.
 */

public class NewUser extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);

        getSupportActionBar().hide();


        Button button_nextstop = (Button) findViewById(R.id.button_nextstep);

        button_nextstop.setOnClickListener(this);



        if(getIntent().getExtras() !=null) {
             String tip_for_forgetPasword = getIntent().getExtras().getString("tip");

             if(tip_for_forgetPasword ==null)
                 tip_for_forgetPasword = "新用户注册";

            ( (TextView) findViewById(R.id.textView_welcome)).setText(tip_for_forgetPasword);



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

        EditText edit_mobile =(EditText) findViewById(R.id.editText_mobile);

        String newUserMobile = edit_mobile.getText().toString();
        System.out.println("new user：" + newUserMobile);

        try {
            Long.parseLong(newUserMobile);


            Intent i = new Intent(NewUser.this, Yanzhengma.class);
            i.putExtra("mobilenumber", newUserMobile);
            startActivity(i);



        }catch (Exception ee) {
            ee.printStackTrace();

            //todo
            //error prompt to gui
        }



    }

}