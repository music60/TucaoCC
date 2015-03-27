/*
 * Copyright (C) 2013 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.studyjun.tucao.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studyjun.tucao.R;
import com.studyjun.tucao.api.Api;
import com.studyjun.tucao.bean.VideoPlay;
import com.studyjun.tucao.manager.DeviceManager;
import com.studyjun.tucao.net.UTF8StringRequest;
import com.studyjun.tucao.util.UI;
import com.studyjun.tucao.util.VideoUtil;
import com.studyjun.tucao.widget.Log;
import com.studyjun.tucao.widget.TucaoMediaPlayerControl;
import com.studyjun.tucao.widget.TucaoVideoPlayerView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import master.flame.danmaku.ui.widget.DanmakuSurfaceView;
import master.flame.danmaku.controller.DrawHandler.Callback;

import static com.android.volley.Response.Listener;


/**
 * 视频播放
 */
public class VideoPlayerActivity extends Activity {
    private final static String TAG = "VideoPlayerActivity";

    private TucaoVideoPlayerView mVideoView;

    private TucaoMediaPlayerControl mMediaController;
    //    private String mVideoPath;
    private RequestQueue mQueue;
    private StringRequest mUrlRequest;
    private StringRequest mDanmuRequest;
    private String vid;
    private String type;
    private String hid;
    private int part;
    private Handler handler;
    private DanmakuSurfaceView mDanmakuView;
    private TextView mLoaddingTextView;
    private StringBuffer mLoaddingString;
    private FrameLayout maskingControl;


