package com.steve.wanqureader.presentation.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.stephentuso.welcome.WelcomeScreenHelper;
import com.steve.wanqureader.R;
import com.steve.wanqureader.WanquApplication;
import com.steve.wanqureader.presentation.ui.fragments.FrontIssuesFragment;
import com.steve.wanqureader.presentation.ui.fragments.PostsFragment;
import com.steve.wanqureader.presentation.ui.fragments.StarredFragment;
import com.steve.wanqureader.utils.ConfigUtil;
import com.steve.wanqureader.utils.Constant;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String FRAGMENT_POSTS = "FRAGMENT_POSTS";

    private PostsFragment mPostFragment;
    private StarredFragment mStarredFragment;
    private FrontIssuesFragment mFrontIssuesFragment;
    private Fragment curFragment;
    private AppUpdaterUtils appUpdaterUtils;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;

    @BindString(R.string.snackbar_no_email_client)
    String noEmailClient;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (ConfigUtil.getBoolean(Constant.IS_FIRST_LAUNCH, true)) {
            Log.d(TAG, "IS_FIRST_LAUNCH is true");
            new WelcomeScreenHelper(this, MWelcomeActivity.class).show();
        }

        // First time init, create the UI.
        if (savedInstanceState == null) {
            mPostFragment = new PostsFragment();
            curFragment = mPostFragment;
            getSupportFragmentManager().beginTransaction().add(
                    R.id.frame_layout,
                    mPostFragment,
                    FRAGMENT_POSTS
            ).commit();
        }

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        if (appUpdaterUtils == null) {
            appUpdaterUtils = new AppUpdaterUtils(this)
                    .setUpdateFrom(UpdateFrom.XML)
                    .setUpdateXML(Constant.UPDATE_URL)
                    .withListener(new AppUpdaterUtils.UpdateListener() {
                        @Override
                        public void onSuccess(Update update, Boolean isUpdateAvailable) {
                            Log.d("AppUpdater", update.getLatestVersion()
                                    + ", " + update.getUrlToDownload()
                                    + ", " + Boolean.toString(isUpdateAvailable));

                            updateApp();
                        }

                        @Override
                        public void onFailed(AppUpdaterError error) {
                            Log.d("AppUpdater", "Something went wrong");
                        }
                    });
            appUpdaterUtils.start();
        }
    }

    @Override
    protected void onResume() {

        WanquApplication.refreshResources(this);
        super.onResume();
    }

    private void updateApp() {
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.setUpdateFrom(UpdateFrom.XML)
                .setUpdateXML(Constant.UPDATE_URL);
        appUpdater.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
//            String welcomeKey = data.getStringExtra(WelcomeActivity.WELCOME_SCREEN_KEY);

            if (resultCode == RESULT_OK) {
                ConfigUtil.putBoolean(Constant.IS_FIRST_LAUNCH, false);
                ConfigUtil.putBoolean(Constant.IS_SKIP_WELCOME, false);
//                Log.d(TAG, welcomeKey + " completed");
            } else {
                ConfigUtil.putBoolean(Constant.IS_FIRST_LAUNCH, false);
                ConfigUtil.putBoolean(Constant.IS_SKIP_WELCOME, true);
//                Log.d(TAG, welcomeKey + " canceled");
            }
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

            if (data.resolveActivity(getPackageManager()) != null) {
                startActivity(data);
            } else {
                Snackbar.make(mFrameLayout, noEmailClient, Snackbar.LENGTH_SHORT).show();
            }


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
            final int themeIndex = ConfigUtil.getInt(Constant.THEME_INDEX, 1);
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.alert_dialog_single_choice)
                    .setSingleChoiceItems(R.array.theme_items, themeIndex, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (themeIndex != whichButton) {
                                dialog.dismiss();
                                // Set the local night mode to some value
                                switch (whichButton) {
                                    case 0:
                                        WanquApplication.setTheme(MainActivity.this, 0);
                                        break;
                                    case 1:
                                        WanquApplication.setTheme(MainActivity.this, 1);
                                        break;
                                    case 2:
                                        WanquApplication.setTheme(MainActivity.this, 2);
                                        break;
                                }
                            }
                        }
                    });
            builder.show();

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
