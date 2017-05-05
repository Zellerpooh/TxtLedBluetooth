package com.example.txtledbluetooth.music;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseFragment;
import com.example.txtledbluetooth.bean.MusicInfo;
import com.example.txtledbluetooth.music.presenter.MusicPresenter;
import com.example.txtledbluetooth.music.presenter.MusicPresenterImpl;
import com.example.txtledbluetooth.music.service.MusicInterface;
import com.example.txtledbluetooth.music.service.MusicService;
import com.example.txtledbluetooth.music.view.MusicView;
import com.example.txtledbluetooth.utils.SharedPreferenceUtils;
import com.example.txtledbluetooth.utils.Utils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.example.txtledbluetooth.main.MainActivity.REQUEST_CODE_SETTING;

/**
 * Created by KomoriWu
 * on 2017-04-19.
 */

public class MusicFragment extends BaseFragment implements MusicAdapter.OnIvRightClickListener,
        MusicAdapter.OnItemClickListener, MusicView {
    public static final String TAG = MusicFragment.class.getSimpleName();
    public static final int PERMISSION_REQUEST_CODE = 100;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.iv_music_head)
    ImageView ivMusicHead;
    @BindView(R.id.iv_music_control)
    ImageView ivMusicControl;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    @BindView(R.id.layout_music_control)
    RelativeLayout layoutMusicControl;
    Unbinder unbinder;
    private MusicAdapter mMusicAdapter;
    private MusicPresenter mMusicPresenter;
    private ArrayList<MusicInfo> mMusicInfoArrayList;
    private MusicInterface mMusicInterface;
    private Intent mIntent;
    private MyServiceConn mServiceConn;
    private int mCurrentPosition = -1;
    private boolean mIsCurrentPlay;
    private boolean mIsExistPlayData;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int duration = bundle.getInt(Utils.DURATION);
            int currentProgress = bundle.getInt(Utils.CURRENT_PROGRESS);
            progressBar.setMax(duration);
            progressBar.setProgress(currentProgress);
            if (progressBar.getProgress() == duration) {
                mMusicPresenter.playMusic(mHandler, mMusicInterface, mMusicInfoArrayList.
                        get(getNextSongPosition()).getUrl());
                ivMusicControl.setImageResource(R.mipmap.icon_play);
            }

        }
    };

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, null);
        ButterKnife.bind(this, view);
        mMusicPresenter = new MusicPresenterImpl(this);
        // 先判断是否有权限。
        if (AndPermission.hasPermission(getActivity(), Utils.getPermission(2),
                Utils.getPermission(3))) {
            mMusicPresenter.scanMusic(getActivity());
        } else {
            AndPermission.with(this)
                    .requestCode(PERMISSION_REQUEST_CODE)
                    .permission(Utils.getPermission(2), Utils.getPermission(3))
                    .send();
        }
        initRecycleView();
        initService();
        return view;
    }

    private void initRecycleView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMusicAdapter = new MusicAdapter(getActivity(), this, this);
        recyclerView.setAdapter(mMusicAdapter);
    }

    private void initService() {
        mServiceConn = new MyServiceConn();
        mIntent = new Intent(getActivity(), MusicService.class);
        getActivity().startService(mIntent);
        getActivity().bindService(mIntent, mServiceConn, BIND_AUTO_CREATE);
    }

    @Override
    public void onIvRightClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, final int position) {
        if (mCurrentPosition != position) {
            mIsCurrentPlay = false;
            mCurrentPosition = position;
        } else {
            mIsCurrentPlay = true;
        }
        MusicInfo musicInfo = mMusicInfoArrayList.get(position);
        if (mIsCurrentPlay) {
            if (mMusicInterface.isPlaying()) {
                mMusicInterface.pausePlay();
                ivMusicControl.setImageResource(R.mipmap.icon_pause);
            } else {
                mMusicInterface.continuePlay();
                ivMusicControl.setImageResource(R.mipmap.icon_play);
            }
        } else {
            mMusicPresenter.playMusic(mHandler, mMusicInterface, musicInfo.getUrl());
            ivMusicControl.setImageResource(R.mipmap.icon_play);
            mIsExistPlayData = true;
        }

    }


    @Override
    public void showMusics(ArrayList<MusicInfo> musicInfoList) {
        mMusicInfoArrayList = musicInfoList;
        mMusicAdapter.setMusicList(musicInfoList);
        //获取上次播放的position
        mCurrentPosition = SharedPreferenceUtils.getLastPlayPosition(getActivity());
        if (mCurrentPosition < mMusicInfoArrayList.size() && mCurrentPosition > -1) {
            updateTextView(mMusicInfoArrayList.get(mCurrentPosition).getUrl());
            if (mMusicInterface.isPlaying()) {
                ivMusicControl.setImageResource(R.mipmap.icon_play);
            } else {
                ivMusicControl.setImageResource(R.mipmap.icon_pause);
            }
        }
    }

    @Override
    public void updateTextView(String songUrl) {
        //定位到正在播放的MusicInfo
        for (MusicInfo musicInfo : mMusicInfoArrayList) {
            if (musicInfo.getUrl().equals(songUrl)) {
                ivMusicHead.setImageBitmap(musicInfo.getAlbumImg());
                tvMusicName.setText(musicInfo.getTitle());
                tvSinger.setText(musicInfo.getArtist());
            }
        }
    }

    @Override
    public void showProgress() {
        showProgressDialog(R.string.scan_music_hint);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionYes(PERMISSION_REQUEST_CODE)
    private void getLocationYes(List<String> grantedPermissions) {
        // TODO 申请权限成功。
        mMusicPresenter.scanMusic(getActivity());
    }

    @PermissionNo(PERMISSION_REQUEST_CODE)
    private void getLocationNo(List<String> deniedPermissions) {
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_music_control, R.id.layout_music_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_music_control:
                if (mIsExistPlayData) {
                    if (mMusicInterface.isPlaying()) {
                        ivMusicControl.setImageResource(R.mipmap.icon_pause);
                        mMusicInterface.pausePlay();
                    } else {
                        ivMusicControl.setImageResource(R.mipmap.icon_play);
                        mMusicInterface.continuePlay();
                    }
                } else {
                    if (mMusicInfoArrayList.size() > 0) {
                        if (!(mCurrentPosition < mMusicInfoArrayList.size() &&
                                mCurrentPosition > -1)) {
                            mCurrentPosition = 0;
                        }
                        mMusicPresenter.playMusic(mHandler, mMusicInterface,
                                mMusicInfoArrayList.get(mCurrentPosition).getUrl());
                        ivMusicControl.setImageResource(R.mipmap.icon_play);
                        mIsExistPlayData = true;
                    }
                }
                break;
            case R.id.layout_music_control:
                Intent intent = new Intent(getActivity(), PlayingActivity.class);
                startActivity(intent);
                break;
        }
    }


    class MyServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMusicInterface = (MusicInterface) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    private int getNextSongPosition() {
        mCurrentPosition += 1;
        int nexSongPosition = mCurrentPosition;
        if (nexSongPosition >= mMusicInfoArrayList.size()) {
            nexSongPosition = 0;
            mCurrentPosition = 0;
        }
        return nexSongPosition;
    }


    @Override
    public void onPause() {
        super.onPause();
        SharedPreferenceUtils.saveLastPlayPosition(getActivity(), mCurrentPosition);
    }
}
