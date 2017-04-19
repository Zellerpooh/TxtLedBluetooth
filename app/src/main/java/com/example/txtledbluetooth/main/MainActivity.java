package com.example.txtledbluetooth.main;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseActivity;
import com.example.txtledbluetooth.light.LightFragment;
import com.example.txtledbluetooth.main.presenter.MainPresenter;
import com.example.txtledbluetooth.main.presenter.MainPresenterImpl;
import com.example.txtledbluetooth.main.view.MainView;
import com.example.txtledbluetooth.music.MusicFragment;
import com.example.txtledbluetooth.setting.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.
        OnCheckedChangeListener, MainView {
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private MainPresenter mPresenter;
    private MusicFragment mMusicFragment;
    private MusicFragment mMusicFragmen;
    private LightFragment mLightFragment;
    private SettingFragment mSettingFragment;

    @Override
    public void init() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenterImpl(this);
        initToolbar();
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(R.id.rb_music);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        mPresenter.switchNavigation(checkedId);
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
    public void switchLight() {
        tvTitle.setText(R.string.mood_light);
        if (mLightFragment == null) {
            mLightFragment = new LightFragment();
        }
        showFragment(mLightFragment);
    }

    @Override
    public void switchSetting() {
        tvTitle.setText(R.string.setting);
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }
        showFragment(mSettingFragment);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_content, fragment)
                .commit();
    }

}
