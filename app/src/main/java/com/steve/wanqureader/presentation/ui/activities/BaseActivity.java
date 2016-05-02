package com.steve.wanqureader.presentation.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by steve on 3/8/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);
}
