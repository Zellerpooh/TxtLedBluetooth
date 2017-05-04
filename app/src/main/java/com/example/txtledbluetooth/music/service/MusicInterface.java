package com.example.txtledbluetooth.music.service;

import android.os.Handler;

/**
 * Created by KomoriWu
 * on 2017-05-04.
 */

public interface MusicInterface {

    void play(String songUrl, Handler handler);

    void pausePlay();

    void continuePlay();

    void seekTo(int progress);
}
