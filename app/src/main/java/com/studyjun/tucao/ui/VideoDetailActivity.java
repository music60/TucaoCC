package com.studyjun.tucao.ui;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gc.materialdesign.views.ButtonRectangle;

import com.studyjun.tucao.R;
import com.studyjun.tucao.api.Api;
import com.studyjun.tucao.bean.Video;
import com.studyjun.tucao.bean.VideoDetail;
import com.studyjun.tucao.bean.VideoPlay;
import com.studyjun.tucao.db.LocalVideoDB;
import com.studyjun.tucao.manager.DeviceManager;
import com.studyjun.tucao.manager.VolleyManager;
import com.studyjun.tucao.net.UTF8StringRequest;
import com.studyjun.tucao.net.VideoRequest;
import com.studyjun.tucao.util.VideoUtil;
import com.studyjun.tucao.widget.DownloadChangeObserver;
import com.studyjun.tucao.widget.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class VideoDetailActivity extends ActionBarActivity implements View.OnClickListener {

    private NetworkImageView mPhoto;
    private TextView mTitle;
    private TextView mUpName;
    private TextView mPlayNum;
    private TextView mDanmuNum;
    private ButtonRectangle mPlayBtn;
    private TextView mDescribe;
    private ListView mVideoList;
    private ImageView mDownload;
    private ImageView mComment;
    private ImageView mCollect;
    private ImageView mSetting;
    private TextView mSources;

    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private VideoDetail videoDetail;
    private VideoListAdapter adpater;

    private boolean showD1etail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        initData();
        VideoRequest videoRequest = new VideoRequest();
        // videoRequest.getPlayUrl(mRequestQueue,"youku","XODY3ODkxNTg4",this);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.video_detail_toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_wht_24dp);

        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoDetailActivity.this.finish();
            }
        });

        mRequestQueue = VolleyManager.getInstance(getApplicationContext()).getRequestQueue();
        mImageLoader = VolleyManager.getInstance(getApplicationContext()).getImageLoader();

        Bundle bundle = getIntent().getBundleExtra("videoDetail");

        videoDetail = (VideoDetail) bundle.getSerializable("videoDetail");

        mPhoto = (NetworkImageView) findViewById(R.id.video_detail_photo);
        mTitle = (TextView) findViewById(R.id.video_detail_title);
        mPlayNum = (TextView) findViewById(R.id.video_detail_play_num);
        mDanmuNum = (TextView) findViewById(R.id.video_detail_danmu_num);
        mDescribe = (TextView) findViewById(R.id.video_detail_describe);
        mUpName = (TextView) findViewById(R.id.video_detail_up_name);
        mSources = (TextView) findViewById(R.id.video_detail_sources);
        mPlayBtn = (ButtonRectangle) findViewById(R.id.video_detail_play);
        mDownload = (ImageView) findViewById(R.id.video_detail_download);
        mComment = (ImageView) findViewById(R.id.video_detail_comment);
        mCollect = (ImageView) findViewById(R.id.video_detail_collect);
        mSetting = (ImageView) findViewById(R.id.video_detail_setting);


        mVideoList = (ListView) findViewById(R.id.video_detail_video_list);

        mPlayBtn.setOnClickListener(this);
        mDownload.setOnClickListener(this);

        mPhoto.setImageUrl(videoDetail.getThumb(), mImageLoader);
        mUpName.setText(videoDetail.getUser());
        mTitle.setText(videoDetail.getTitle());
        mPlayNum.setText(videoDetail.getPlay());
        mDanmuNum.setText(videoDetail.getMukio() + "");
        mDescribe.setText(videoDetail.getDescription());
        mSources.setText(videoDetail.getVideo().get(0).getType());

        adpater = new VideoListAdapter(videoDetail.getVideo());
        mVideoList.setAdapter(adpater);


