package com.example.txtledbluetooth.music;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseActivity;

import butterknife.ButterKnife;

public class PlayingActivity extends BaseActivity {

    @Override
    public void init() {
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);
        initToolbar();
        tvTitle.setText(getString(R.string.now_playing));
    }

}
