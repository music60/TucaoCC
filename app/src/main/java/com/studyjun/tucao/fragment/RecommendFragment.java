package com.studyjun.tucao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.studyjun.tucao.R;
import com.studyjun.tucao.api.Api;
import com.studyjun.tucao.manager.VolleyManager;
import com.studyjun.tucao.ui.MainActivity;
import com.studyjun.tucao.ui.VideoDetailActivity;
import com.studyjun.tucao.bean.VideoDetail;
import com.yalantis.pulltorefresh.library.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 精选
 * 客户端推荐内容
 * @author studyjun
 * @about http://www.studyjun.com
 */
public class RecommendFragment extends android.support.v4.app.Fragment {
    public static final String TAG ="RecommendFragment" ;
    private List<VideoDetail> videoDetailList;
    private GridView mGridView;
    private PullToRefreshView mPullToRefreshView;
    private MyBaseAdapter mAdapter;
    private ImageLoader mImageLoader;
    private MainActivity context;

    private static int TID=19;
    private static int DATE=0;

    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        context = (MainActivity) getActivity();
        mGridView = (GridView) view.findViewById(R.id.recommend_grid_view);

        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.recommend_pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        refresh();

                    }
                }, 1000);
            }
        });
        initComponent();
        refresh();
        return view;
    }

    void initComponent(){

        requestQueue = VolleyManager.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        mAdapter = new MyBaseAdapter();
        mGridView.setAdapter(mAdapter);
        mImageLoader =VolleyManager.getInstance(getActivity().getApplicationContext()).getImageLoader();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
                Bundle bd = new Bundle();
                bd.putSerializable("videoDetail",mAdapter.getDatas().get(i));
                intent.putExtra("videoDetail",bd);
                startActivity(intent);
            }
        });

    }

    void refresh(){

        final String realUrl =String.format(Api.RANK_LIST,TID,DATE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET,realUrl,new JSONObject(), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array =response.getJSONArray("result");
                    List<VideoDetail> vids = com.alibaba.fastjson.JSONArray.parseArray(array.toString(), VideoDetail.class);
                    mAdapter.getDatas().clear();
                    mAdapter.getDatas().addAll(vids);
                    mAdapter.notifyDataSetChanged();
                    VolleyManager.getStringCache().putString(realUrl,array.toString());

                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

                    String array = VolleyManager.getStringCache().getString(realUrl);
                    List<VideoDetail> vids = com.alibaba.fastjson.JSONArray.parseArray(array, VideoDetail.class);
                    if(vids==null){
                        return;
                    }
                    mAdapter.getDatas().addAll(vids);
                    mAdapter.notifyDataSetChanged();



            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    class MyBaseAdapter extends BaseAdapter {

        List<VideoDetail> vdetails = new ArrayList<VideoDetail>();

        @Override
        public int getCount() {
            return vdetails.size();
        }

        @Override
        public Object getItem(int i) {
            return vdetails.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view!=null){
                viewHolder = (ViewHolder) view.getTag();
            }else {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.widget_video_item,null);

                viewHolder.mNetworkImageView= (NetworkImageView) view.findViewById(R.id.widget_video_img);
                viewHolder.mTitle= (TextView) view.findViewById(R.id.widget_video_title);
                viewHolder.mPlayNum= (TextView) view.findViewById(R.id.widget_video_play_num);
                viewHolder.mDanmuNum= (TextView) view.findViewById(R.id.widget_video_danmu_num);
                viewHolder.mNetworkImageView.setDefaultImageResId(R.drawable.tucao_defalut);
                view.setTag(viewHolder);
            }
            viewHolder.mTitle.setText(vdetails.get(i).getTitle());
            viewHolder.mPlayNum.setText(vdetails.get(i).getPlay());
            viewHolder.mDanmuNum.setText(""+vdetails.get(i).getMukio());
            viewHolder.mNetworkImageView.setImageUrl(vdetails.get(i).getThumb(),mImageLoader);
            return view;
        }

        /**
         * 获取到数据
         * @return
         */
        public List<VideoDetail> getDatas(){
            return  vdetails;
        }


        class ViewHolder {
            private TextView mTitle;
            private NetworkImageView mNetworkImageView;
            private TextView mPlayNum;
            private TextView mDanmuNum;

        }
    }
} 