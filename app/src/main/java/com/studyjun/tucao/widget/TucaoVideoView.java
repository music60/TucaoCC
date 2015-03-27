package com.studyjun.tucao.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.MediaController;

import java.util.List;

import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.SimpleMediaPlayer;
import tv.danmaku.ijk.media.widget.VideoView;

/**
 * Created by hn on 2015/2/9.
 *
 * @TODO
 */
public class TucaoVideoView extends VideoView{

    private static String TAG="TucaoVideoView";
    private List<Uri> mUris = null;
    private int currentUriPosition=0;


    public TucaoVideoView(Context context) {
        super(context);
    }

    public TucaoVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TucaoVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setVideoURIs(List<Uri> uris){
        mUris=uris;
    }

    @Override
    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener l) {
        super.setOnCompletionListener(l);
    }


}
