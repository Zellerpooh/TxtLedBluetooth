package com.example.txtledbluetooth.main;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

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
import com.example.txtledbluetooth.utils.AlertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {
    public static final int REQUEST_CODE_SETTING = 1;
    public static final int REQUEST_CODE_ALLOW = 2;
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
    private long mExitTime;

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
        navigationView.setItemIconTintList(getResources().
                getColorStateList(R.drawable.menu_icon_dashboard));
        navigationView.setItemTextColor(getResources().
                getColorStateList(R.drawable.menu_icon_dashboard));
        navigationView.setItemBackground(getResources().getDrawable(R.drawable.menu_item));
        setupDrawerContent(navigationView);
//        switchDashboard();
//        mPresenter.initBle(this);
        switchLighting();
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
    public void showProgress() {
        showProgressDialog(R.string.init_the_bluetooth);
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
        showFragment(mSourcesFragment);
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

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showLoadSuccessMsg(String name) {
        AlertUtils.showAlertDialog(this, String.format(getString(R.string.conn_ble_success),
                name));
    }

    @Override
    public void showLoadFailMsg(String message) {
        final Intent intent = new Intent();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setNegativeButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
                        startActivityForResult(intent, REQUEST_CODE_SETTING);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_CODE_ALLOW);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void showLoadExceptionMsg(String exception) {
        AlertUtils.showAlertDialog(this, exception);
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_ALLOW && resultCode == RESULT_OK) ||
                requestCode == REQUEST_CODE_SETTING) {
            mPresenter.initBle(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, R.string.exit_program_hint, Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
