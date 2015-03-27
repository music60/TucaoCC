package com.studyjun.tucao.ui;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.studyjun.tucao.R;
import com.studyjun.tucao.fragment.AccountFragment;
import com.studyjun.tucao.fragment.ChannelFragment;
import com.studyjun.tucao.fragment.RecommendFragment;
import com.studyjun.tucao.manager.VolleyManager;
import com.studyjun.tucao.util.DeviceUtil;
import com.studyjun.tucao.util.UI;
import com.studyjun.tucao.widget.FragmentText;
import com.umeng.update.UmengUpdateAgent;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * 主页
 */
public class MainActivity extends ActionBarActivity implements MaterialTabListener {
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    private MaterialTabHost tabHost;
//    private Resources res;
    public RequestQueue mQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.update(this);

        mQueue = VolleyManager.getInstance(getApplicationContext()).getRequestQueue();
        setContentView(R.layout.activity_main);
//        res = this.getResources();
//        init toolbar (old action bar)
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);

        toolbar.setTitleTextColor(Color.WHITE);


        this.setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.back);
        tabHost = (MaterialTabHost) this.findViewById(R.id.main_tabHost);
        pager = (ViewPager) this.findViewById(R.id.main_pager);
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

        pager.setOffscreenPageLimit(3);//缓存3个界面，默认缓存1个

        DeviceUtil deviceUtil = new DeviceUtil(getApplicationContext());
        String netWorktype = deviceUtil.getNetWorkType(getApplicationContext());
        if (!"wifi".equals(netWorktype)){
            UI.toastLongTime(getApplicationContext(),"你现在使用的是"+netWorktype+"网络，请注意资费");
        } else if("invalid".equals(netWorktype)){
            UI.toastLongTime(getApplicationContext(),"当前网络未连接，请查看你的网络连接");
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        // 获取搜索服务管理器
        // searchable activity的component name，由此系统可通过intent进行唤起
        ComponentName cn = new ComponentName(this,SearchResultActivity.class);
        // 通过搜索管理器，从searchable activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
        SearchableInfo info = searchManager.getSearchableInfo(cn);
        if(info == null){
            Log.e("SearchableInfo", "Fail to get search info.");
        }
        // 将searchable activity的搜索信息与search view关联
        searchView.setSearchableInfo(info);

        return true;
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

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {
            if (num==0)
                return new RecommendFragment();
            if (num==1)
                return new ChannelFragment();
            else if (num==2)
                return new AccountFragment();
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "精选";
                case 1:
                    return "视频";
                case 2:
                    return "更多";
                default:
                    return null;
            }
        }
    }

}

