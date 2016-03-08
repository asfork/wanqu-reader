package com.steve.wanqureader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by steve on 3/8/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);//解除绑定，官方文档只对fragment做了解绑
    }
}