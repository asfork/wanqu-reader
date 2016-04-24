package com.steve.wanqureader.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.steve.wanqureader.WanquApplication;

import butterknife.ButterKnife;

/**
 * Created by Steve Zhang
 * 2/23/16
 * <p/>
 * If it works, I created it. If not, I didn't.
 */
public abstract class BaseFragment extends Fragment {
    public abstract int getContentViewId();

    protected Context mContext;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, mRootView);
        this.mContext = getActivity();
        initView(savedInstanceState);
        return mRootView;
    }

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);//解绑
        RefWatcher refWatcher = WanquApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
