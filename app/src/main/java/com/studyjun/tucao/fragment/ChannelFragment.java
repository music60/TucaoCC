package com.studyjun.tucao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.studyjun.tucao.R;
import com.studyjun.tucao.bean.ChannelType;
import com.studyjun.tucao.bean.ChannelTypeItem;
import com.studyjun.tucao.ui.VideoListActivity;
import com.studyjun.tucao.util.UI;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 频道
 * @ClassName: ChannelFragment
 * @Description: TODO
 * @author studyjun
 * @date 2015-01-16
 *
 */
public class ChannelFragment extends Fragment implements OnClickListener {
    private static final String TAG = "ChannelFragment";

    private RelativeLayout mAinimation;
    private RelativeLayout mBangumi;
    private RelativeLayout mGame;
    private RelativeLayout mMusic;
    private RelativeLayout mGallery;
    private RelativeLayout mWhat;
    private RelativeLayout mSanciyuan;
    private RelativeLayout mMovie;
     private  Map<Integer,ChannelType> results;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = (View) inflater.inflate(R.layout.fragment_channel, null);
        mAinimation = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_ani);
        mGame = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_game);
        mBangumi = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_bangumi);
        mMovie = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_movie);

        mMusic = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_music);
        mWhat = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_what);
        mSanciyuan = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_sanciyuan);
        mGallery = (RelativeLayout) mView.findViewById(R.id.reccommend_channel_gallery);


        mAinimation.setOnClickListener(this);
        mGame.setOnClickListener(this);
        mBangumi.setOnClickListener(this);
        mMovie.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mWhat.setOnClickListener(this);
        mSanciyuan.setOnClickListener(this);
        mGallery.setOnClickListener(this);

        initChannel();
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 跳转列表页
     * @param channelType
     */
    void  gotoListView(ChannelType channelType){
        Intent intent = new Intent(this.getActivity(),VideoListActivity.class);
        Bundle bundle  = new Bundle();
        bundle.putSerializable("channelType",channelType);
        intent.putExtra("channelType",bundle);
        startActivity(intent);

    }


    void initChannel(){
        results = new HashMap<Integer,ChannelType>();
        ChannelType channelType1 = new ChannelType(19, "动画");
        channelType1.getItems().add(new ChannelTypeItem(25, "原创·配音"));
        channelType1.getItems().add(new ChannelTypeItem(6, "MMD·3D"));
        channelType1.getItems().add((new ChannelTypeItem(28, "MAD·AMV·GMV")));
        channelType1.getItems().add(new ChannelTypeItem(2, "综合·周边·其他"));


        ChannelType channelType2 = new ChannelType(20, "音乐");
        channelType2.getItems().add(new ChannelTypeItem(37, "舞蹈"));
        channelType2.getItems().add(new ChannelTypeItem(31, "翻唱·原唱"));
        channelType2.getItems().add(new ChannelTypeItem(7, "二次元音乐"));
        channelType2.getItems().add(new ChannelTypeItem(40, "演奏·乐器"));
        channelType2.getItems().add(new ChannelTypeItem(30, "VOCALOID·UTAU"));
        channelType2.getItems().add(new ChannelTypeItem(52, "Live·声优相关"));

        ChannelType channelType3 = new ChannelType(21, "游戏");
        channelType3.getItems().add(new ChannelTypeItem(33, "电子竞技"));
        channelType3.getItems().add(new ChannelTypeItem(44, "单机游戏"));
        channelType3.getItems().add(new ChannelTypeItem(34, "网络游戏"));
        channelType3.getItems().add(new ChannelTypeItem(8, "游戏影像"));
        channelType3.getItems().add(new ChannelTypeItem(42, "家机·掌机·手机"));

        ChannelType channelType4 = new ChannelType(22, "三次元");
        channelType4.getItems().add(new ChannelTypeItem(57, "科技"));
        channelType4.getItems().add(new ChannelTypeItem(32, "娱乐鬼畜"));
        channelType4.getItems().add(new ChannelTypeItem(9, "喜闻乐见"));
        channelType4.getItems().add(new ChannelTypeItem(61, "体育"));
        channelType4.getItems().add(new ChannelTypeItem(15, "宠物·猫·狗"));
        channelType4.getItems().add(new ChannelTypeItem(65, "军事情报"));

        ChannelType channelType5 = new ChannelType(23, "影剧");
        channelType5.getItems().add(new ChannelTypeItem(39, "电视剧"));
        channelType5.getItems().add(new ChannelTypeItem(27, "完结影剧"));
        channelType5.getItems().add(new ChannelTypeItem(38, "电影"));
        channelType5.getItems().add(new ChannelTypeItem(39, "电视剧"));

        ChannelType channelType6 = new ChannelType(24, "新番");
        channelType6.getItems().add(new ChannelTypeItem(11, "连载新番"));
        channelType6.getItems().add(new ChannelTypeItem(10, "完结合集"));
        channelType6.getItems().add(new ChannelTypeItem(24, "番剧大全"));
        channelType6.getItems().add(new ChannelTypeItem(26, "OAD·OVA·剧场版"));
        channelType6.getItems().add(new ChannelTypeItem(43, "天朝出品"));

        results.put(channelType1.getTid(),channelType1);
        results.put(channelType2.getTid(),channelType2);
        results.put(channelType3.getTid(),channelType3);
        results.put(channelType4.getTid(),channelType4);
        results.put(channelType5.getTid(),channelType5);
        results.put(channelType6.getTid(),channelType6);
    }

    Map<Integer,ChannelType> getChannelData(){


        return  results;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reccommend_channel_ani: //动画
//
                gotoListView(getChannelData().get(19));
                break;
            case R.id.reccommend_channel_game: //游戏
                gotoListView(getChannelData().get(21));
                break;
            case R.id.reccommend_channel_bangumi: //新番
                gotoListView(getChannelData().get(24));
                break;
            case R.id.reccommend_channel_movie: //电影
                gotoListView(getChannelData().get(23));
                break;
            case R.id.reccommend_channel_music: //音乐
                gotoListView(getChannelData().get(20));
                break;
            case R.id.reccommend_channel_gallery: //画室
                UI.toast(getActivity().getApplicationContext(),"这是什么东东？还没有开发");
                break;
            case R.id.reccommend_channel_sanciyuan: //三次元
                gotoListView(getChannelData().get(22));
                break;
            case R.id.reccommend_channel_what: //什么
                UI.toast(getActivity().getApplicationContext(),"这是什么东东？还没有开发");
                break;
        }
    }




}
