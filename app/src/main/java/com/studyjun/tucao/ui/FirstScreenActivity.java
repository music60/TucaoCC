package com.studyjun.tucao.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.studyjun.tucao.R;


/**
 * 第一启动页
 */
public class FirstScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView mImageView= new ImageView(this);
        mImageView.setImageResource(R.drawable.app_start);
        setContentView(mImageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoHome(); //2.5秒后跳转到主页
            }
        },800);
    }

    void getShowImg(){

    }

    /**
     * 跳转到主页
     */
    void gotoHome(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}
