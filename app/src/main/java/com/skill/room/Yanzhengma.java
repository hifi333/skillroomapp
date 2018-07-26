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
import android.widget.TextView;

/**
 * Created by sam on 2018/6/29.
 */

public class Yanzhengma extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanzhengma);

        getSupportActionBar().hide();




//        Button button_nextstop = (Button) findViewById(R.id.button_nextstep);
//
//        button_nextstop.setOnClickListener(this);
//
//        TextView welcome =(TextView) findViewById(R.id.textView_welcome);
//        TextView  mobile =(TextView) findViewById(R.id.textView_mobile);
//





    }

    public void onclick_shoubudaoyanzhengma(View view){


        Intent i = new Intent(Yanzhengma.this, ShoubudaoYanzhengma.class);

        startActivity(i);




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


    }

}