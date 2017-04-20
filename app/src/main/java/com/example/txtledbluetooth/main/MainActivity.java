package com.example.txtledbluetooth.main;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseActivity;
import com.example.txtledbluetooth.dashboard.DashboardFragment;
import com.example.txtledbluetooth.light.LightFragment;
import com.example.txtledbluetooth.main.presenter.MainPresenter;
import com.example.txtledbluetooth.main.presenter.MainPresenterImpl;
import com.example.txtledbluetooth.main.view.MainView;
import com.example.txtledbluetooth.music.MusicFragment;
import com.example.txtledbluetooth.about.AboutFragment;
import com.example.txtledbluetooth.setting.SettingFragment;
import com.example.txtledbluetooth.sources.SourcesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private MainPresenter mPresenter;
    private DashboardFragment mDashboardFragment;
    private SourcesFragment mSourcesFragment;
    private MusicFragment mMusicFragment;
    private LightFragment mLightFragment;
    private SettingFragment mSettingFragment;
    private AboutFragment mAboutFragment;

    @Override
    public void init() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenterImpl(this);
        initToolbar();

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setHomeAsUpIndicator(null);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        navigationView.setItemIconTintList(null);
        setupDrawerContent(navigationView);
        switchDashboard();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mPresenter.switchNavigation(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void switchDashboard() {
        tvTitle.setText(R.string.dashboard);
        if (mDashboardFragment == null) {
            mDashboardFragment = new DashboardFragment();
        }
        showFragment(mDashboardFragment);
    }

    @Override
    public void switchSources() {
        tvTitle.setText(R.string.sources);
        if (mSourcesFragment == null) {
            mSourcesFragment = new SourcesFragment();
        }
        showFragment(mDashboardFragment);
    }

    @Override
    public void switchMusic() {
        tvTitle.setText(R.string.music);
        if (mMusicFragment == null) {
            mMusicFragment = new MusicFragment();
        }
        showFragment(mMusicFragment);
    }

    @Override
    public void switchLighting() {
        tvTitle.setText(R.string.lighting);
        if (mLightFragment == null) {
            mLightFragment = new LightFragment();
        }
        showFragment(mLightFragment);
    }

    @Override
    public void switchSettings() {
        tvTitle.setText(R.string.settings);
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }
        showFragment(mSettingFragment);
    }

    @Override
    public void switchAbout() {
        tvTitle.setText(R.string.about);
        if (mAboutFragment == null) {
            mAboutFragment = new AboutFragment();
        }
        showFragment(mAboutFragment);
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();
    }


}
