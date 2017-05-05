package com.example.txtledbluetooth.music;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentUris;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.application.MyApplication;
import com.example.txtledbluetooth.base.BaseActivity;
import com.example.txtledbluetooth.bean.MusicInfo;
import com.example.txtledbluetooth.utils.MusicUtils;
import com.example.txtledbluetooth.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayingActivity extends BaseActivity {
    public static final String ROTATION = "rotation";
    @BindView(R.id.layout_activity_play)
    RelativeLayout layoutActivityPlay;
    @BindView(R.id.layout_album_cover)
    RelativeLayout layoutAlbumCover;
    @BindView(R.id.layout_volume)
    LinearLayout layoutVolume;
    @BindView(R.id.iv_needle)
    ImageView ivNeedle;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_album_cover)
    ImageView ivAlbumCover;
    private ObjectAnimator mNeedleAnim;
    private ObjectAnimator mRotateAnim;
    private AnimatorSet mAnimatorSet;

    @Override
    public void init() {
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);
        initToolbar();
        tvTitle.setText(getString(R.string.now_playing));
        List<MusicInfo> musicInfoList = MusicInfo.listAll(MusicInfo.class);
        Uri albumUri = ContentUris.withAppendedId(Uri.parse(MusicUtils.MUSIC_ALBUM_URI),
                musicInfoList.get(0).getAlbumId());
        MyApplication.getImageLoader(this).displayImage(String.valueOf(albumUri), ivAlbumCover, Utils.
                        getImageOptions(R.mipmap.logo,360));
    }

    @OnClick({R.id.layout_activity_play, R.id.iv_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_activity_play:
                if (layoutVolume.getVisibility() == View.GONE) {
                    layoutVolume.setVisibility(View.VISIBLE);
                } else if (layoutVolume.getVisibility() == View.VISIBLE) {
                    layoutVolume.setVisibility(View.GONE);
                }
                break;

            case R.id.iv_play:
                initNeedleAnim();
                initRotateAnim();
                mAnimatorSet = new AnimatorSet();
                mAnimatorSet.play(mNeedleAnim).before(mRotateAnim);
                mAnimatorSet.start();

                break;
        }
    }

    private void initRotateAnim() {
        if (mRotateAnim == null) {
            mRotateAnim = ObjectAnimator.ofFloat(layoutAlbumCover, ROTATION, 0f, 360f);
            mRotateAnim.setDuration(5000);
            mRotateAnim.setRepeatCount(-1);//设置动画重复次数，这里-1代表无限
            mRotateAnim.setRepeatMode(Animation.ABSOLUTE);//设置动画循环模式。
            mRotateAnim.setInterpolator(new LinearInterpolator());
        }
    }

    private void initNeedleAnim() {
        if (mNeedleAnim == null) {
            mNeedleAnim = ObjectAnimator.ofFloat(ivNeedle, ROTATION, -25, 0);
            mNeedleAnim.setDuration(200);
            mNeedleAnim.setRepeatMode(0);
            mNeedleAnim.setInterpolator(new LinearInterpolator());
        }
    }


}