    private static final int URL_RESPONSE_SUCCESS = 200;//返回URL成功标志
    private static final int URL_RESPONSE_ERROR = 201; //返回URL失败
    private static final int DANMU_RESPONSE_SUCCESS = 300; //返回弹幕成功
    private static final int DANMU_RESPONSE_ERROR = 301; //返回弹幕失败

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        init();
        urlRequest();
        danMuRequest();
        handler();

    }

    /**
     * 事件处理
     */
    private void handler() {
        handler = new Handler() {


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case URL_RESPONSE_SUCCESS://返回URL成功标志
                        String urldata = (String) msg.obj;
                        play(urldata);
                        mLoaddingString.append("视频地址请求成功\n");

                        break;
                    case URL_RESPONSE_ERROR://返回URL失败
                        mLoaddingString.append("视频解析失败...\n");
                        UI.toast(getApplicationContext(), "视频解析失败" + msg.obj.toString());
                        break;
                    case DANMU_RESPONSE_SUCCESS: //返回弹幕成功
                        String danmudata = (String) msg.obj;
                        mLoaddingString.append("吐槽能量填充成功\n");
//                        playDanmu(danmudata);

                        break;
                    case DANMU_RESPONSE_ERROR://返回弹幕失败
                        mLoaddingString.append("吐槽能量填充失败...\n");
                        UI.toast(getApplicationContext(), "吐槽能量填充失败");
                        break;
                }
                mLoaddingTextView.setText(mLoaddingString);
            }

        };

    }

    /**
     * 播放地址请求
     */
    void urlRequest() {
        Log.log(TAG, "urlRequest 播放地址请求");

        String realUrl;
        if ("qq".equalsIgnoreCase(type)) {
            realUrl = String.format(Api.PLAY_URL_DEFAULT, type, vid);
        } else {
            realUrl = String.format(Api.PLAY_URL_OTHER, type, vid);
        }

        Log.logDisk(TAG, realUrl);

        mLoaddingString.append("视频地址请求中....\n");
        mLoaddingTextView.setText(mLoaddingString);

        mUrlRequest = new UTF8StringRequest(Request.Method.GET, realUrl, new Listener<String>() {



            @Override
            public void onResponse(String response) {
                sendMsg(response, URL_RESPONSE_SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sendMsg(error, URL_RESPONSE_ERROR);
            }


        });

        mQueue.add(mUrlRequest);
    }

    /**
     * 发送消息
     *
     * @param data  数据
     * @param state 标志
     */
    void sendMsg(Object data, int state) {
        Message msg = Message.obtain();
        msg.what = state;
        msg.obj = data;
        handler.sendMessage(msg);
    }

    /**
     * 播放
     *
     * @param jsondata
     */
    void play(String jsondata) {

        VideoPlay videoPlay = VideoUtil.parseUrl(jsondata);


        if (videoPlay != null && videoPlay.getDurls() != null && videoPlay.getDurls().size() > 0) {

            mMediaController.setDuration(videoPlay.timelength);
            UI.toastLongTime(getApplicationContext(), "" + videoPlay.timelength + "-" + videoPlay.getDurls().get(0).getUrl());

            mVideoView.setUserAgent(DeviceManager.getCurrentUserAgent());

            UI.toastLongTime(getApplicationContext(), (DeviceManager.getCurrentUserAgent()));
            mVideoView.setVideoPlay(videoPlay);
        }

        mVideoView.requestFocus();
//        mVideoView.start();

    }

//    private void test(VideoPlay videoPlay) {
//        for (Segment s : videoPlay.getDurls()) {
////            s.setUrl("http://bk-client.oohope.com/benko/videos/110/1/20123220153219.mp4");
//          s.setUrl("http://61.153.87.231/f4v/12/225890412.h264_4_seg_1.04001502015507560AF4B82ED126A4BE337099-B7F2-E770-EBE7-000293626302.f4v?key=7feb1f5fc76beebfc010e95508f0f10040536d8288&playtype=15&tk=72867131478377064810558866&brt=15&bc=0&xid=04001502015507560AF4B82ED126A4BE337099-B7F2-E770-EBE7-000293626302&nt=0&nw=0&bs=0&ispid=41&rc=200&inf=12&si=un&npc=1628&pp=0&ul=0&mt=0&sid=10000&pc=1&cip=183.60.137.58&id=tudou&hf=0&hd=2&sta=0&ssid=0&itemid=293626302&fi=0&sz=100105660");
//
//
//
//        }
//    }

    /**
     * 弹幕请求
     */
    void danMuRequest() {
        String realUrl = String.format(Api.BARRAGE, hid, part);
        mLoaddingString.append("吐槽能量开始蓄力...\n");
        mLoaddingTextView.setText(mLoaddingString);
//        test();
        mDanmuRequest = new UTF8StringRequest(Request.Method.GET, realUrl, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                sendMsg(response, DANMU_RESPONSE_SUCCESS);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                sendMsg(error, DANMU_RESPONSE_ERROR);
            }
        });
        mQueue.add(mDanmuRequest);
    }

    /**
     * 播放弹幕
     *
     * @param jsondata
     */
    private void playDanmu(String jsondata) {
        if (mDanmakuView != null) {

            InputStream inputStream = new ByteArrayInputStream(jsondata.getBytes());

            BaseDanmakuParser mParser = createParser(inputStream);

            mDanmakuView.setCallback(new Callback() {

                @Override
                public void updateTimer(DanmakuTimer timer) {

                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
            mDanmakuView.prepare(mParser);

            mDanmakuView.enableDanmakuDrawingCache(true);

        }
    }

    private String getKey(int vid, int poi) {
        String str = Integer.toHexString(0 - (vid + (0xA55AA5A5 ^ poi + 0))).replace("ffffffff00", "").replace("ffffffff0", "").replace("ffffffff", "");
        return "tucao" + str + ".cc";
    }




    void init() {
        mLoaddingString = new StringBuffer();
        mQueue = Volley.newRequestQueue(this);
        vid = getIntent().getStringExtra("vid");
        type = getIntent().getStringExtra("type");
        hid = getIntent().getStringExtra("hid");
        part = getIntent().getIntExtra("part", 0);

        mMediaController = new TucaoMediaPlayerControl(this);

        mDanmakuView = (DanmakuSurfaceView) findViewById(R.id.sv_danmaku);
        mLoaddingTextView = (TextView) findViewById(R.id.player_loading_text);

        maskingControl = (FrameLayout) findViewById(R.id.player_masking_control);

        mVideoView = (TucaoVideoPlayerView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);

        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }

        maskingControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaController.show();
            }
        });
    }


    /**
     * 弹幕解析器
     *
     * @param stream
     * @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }


        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    @Override
    protected void onDestroy() {
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();

        }
        super.onDestroy();
    }


}
