package com.steve.wanqureader.presentation.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.stephentuso.welcome.WelcomeScreenHelper;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.steve.wanqureader.R;
import com.steve.wanqureader.presentation.ui.fragments.FrontIssuesFragment;
import com.steve.wanqureader.presentation.ui.fragments.PostsFragment;
import com.steve.wanqureader.presentation.ui.fragments.StarredFragment;
import com.steve.wanqureader.utils.Constant;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String fragmentPosts = "PostsFragment";
    private static final String isFirstLaunch = "isFirstLaunch";
    private static final String isSkipWelcome = "isSkipWelcome";

    private PostsFragment mPostFragment;
    private StarredFragment mStarredFragment;
    private FrontIssuesFragment mFrontIssuesFragment;
    private Fragment curFragment;
    private SharedPreferences pref;

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
        // First time launch, show welcome
        pref = getSharedPreferences("data", MODE_PRIVATE);
        if (pref.getBoolean(isFirstLaunch, true)) {
            Log.d(TAG, isFirstLaunch + " is true");
            new WelcomeScreenHelper(this, MWelcomeActivity.class).show();
        }

        // First time init, create the UI.
        if (savedInstanceState == null) {
            mPostFragment = new PostsFragment();
            curFragment = mPostFragment;
            getSupportFragmentManager().beginTransaction().add(
                    R.id.frame_layout,
                    mPostFragment,
                    fragmentPosts
            ).commit();
        }

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateXML(Constant.UPDATE_URL)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                                  @Override
                                  public void onSuccess(Update update, Boolean isUpdateAvailable) {
                                      Log.d("AppUpdater", update.getLatestVersion() + ", " + update.getUrlToDownload() + ", " + Boolean.toString(isUpdateAvailable));
                                  }

                                  @Override
                                  public void onFailed(AppUpdaterError error) {
                                      Log.d("AppUpdater", "Something went wrong");
                                  }
                              });
        appUpdaterUtils.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
            String welcomeKey = data.getStringExtra(WelcomeActivity.WELCOME_SCREEN_KEY);
            SharedPreferences.Editor editor = pref.edit();

            if (resultCode == RESULT_OK) {
                editor.putBoolean(isFirstLaunch, false);
                editor.putBoolean(isSkipWelcome, false);
                Log.d(TAG, welcomeKey + " completed");
            } else {
                editor.putBoolean(isFirstLaunch, false);
                editor.putBoolean(isSkipWelcome, true);
                Log.d(TAG, welcomeKey + " canceled");
            }
            editor.apply();
        }
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
            AboutActivity.actionStart(this);

        } else if (id == R.id.action_issues) {
            Intent data = new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse(Constant.ISSUES_EMAIL));
            data.putExtra(Intent.EXTRA_SUBJECT, Constant.ISSUES_TITLE);
            startActivity(data);

        } else if (id == R.id.action_search) {
            SearchByIssueIdActivity.actionStart(this);
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
            // Set action bar title
            setTitle(item.getTitle());
        } else if (id == R.id.nav_archives) {
            if (mFrontIssuesFragment != null) {
                switchContent(mFrontIssuesFragment);
            } else {
                mFrontIssuesFragment = new FrontIssuesFragment();
                switchContent(mFrontIssuesFragment);
            }
            setTitle(item.getTitle());
        } else if (id == R.id.nav_likes) {
            if (mStarredFragment != null) {
                switchContent(mStarredFragment);
            } else {
                mStarredFragment = new StarredFragment();
                switchContent(mStarredFragment);
            }
            setTitle(item.getTitle());
        } else if (id == R.id.nav_switch) {

        } else if (id == R.id.nav_about) {
            AboutActivity.actionStart(this);
        }

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
