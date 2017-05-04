package com.example.txtledbluetooth.music;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
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
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseFragment;
import com.example.txtledbluetooth.bean.MusicInfo;
import com.example.txtledbluetooth.music.presenter.MusicPresenter;
import com.example.txtledbluetooth.music.presenter.MusicPresenterImpl;
import com.example.txtledbluetooth.music.service.MusicInterface;
import com.example.txtledbluetooth.music.service.MusicService;
import com.example.txtledbluetooth.music.view.MusicView;
import com.example.txtledbluetooth.utils.Utils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private MusicAdapter mMusicAdapter;
    private MusicPresenter mMusicPresenter;
    private ArrayList<MusicInfo> mMusicInfoArrayList;
    private MusicInterface mMusicInterface;
    private int mCurrentPosition;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int duration = bundle.getInt(Utils.DURATION);
            int currentProgress = bundle.getInt(Utils.CURRENT_PROGRESS);
            progressBar.setMax(duration);
            progressBar.setProgress(currentProgress);
            if (progressBar.getProgress() == duration) {
                new MusicAsyncTask(mMusicInfoArrayList.get(getNextSongPosition())).execute();
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMusicAdapter = new MusicAdapter(getActivity(), this, this);
        recyclerView.setAdapter(mMusicAdapter);
        initService();
        return view;
    }

    private void initService() {
        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().startService(intent);
        getActivity().bindService(intent, new MyServiceConn(), BIND_AUTO_CREATE);
    }

    @Override
    public void onIvRightClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, final int position) {
        mCurrentPosition = position;
        MusicInfo musicInfo = mMusicInfoArrayList.get(position);
        new MusicAsyncTask(musicInfo).execute();
    }

    private class MusicAsyncTask extends AsyncTask<Void, Integer, Void> {
        MusicInfo musicInfo;

        public MusicAsyncTask(MusicInfo musicInfo) {
            this.musicInfo = musicInfo;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mMusicInterface.play(musicInfo.getUrl(), mHandler);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivMusicHead.setImageBitmap(musicInfo.getAlbumImg());
            tvMusicName.setText(musicInfo.getTitle());
            tvSinger.setText(musicInfo.getArtist());
        }
    }


    @Override
    public void showMusics(ArrayList<MusicInfo> musicInfoList) {
        mMusicInfoArrayList = musicInfoList;
        mMusicAdapter.setMusicList(musicInfoList);
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
        }
        return nexSongPosition;
    }
}
