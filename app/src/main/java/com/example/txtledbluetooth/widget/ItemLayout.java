package com.example.txtledbluetooth.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.txtledbluetooth.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KomoriWu
 * on 2017-04-19.
 */

public class ItemLayout extends RelativeLayout {
    @BindView(R.id.layout_top)
    RelativeLayout layoutItem;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private Context mContext;
    public OnItemListener onItemListener;
    public OnTvRightListener onTvRightListener;

    public OnItemListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public OnTvRightListener getOnTvRightListener() {
        return onTvRightListener;
    }

    public void setOnTvRightListener(OnTvRightListener onTvRightListener) {
        this.onTvRightListener = onTvRightListener;
    }

    public interface OnItemListener {
        void onClickItemListener(View v);
    }

    public interface OnTvRightListener {
        void onClickTvRightListener(View v);
    }

    public ItemLayout(Context context) {
        this(context, null);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String strLeft = "";
        String strRight = "";
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ItemLayout,
                defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ItemLayout_text_left:
                    strLeft = a.getString(attr);
                    break;
                case R.styleable.ItemLayout_text_right:
                    strRight = a.getString(attr);
                    break;
            }

        }
        a.recycle();
        init(context);
        tvRight.setText(strRight);
        tvLeft.setText(strLeft);
    }

    public void init(Context c) {
        this.mContext = c;
        LayoutInflater.from(mContext).inflate(R.layout.item_layout, this, true);
        ButterKnife.bind(this);
        initListener();

    }

    private void initListener() {
        layoutItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.onClickItemListener(v);
                }
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTvRightListener != null) {
                    onTvRightListener.onClickTvRightListener(v);
                }
            }
        });
    }
}
