package com.example.txtledbluetooth.setting.presenter;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.setting.view.SettingView;

/**
 * Created by KomoriWu
 * on 2017-04-21.
 */

public class SettingPresenterImp implements SettingPresenter {
    private SettingView mSettingView;

    public SettingPresenterImp(SettingView mSettingView) {
        this.mSettingView = mSettingView;
    }

    @Override
    public void settings(int id) {
        switch (id) {
            case R.id.item_audio_prompts:
                mSettingView.setAudioPrompts();
                break;
            case R.id.item_auto_standby:
                mSettingView.setAutoStandby();
                break;
            case R.id.item_reset:
                mSettingView.setReset();
                break;
        }
    }
}
