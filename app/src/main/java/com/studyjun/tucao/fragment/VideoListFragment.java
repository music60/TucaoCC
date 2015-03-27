package com.studyjun.tucao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.studyjun.tucao.R;
import com.studyjun.tucao.api.Api;
import com.studyjun.tucao.bean.VideoDetail;
import com.studyjun.tucao.manager.VolleyManager;
import com.studyjun.tucao.ui.VideoDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VideoListFragment extends Fragment {

    private VideoListAdapter mAdapter;
    private ImageLoader mImageLoader;
    private RequestQueue mQueue;
    private JsonObjectRequest mJsonRequest;
    private ListView mListView;
    private int tid;
    private int page=1;
    private static int PAGE_SIZE=20;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = this.getArguments();
        tid = arguments.getInt("tid");
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        mListView = (ListView) view.findViewById(R.id.fragment_video_list_container);
        init();
        pullAddData();
        return view;
    }

    void init(){
        mAdapter = new VideoListAdapter();
        mListView.setAdapter(mAdapter);
        mQueue = VolleyManager.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        mImageLoader = VolleyManager.getInstance(getActivity().getApplicationContext()).getImageLoader();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


    class VideoListAdapter extends BaseAdapter {

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
            if (view != null) {
                viewHolder = (ViewHolder) view.getTag();
            } else {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.widget_video_list_item, null);

                viewHolder.mNetworkImageView = (NetworkImageView) view.findViewById(R.id.widget_video_list_img);
                viewHolder.mTitle = (TextView) view.findViewById(R.id.widget_video_list_title);
                viewHolder.mPlayNum = (TextView) view.findViewById(R.id.widget_video_list_play_num);
                viewHolder.mUPzhu = (TextView) view.findViewById(R.id.widget_video_list_upzhu);
                viewHolder.mNetworkImageView.setDefaultImageResId(R.drawable.tucao_defalut);
                view.setTag(viewHolder);
            }

            if (i==getDatas().size()-16){ //但结果还剩下10个时，主动更新20个
                pullAddData();
            }
            viewHolder.mTitle.setText(vdetails.get(i).getTitle());
            viewHolder.mPlayNum.setText(vdetails.get(i).getPlay());
            viewHolder.mUPzhu.setText(vdetails.get(i).getUser());

            viewHolder.mNetworkImageView.setImageUrl(vdetails.get(i).getThumb(), mImageLoader);
            return view;
        }

        /**
         * 获取到数据
         *
         * @return
         */
        public List<VideoDetail> getDatas() {
            return vdetails;
        }


        class ViewHolder {
            private TextView mTitle;
            private NetworkImageView mNetworkImageView;
            private TextView mPlayNum;
            private TextView mUPzhu;

        }
    }


    /**
     * 加载数据
     */
    public void pullAddData(){
        if (mJsonRequest==null) { //当mJsonRequest ==Null 时才进行更新


            String realUrl =String.format(Api.REGION_VIDEO_LIST,tid,page,PAGE_SIZE);

            mJsonRequest = new JsonObjectRequest(Request.Method.GET,realUrl,new JSONObject(),new Response.Listener<JSONObject>(){

                @Override
                public void onResponse(JSONObject response) {
                    JSONArray array = null;
                    try {
                        array = response.getJSONArray("result");
                        if (array!=null){
                            List<VideoDetail> vids = com.alibaba.fastjson.JSONArray.parseArray(array.toString(), VideoDetail.class);
                            mAdapter.getDatas().addAll(vids);
                            mAdapter.notifyDataSetChanged();
                            mJsonRequest =null;
                            page++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mJsonRequest =null;
                }
            });

            mQueue.add(mJsonRequest);
        } else {

        }
    }

}
