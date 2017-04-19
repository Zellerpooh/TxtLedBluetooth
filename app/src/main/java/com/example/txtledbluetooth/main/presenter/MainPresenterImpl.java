package com.example.txtledbluetooth.main.presenter;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.main.view.MainView;

/**
 * Created by KomoriWu
 * on 2017-04-19.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;

    public MainPresenterImpl(MainView mMainView) {
        this.mMainView = mMainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.rb_music:
                mMainView.switchMusic();
                break;
            case R.id.rb_light:
                mMainView.switchLight();
                break;
            case R.id.rb_setting:
                mMainView.switchSetting();
                break;
        }
    }
}
