package com.example.txtledbluetooth.light;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.bean.Lighting;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KomoriWu
 * on 2017-04-22.
 */

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightViewHolder> {
    private Context mContext;
    private ArrayList<Lighting> mLightingList;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public LightAdapter(Context mContext, ArrayList<Lighting> mLightingList,OnItemClickListener
            mOnItemClickListener) {
        this.mContext = mContext;
        this.mLightingList = mLightingList;
        this.mOnItemClickListener=mOnItemClickListener;
    }

    @Override
    public LightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
        return new LightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LightViewHolder holder, int position) {
        Lighting lighting = mLightingList.get(position);
        holder.ivLightIcon.setImageResource(lighting.getLightingIcon());
        holder.tvLightName.setText(lighting.getLightingName());
        if (lighting.isEdit()) {
            holder.ivRight.setVisibility(View.VISIBLE);
            holder.ivRight.setImageResource(R.mipmap.icon_right_arrow);
        } else {
            holder.ivRight.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mLightingList == null ? 0 : mLightingList.size();
    }

    public class LightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_item_left)
        ImageView ivLightIcon;
        @BindView(R.id.tv_left_top)
        TextView tvLightName;
        @BindView(R.id.iv_item_right)
        ImageView ivRight;

        public LightViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }
}