//        VideoURLParse videoURLParse = new VideoURLParse();
//        try {
//            VideoPlay videoPlay = videoURLParse.parse(getAssets().open("url.xml"));
//           // UI.toast(this,videoPlay.getDurls().get(0).getUrl()+"");
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        onItemClick();
    }


    void onItemClick() {
        mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(VideoDetailActivity.this, VideoPlayerActivity.class);
//                "youku","XODY3ODkxNTg4",
                intent.putExtra("vid", videoDetail.getVideo().get(i).getVid());
                intent.putExtra("type", videoDetail.getVideo().get(i).getType());
                intent.putExtra("type", videoDetail.getVideo().get(0).getType());
                intent.putExtra("hid", videoDetail.getHid());
                intent.putExtra("part", i);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_detail_play:
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                intent.putExtra("vid", videoDetail.getVideo().get(0).getVid());
                intent.putExtra("type", videoDetail.getVideo().get(0).getType());
                intent.putExtra("type", videoDetail.getVideo().get(0).getType());
                intent.putExtra("hid", videoDetail.getHid());
                intent.putExtra("part", 0);
                startActivity(intent);
                break;
            case R.id.video_detail_download: //这里逻辑还未处理好
                if (showD1etail == true) {
                    for (Video v : adpater.getDatas()) {
                        v.setDown(false);
                    }
                    showD1etail = false;
                } else {
                    for (Video v : adpater.getDatas()) {
                        v.setDown(true);
                    }
                    showD1etail = true;
                }

                adpater.notifyDataSetChanged();
                break;
        }
    }

    class VideoListAdapter extends BaseAdapter {

        private List<Video> videoList;

        public VideoListAdapter(List<Video> videoList) {
            this.videoList = videoList;
        }

        @Override
        public int getCount() {
            return videoList.size();
        }

        @Override
        public Object getItem(int i) {
            return videoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(VideoDetailActivity.this).inflate(R.layout.video_item, null);
                viewHolder = new ViewHolder();
                viewHolder.position = (TextView) convertView.findViewById(R.id.video_item_position);
                viewHolder.title = (TextView) convertView.findViewById(R.id.video_item_title);
                viewHolder.down = (ImageView) convertView.findViewById(R.id.video_item_down);
                convertView.setTag(viewHolder);
            }

            if (showD1etail) {
                if (videoList.get(i).isDown()) {
                    viewHolder.down.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.down.setVisibility(View.INVISIBLE);
                }
            } else {
                viewHolder.down.setVisibility(View.VISIBLE);
            }

            viewHolder.down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getRealPlayUrl(videoDetail.getHid(), i, videoList.get(i).getType(), videoList.get(i).getVid());
                    danMuRequest(videoDetail.getHid(), i);
                }
            });

            viewHolder.position.setText((i + 1) + "");
            viewHolder.title.setText(videoList.get(i).getTitle() + "-" + videoList.get(i).getVid());
            return convertView;
        }


        public List<Video> getDatas() {
            return videoList;
        }

        class ViewHolder {
            private TextView position;
            private TextView title;
            private ImageView down;

        }
    }

    public VideoPlay getRealPlayUrl(final String hid, final int position, String type, String vid) {
        String realUrl;
        if ("qq".equalsIgnoreCase(type)) {
            realUrl = String.format(Api.PLAY_URL_DEFAULT, type, vid);
        } else {
            realUrl = String.format(Api.PLAY_URL_OTHER, type, vid);
        }
        UTF8StringRequest mUrlRequest = new UTF8StringRequest(Request.Method.GET, realUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                VideoPlay videoPlay = VideoUtil.parseUrl(response);
                String basePath = VideoUtil.createFile(getApplicationContext(), hid, position);
                File entry = new File(basePath, "entry.json");
                try {

                    entry.createNewFile();
                    FileOutputStream fos = new FileOutputStream(entry);
                    ByteArrayInputStream bis = new ByteArrayInputStream( JSON.toJSON(videoDetail).toString().getBytes());
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = bis.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    bis.close();
                    bis = null;
                    fos.close();
                    fos = null;

                    File index = new File(basePath + "/video", "index.json");
                    if (!index.exists()) {
                        index.mkdirs();
                        index.delete();
                        index.createNewFile();
                    }

                    fos = new FileOutputStream(index);
                    bis = new ByteArrayInputStream(response.getBytes());

                    count = 0;
                    while ((count = bis.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    bis.close();
                    bis = null;
                    fos.close();
                    fos = null;

                    downloadVideo(basePath+"/video",videoPlay.getDurls().get(0).getUrl(),0);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });

        mRequestQueue.add(mUrlRequest);
        return null;
    }

    /**
     * 弹幕请求
     */
    void danMuRequest(final String hid, final int part) {
        String realUrl = String.format(Api.BARRAGE, hid, part);
//        test();
        UTF8StringRequest mDanmuRequest = new UTF8StringRequest(Request.Method.GET, realUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String basePath = VideoUtil.createFile(getApplicationContext(), hid, part);
                File entry = new File(basePath, "danmuku.xml");
                try {
                    if (!entry.exists()) {

                        entry.createNewFile();

                    }
                    FileOutputStream fos = new FileOutputStream(entry);
                    ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = bis.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    bis.close();
                    bis = null;
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(mDanmuRequest);
    }


    public void downloadVideo(String path,String url,int position){
        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        String apkUrl = "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setDestinationInExternalPublicDir(path, position+".flv");
        request.addRequestHeader("User-agent", (DeviceManager.getCurrentUserAgent()));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        long downloadId = downloadManager.enqueue(request);


        DownloadChangeObserver downloadObserver = new DownloadChangeObserver(null,getApplicationContext(),downloadId);
        registerReceiver(downloadObserver.receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        getContentResolver().registerContentObserver(DownloadChangeObserver.CONTENT_URI, true, downloadObserver);

        save(videoDetail.getVideo().get(position).getVid(), (short) 0,videoDetail.getHid());
    }

    public void save(String vid,short down,String hid){
        LocalVideoDB db = new LocalVideoDB(getApplicationContext());
        db.openDB();
        db.insertOrUpdate(vid, down, hid);
    }


}
