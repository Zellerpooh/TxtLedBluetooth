package com.example.txtledbluetooth.setting.presenter;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.setting.view.AudioPromptsView;

/**
 * Created by KomoriWu
 * on 2017-04-21.
 */

public class AudioPromptsPresenterImp implements AudioPromptsPresenter {
    private AudioPromptsView mPromptsView;

    public AudioPromptsPresenterImp(AudioPromptsView mPromptsView) {
        this.mPromptsView = mPromptsView;
    }

    @Override
    public void choseModel(int id) {
        switch (id) {
            case R.id.item_off:
                mPromptsView.selectOff();
                break;
            case R.id.item_tones_only:
                mPromptsView.selectTones();
                break;
            case R.id.item_voice_and_tones:
                mPromptsView.selectVoiceAndTones();
                break;
        }
    }
}
