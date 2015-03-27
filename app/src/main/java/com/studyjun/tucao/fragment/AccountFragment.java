package com.studyjun.tucao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studyjun.tucao.R;
import com.studyjun.tucao.ui.DownLoadListActivity;
import com.studyjun.tucao.util.UI;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * 账户
 *
 * @ClassName: AccountFragment
 * @Description: TODO
 * @author studyjun
 * @date 2014-12-17 下午1:22:25
 *
 */
public class AccountFragment extends Fragment implements OnClickListener {
    private static final String TAG = "AccountFragment";

    private View userLogin; //登录
    private View feedBack; //意见反馈
    private View setting; //设置
    private View down; //下载
    private View about; //关于
    private View update; //更新



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = (View) inflater.inflate(R.layout.fragment_account, null);
        userLogin = mView.findViewById(R.id.account_mine);
        feedBack = mView.findViewById(R.id.account_feedback);
        setting = mView.findViewById(R.id.account_setting);
        down = mView.findViewById(R.id.account_down);
        about = mView.findViewById(R.id.account_about);
        update = mView.findViewById(R.id.account_checkupdate);

        userLogin.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        setting.setOnClickListener(this);
        down.setOnClickListener(this);
        about.setOnClickListener(this);
        update.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_mine: // 用户登录
                UI.toast(getActivity().getApplicationContext(),"这是什么东东，还没做哦");
                break;
            case R.id.account_about: // 关于
                UI.toast(getActivity().getApplicationContext(),"这是什么东东，还没做哦");
                break;
            case R.id.account_setting: // 设置
                UI.toast(getActivity().getApplicationContext(),"这是什么东东，还没做哦");
                break;
            case R.id.account_down: // 下载
                UI.gotoActivity(getActivity(), DownLoadListActivity.class);
                break;
            case R.id.account_feedback: // 建议反馈
                UI.toast(getActivity().getApplicationContext(),"这是什么东东，还没做哦");
                break;
            case R.id.account_checkupdate: // 更新
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(getActivity(), updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(getActivity(), "没有更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(getActivity(), "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(getActivity(), "超时", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                UmengUpdateAgent.forceUpdate(getActivity());
                break;
            default:
                break;
        }
    }




}
