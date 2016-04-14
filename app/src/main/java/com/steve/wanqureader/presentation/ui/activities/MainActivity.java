package com.steve.wanqureader.presentation.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.steve.wanqureader.R;
import com.steve.wanqureader.presentation.ui.fragments.FrontIssuesFragment;
import com.steve.wanqureader.presentation.ui.fragments.PostsFragment;
import com.steve.wanqureader.presentation.ui.fragments.StarredFragment;
import com.steve.wanqureader.utils.Constant;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static String TAG = "MainActivity";
    private PostsFragment mPostFragment;
    private StarredFragment mStarredFragment;
    private FrontIssuesFragment mFrontIssuesFragment;
    private Fragment curFragment;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nav_view)
    NavigationView mNavView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // First time init, create the UI.
        if (savedInstanceState == null) {
            mPostFragment = new PostsFragment();
            curFragment = mPostFragment;
            getSupportFragmentManager().beginTransaction().add(
                    R.id.frame_layout,
                    mPostFragment,
                    Constant.TAG_FRAGMENT_POSTS
            ).commit();
        }

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            WebViewActivity.actionStart(this, getString(R.string.activity_about), Constant.ABOUT_URL);
        } else if (id == R.id.action_issues) {
            Intent data = new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse(Constant.ISSUES_EMAIL));
            data.putExtra(Intent.EXTRA_SUBJECT, Constant.ISSUES_TITLE);
            startActivity(data);
        } else if (id == R.id.action_search) {
            Log.d("BaseActivity", "search");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_latest) {
            if (mPostFragment != null) {
                switchContent(mPostFragment);
            } else {
                mPostFragment = new PostsFragment();
                switchContent(mPostFragment);
            }

        } else if (id == R.id.nav_archives) {
            if (mFrontIssuesFragment != null) {
                switchContent(mFrontIssuesFragment);
            } else {
                mFrontIssuesFragment = new FrontIssuesFragment();
                switchContent(mFrontIssuesFragment);
            }

        } else if (id == R.id.nav_likes) {
            if (mStarredFragment != null) {
                switchContent(mStarredFragment);
            } else {
                mStarredFragment = new StarredFragment();
                switchContent(mStarredFragment);
            }

        } else if (id == R.id.nav_about) {
            WebViewActivity.actionStart(this, getString(R.string.activity_about), Constant.ABOUT_URL);
        }

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "switch theme clicked");
    }

    private void switchContent(Fragment fragment) {
        try {
            Log.d(TAG, "current: " + curFragment.getClass().getSimpleName());
            if (fragment != curFragment) {
                Log.d(TAG, "new: " + fragment.getClass().getSimpleName());
                Fragment frontFragment = curFragment;
                curFragment = fragment;

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (!fragment.isAdded()) {    // 先判断是否被add过
                    // 隐藏当前的fragment，add下一个到Activity中
                    fragmentManager.beginTransaction().hide(frontFragment).add(R.id.frame_layout, fragment).commit();
                } else {
                    // 隐藏当前的fragment，显示下一个
                    fragmentManager.beginTransaction().hide(frontFragment).show(fragment).commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
