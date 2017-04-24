package com.example.txtledbluetooth.light.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.application.MyApplication;
import com.example.txtledbluetooth.light.view.LightView;
import com.example.txtledbluetooth.utils.BleCommandUtils;
import com.example.txtledbluetooth.utils.SharedPreferenceUtils;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;

import java.util.UUID;

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
                Toast.makeText(mContext, id + "", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void operateSwitchBluetooth(boolean isChecked) {
        String command = isChecked ? BleCommandUtils.OPEN : BleCommandUtils.CLOSE;
        UUID serviceUUID = UUID.fromString(SharedPreferenceUtils.getSendService(mContext));
        UUID characterUUID = UUID.fromString(SharedPreferenceUtils.getSendCharacter(mContext));
        MyApplication.getBluetoothClient(mContext).write(SharedPreferenceUtils.
                        getMacAddress(mContext), serviceUUID, characterUUID, command.getBytes(),
                new BleWriteResponse() {
                    @Override
                    public void onResponse(int code) {

                    }
                });
    }
}
