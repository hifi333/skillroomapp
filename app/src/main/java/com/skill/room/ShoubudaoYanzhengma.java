package com.skill.room;

/**
 * Created by eviewlake on 2018/6/29.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by sam on 2018/6/29.
 */

public class ShoubudaoYanzhengma extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoubudaoyanzhengma);
        getSupportActionBar().hide();


//        Button button_nextstop = (Button) findViewById(R.id.button_nextstep);
//
//        button_nextstop.setOnClickListener(this);
//
//        TextView welcome =(TextView) findViewById(R.id.textView_welcome);
//        TextView  mobile =(TextView) findViewById(R.id.textView_mobile);
//





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