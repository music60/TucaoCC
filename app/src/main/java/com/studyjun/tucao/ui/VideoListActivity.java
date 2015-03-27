package com.studyjun.tucao.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.RequestQueue;
import com.studyjun.tucao.R;
import com.studyjun.tucao.bean.ChannelType;
import com.studyjun.tucao.bean.ChannelTypeItem;
import com.studyjun.tucao.fragment.VideoListFragment;
import com.studyjun.tucao.manager.VolleyManager;

import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class VideoListActivity extends ActionBarActivity implements MaterialTabListener,NavigationDrawerFragment.NavigationDrawerCallbacks {
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    MaterialTabHost tabHost;
    private Resources res;
    public RequestQueue mQueue;
    private ChannelType channelType;
    private List<ChannelTypeItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Bundle bundle = getIntent().getBundleExtra("channelType");
        channelType = (ChannelType) bundle.getSerializable("channelType");
        items = channelType.getItems();

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.video_list_toolbar);
        toolbar.setTitle(channelType.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_wht_24dp);

        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoListActivity.this.finish();
            }
        });

        mQueue = VolleyManager.getInstance(getApplicationContext()).getRequestQueue();


        tabHost = (MaterialTabHost) this.findViewById(R.id.video_list_tabHost);

        pager = (ViewPager) this.findViewById(R.id.video_list_pager);
        // init view pager
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }

        pager.setOffscreenPageLimit(items.size());//缓存全部界面，如不设置默认缓存1个
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
// when the tab is clicked the pager swipe content to the tab position
        pager.setCurrentItem(tab.getPosition());
    }


    @Override
    public void onTabReselected(MaterialTab tab) {
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }


        public Fragment getItem(int num) {
//            if (num==0){
//                return new NavigationDrawerFragment();
//            }
            VideoListFragment videoListFragment;
            Bundle bundle = new Bundle();
            bundle.putInt("tid", items.get(num).getTid());
            videoListFragment = new VideoListFragment();
            videoListFragment.setArguments(bundle);
            return videoListFragment;


        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).getName();
        }
    }

}

