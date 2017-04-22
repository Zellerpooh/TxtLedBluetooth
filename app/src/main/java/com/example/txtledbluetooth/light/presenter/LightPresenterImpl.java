package com.example.txtledbluetooth.light.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.light.view.LightView;

/**
 * Created by KomoriWu
 * on 2017-04-22.
 */

public class LightPresenterImpl implements LightPresenter {
    private LightView mLightView;
    private Context mContext;

    public LightPresenterImpl(LightView mLightView, Context mContext) {
        this.mLightView = mLightView;
        this.mContext = mContext;
        mLightView.showLightData();
    }


    @Override
    public void operateItemBluetooth(int id) {
        switch (id) {
            case R.id.iv_item_right:
                mLightView.editLight();
                break;
            default:
                Toast.makeText(mContext, id+"", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void operateSwitchBluetooth(boolean isChecked) {
        Toast.makeText(mContext, "sss", Toast.LENGTH_SHORT).show();
    }
}
