package com.example.txtledbluetooth.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.bean.MusicInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KomoriWu
 * on 2017-04-28.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private Context mContext;
    private ArrayList<MusicInfo> mLightingList;
    private OnItemClickListener mOnItemClickListener;
    private OnIvRightClickListener mOnIvRightClickListener;

    public MusicAdapter(Context mContext, ArrayList<MusicInfo> mLightingList,
                        OnItemClickListener mOnItemClickListener, OnIvRightClickListener
                                mOnIvRightClickListener) {
        this.mContext = mContext;
        this.mLightingList = mLightingList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mOnIvRightClickListener = mOnIvRightClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnIvRightClickListener {
        void onIvRightClick(View view, int position);
    }


    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_bottom_control, null);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.
            OnClickListener {
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.ic_music_head)
        ImageView icMusicHead;
        @BindView(R.id.iv_music_control)
        ImageView ivRight;
        @BindView(R.id.tv_music_name)
        TextView tvMusicName;
        @BindView(R.id.tv_singer)
        TextView tvSinger;

        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            progressBar.setVisibility(View.GONE);
            ivRight.setImageResource(R.mipmap.icon_music_three_points);
            itemView.setOnClickListener(this);
            ivRight.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            switch (view.getId()) {
                case R.id.iv_music_control:
                    if (mOnIvRightClickListener != null) {
                        mOnIvRightClickListener.onIvRightClick(view, position);
                    }
                    break;
                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position);
                    }
                    break;
            }
        }
    }
}