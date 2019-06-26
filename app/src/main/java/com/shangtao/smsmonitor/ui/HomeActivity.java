package com.shangtao.smsmonitor.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mula.base.activity.BaseActivity;
import com.shangtao.smsmonitor.R;

import com.shangtao.smsmonitor.ui.fragment.SettingFragment;

public class HomeActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    protected void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fl_container, new SettingFragment())
                .commitAllowingStateLoss();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // super中回调Fragment的onRequestPermissionsResult有问题，需自己回调Fragment的onRequestPermissionsResult方法
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {//可能为null需判断
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

}
