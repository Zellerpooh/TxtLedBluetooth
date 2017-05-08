package com.example.txtledbluetooth.music;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.application.MyApplication;
import com.example.txtledbluetooth.base.BaseActivity;
import com.example.txtledbluetooth.bean.MusicInfo;
import com.example.txtledbluetooth.music.service.MusicInterface;
import com.example.txtledbluetooth.music.service.MusicService;
import com.example.txtledbluetooth.utils.GaussianBlurUtil;
import com.example.txtledbluetooth.utils.MusicUtils;
import com.example.txtledbluetooth.utils.Utils;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayingActivity extends BaseActivity implements Observer {
    public static final String TAG = PlayingActivity.class.getSimpleName();
    public static final String ROTATION = "rotation";
    @BindView(R.id.layout_activity_play)
    RelativeLayout layoutActivityPlay;
    @BindView(R.id.layout_album_cover)
    FrameLayout layoutAlbumCover;
    @BindView(R.id.layout_volume)
    LinearLayout layoutVolume;
    @BindView(R.id.iv_needle)
    ImageView ivNeedle;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_album_cover)
    ImageView ivAlbumCover;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.seek_bar_play)
    SeekBar seekBarPlay;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    private ObjectAnimator mNeedleAnim;
    private ObjectAnimator mRotateAnim;
    private AnimatorSet mAnimatorSet;
    private int mIntentPosition;
    private String mAlbumUri;
    private List<MusicInfo> mMusicInfoList;
    private MusicInterface mMusicInterface;
    private Intent mIntent;
    private MyServiceConn mServiceConn;
    private int mUpdatePosition;

    @Override
    public void init() {
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);
        initToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
            }
        });
        initService();
        toolbar.setBackground(null);
        tvTitle.setText(getString(R.string.now_playing));
        mIntentPosition = getIntent().getIntExtra(Utils.POSITION, 0);
        mUpdatePosition=mIntentPosition;
        mMusicInfoList = MusicInfo.listAll(MusicInfo.class);
        initPlayUi(mIntentPosition);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mUpdatePosition != mIntentPosition) {
            initPlayUi(mUpdatePosition);
        }
    }
    private void initPlayUi(int position) {
        MusicInfo musicInfo = mMusicInfoList.get(position);
        tvMusicName.setText(musicInfo.getTitle());
        tvSinger.setText(musicInfo.getArtist());
        mAlbumUri = musicInfo.getAlbumUri();
        MyApplication.getImageLoader(PlayingActivity.this).displayImage(mAlbumUri,
                ivAlbumCover, Utils.getImageOptions(R.mipmap.placeholder_disk_play_program, 360));
        new AlbumCoverAsyncTask().execute();
    }

    private void initService() {
        mServiceConn = new MyServiceConn();
        mIntent = new Intent(this, MusicService.class);
        startService(mIntent);
        bindService(mIntent, mServiceConn, BIND_AUTO_CREATE);
    }

    private class AlbumCoverAsyncTask extends AsyncTask<Void, Void, Drawable> {

        @Override
        protected Drawable doInBackground(Void... voids) {
            Drawable drawable = GaussianBlurUtil.BoxBlurFilter(MusicUtils.createThumbFromUir(
                    PlayingActivity.this, Uri.parse(mAlbumUri)));
            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            layoutActivityPlay.setBackground(drawable);

        }
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

    class MyServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMusicInterface = (MusicInterface) iBinder;
            mMusicInterface.addObserver(PlayingActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    @Override
    public void update(Observable observable, Object object) {
        Bundle bundle = (Bundle) object;
        int duration = bundle.getInt(Utils.DURATION);
        int currentProgress = bundle.getInt(Utils.CURRENT_PROGRESS);
        String currentUrl = bundle.getString(Utils.CURRENT_PLAY_URL);
        for (int i = 0; i < mMusicInfoList.size(); i++) {
            if (currentUrl.equals(mMusicInfoList.get(i).getUrl())) {
                mUpdatePosition = i;
            }
        }
//        tvTimeRight.setText(duration/3600);
//        tvTimeLeft.setText(currentProgress);
        seekBarPlay.setMax(duration);
        seekBarPlay.setProgress(currentProgress);
//        if (seekBarPlay.getProgress() == duration) {
//
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConn);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
