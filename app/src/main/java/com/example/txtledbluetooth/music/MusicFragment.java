package com.example.txtledbluetooth.music;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.txtledbluetooth.music.view.MusicView;
import com.example.txtledbluetooth.utils.AlertUtils;
import com.example.txtledbluetooth.utils.MusicUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KomoriWu
 * on 2017-04-19.
 */

public class MusicFragment extends BaseFragment implements MusicAdapter.OnIvRightClickListener,
        MusicAdapter.OnItemClickListener, MusicView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.ic_music_head)
    ImageView icMusicHead;
    @BindView(R.id.iv_music_control)
    ImageView icMusicControl;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    private MusicAdapter mMusicAdapter;
    private MusicPresenter mMusicPresenter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, null);
        ButterKnife.bind(this, view);
        mMusicPresenter = new MusicPresenterImpl(this);
        mMusicPresenter.scanMusic(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMusicAdapter = new MusicAdapter(getActivity(), this, this);
        recyclerView.setAdapter(mMusicAdapter);
        return view;
    }

    @Override
    public void onIvRightClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }


    @Override
    public void showMusics(ArrayList<MusicInfo> musicInfoList) {
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

}
