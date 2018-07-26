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

        String mobilenumber = "";
        String forgetPasswordtip = "";

        if(getIntent().getExtras() !=null) {
            mobilenumber = getIntent().getExtras().getString("mobilenumber");
            forgetPasswordtip = getIntent().getExtras().getString("tip");
        }


        Button button_nextstop = (Button) findViewById(R.id.button_nextstep);

        button_nextstop.setOnClickListener(this);

        TextView  welcome =(TextView) findViewById(R.id.textView_welcome);
        TextView  mobiletip =(TextView) findViewById(R.id.textView_solutions);

        EditText edit_mobile =(EditText) findViewById(R.id.editText_mobile);



        if(mobilenumber.length() >0) edit_mobile.setText(mobilenumber);
        if(forgetPasswordtip.length() >0) welcome.setText(forgetPasswordtip);




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

        System.out.println("new user.");


        Intent i = new Intent(NewUser.this, Yanzhengma.class);

        startActivity(i);


    }

}