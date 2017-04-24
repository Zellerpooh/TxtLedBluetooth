package com.example.txtledbluetooth.light.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.application.MyApplication;
import com.example.txtledbluetooth.light.model.LightModel;
import com.example.txtledbluetooth.light.model.LightModelImpl;
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
    private LightModel mLightModel;
    private String mMacAddress;
    private UUID mServiceUUID;
    private UUID mCharacterUUID;

    public LightPresenterImpl(LightView mLightView, Context mContext) {
        this.mLightView = mLightView;
        this.mContext = mContext;
        mLightModel = new LightModelImpl();
        mLightView.showLightData();
        mServiceUUID = UUID.fromString(SharedPreferenceUtils.getSendService(mContext));
        mCharacterUUID = UUID.fromString(SharedPreferenceUtils.getSendCharacter(mContext));
        mMacAddress = SharedPreferenceUtils.getMacAddress(mContext);
    }


    @Override
    public void operateItemBluetooth(int id) {

        String command = "";
        switch (id) {
            case R.id.iv_item_right:
                mLightView.editLight();
                break;
            case 0:
                command = BleCommandUtils.MOON_LIGHT;
                break;
            case 1:
                command = BleCommandUtils.FIREWORK;
                break;
            case 2:
                command = BleCommandUtils.BLUE_SKIES;
                break;
            case 3:
                command = BleCommandUtils.RAINBOW;
                break;
            case 4:
                command = BleCommandUtils.PULSATE;
                break;
            case 5:
                command = BleCommandUtils.GLOW;
                break;
            case 6:
                command = BleCommandUtils.MONOCHROME;
                break;
        }
        if (!TextUtils.isEmpty(command)) {
            mLightModel.WriteCommand(MyApplication.getBluetoothClient(mContext),mMacAddress
                    , mServiceUUID, mCharacterUUID, command);
        }
    }

    @Override
    public void operateSwitchBluetooth(boolean isChecked) {
        String command = isChecked ? BleCommandUtils.OPEN : BleCommandUtils.CLOSE;
        if (!TextUtils.isEmpty(command)) {
            mLightModel.WriteCommand(MyApplication.getBluetoothClient(mContext),
                    SharedPreferenceUtils.getMacAddress(mContext), mServiceUUID,
                    mCharacterUUID, command);
        }
    }
}
