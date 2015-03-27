package com.stduyjun.tucao;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private RequestQueue mQueue;
    private StringRequest mUrlRequest;
    private StringRequest mDanmuRequest;
    public ApplicationTest() {
        super(Application.class);


        String url ="http://www.tucao.cc/index.php?m=mukio&c=index&a=init&playerID=11-4039950-1-0";
        mDanmuRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
//                DanmakuGlobalConfig.DEFAULT.setDanmakuStyle(DanmakuGlobalConfig.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(true);
                  System.out.print(response);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(mDanmuRequest);

    }
}