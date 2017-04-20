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
            case R.id.nav_item_dashboard:
                mMainView.switchDashboard();
                break;
            case R.id.nav_item_sources:
                mMainView.switchSources();
                break;
            case R.id.nav_item_music:
                mMainView.switchMusic();
                break;
            case R.id.nav_item_lighting:
                mMainView.switchLighting();
                break;
            case R.id.nav_item_setting:
                mMainView.switchSettings();
                break;
            case R.id.nav_item_about:
                mMainView.switchAbout();
                break;
        }
    }
}
