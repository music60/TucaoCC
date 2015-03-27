package com.studyjun.tucao.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.studyjun.tucao.R;
import com.studyjun.tucao.fragment.ResultListFragment;

public class SearchResultActivity extends ActionBarActivity {

    private FrameLayout mFrameLayout;
    private String queryString; //获取搜索内容
    private ResultListFragment resultListFragment;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init();
        doSearchQuery(getIntent());


    }

    // 对searchable activity的调用仍是标准的intent，我们可以从intent中获取信息，即要搜索的内容
    private void doSearchQuery(Intent intent) {
        if (intent == null)
            return;

        String queryAction = intent.getAction();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {  //如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
            queryString = intent.getStringExtra(SearchManager.QUERY);

        }
        if (resultListFragment!=null){
            ft.remove(resultListFragment);
        }

        resultListFragment = new ResultListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("p",queryString);

        resultListFragment.setArguments(bundle);
        ft.add(R.id.search_result_framelayout,resultListFragment);
        ft.commit();

    }

    void init(){

        toolbar = (Toolbar) this.findViewById(R.id.search_result_toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_wht_24dp);
        toolbar.setTitle("查找:");
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResultActivity.this.finish();
            }
        });

        mFrameLayout = (FrameLayout) findViewById(R.id.search_result_framelayout);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

    }


    @Override
    protected void onNewIntent(Intent intent) {  //activity重新置顶
        super.onNewIntent(intent);
        doSearchQuery(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
