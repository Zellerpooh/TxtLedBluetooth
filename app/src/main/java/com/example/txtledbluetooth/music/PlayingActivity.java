package com.example.txtledbluetooth.music;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayingActivity extends BaseActivity {

    @BindView(R.id.activity_playing)
    RelativeLayout activityPlaying;
    @BindView(R.id.layout_volume)
    LinearLayout layoutVolume;

    @Override
    public void init() {
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);
        initToolbar();
        tvTitle.setText(getString(R.string.now_playing));
    }


    @OnClick({R.id.activity_playing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_playing:
                if (layoutVolume.getVisibility()==View.GONE){
                    layoutVolume.setVisibility(View.VISIBLE);
                }else if (layoutVolume.getVisibility()==View.VISIBLE){
                    layoutVolume.setVisibility(View.GONE);
                }
                break;
        }
    }
}
