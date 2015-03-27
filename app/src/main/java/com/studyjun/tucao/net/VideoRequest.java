package com.studyjun.tucao.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studyjun.tucao.api.Api;
import com.studyjun.tucao.util.UI;

/**
 * Created by hn on 2015/1/26.
 *
 * @TODO
 */
public class VideoRequest {

    public void getPlayUrl(RequestQueue queue,String type,String vid,final Context context){
       String url = String.format(Api.PLAY_URL_OTHER,type,vid);
        StringRequest playUrlRequest = new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UI.toast(context,response);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(playUrlRequest);
    }
}
