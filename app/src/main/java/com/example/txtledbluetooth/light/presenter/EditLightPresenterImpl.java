package com.example.txtledbluetooth.light.presenter;

import android.content.Context;
import android.view.View;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.light.view.EditLightView;
import com.example.txtledbluetooth.widget.ColorPicker;

/**
 * Created by KomoriWu
 * on 2017-04-25.
 */

public class EditLightPresenterImpl implements EditLightPresenter, ColorPicker.
        OnColorSelectListener {
    private EditLightView mEditLightView;
    private ColorPicker mColorPicker;
    private Context mContext;
    private View mBgView;
    private boolean mIsSetOnColorSelectListener;


    public EditLightPresenterImpl(Context mContext, EditLightView mEditLightView,
                                  ColorPicker mColorPicker) {
        this.mContext = mContext;
        this.mEditLightView = mEditLightView;
        this.mColorPicker = mColorPicker;
        mColorPicker.setOnColorSelectListener(this);
    }

    @Override
    public void viewOnclick(View clickView, View bgView) {
        mBgView = bgView;
        switch (clickView.getId()) {
            case R.id.tv_chose_color_type:
                mEditLightView.showPopWindow();
                break;
            case R.id.rb_board1:
                break;
            case R.id.rb_board2:
                break;
            case R.id.rb_board3:
                break;
            case R.id.rb_board4:
                break;
            case R.id.rb_board5:
                break;
            case R.id.rb_board6:
                break;
            case R.id.rb_board7:
                break;
        }
    }

    @Override
    public void setIsSetOnColorSelectListener(boolean isSetOnColorSelectListener) {
        mIsSetOnColorSelectListener = isSetOnColorSelectListener;
    }

    @Override
    public void onColorSelect(int color) {
        if (mIsSetOnColorSelectListener) {
            mBgView.setBackgroundColor(color);
            mEditLightView.setTvColor(color);
        }
    }


}
