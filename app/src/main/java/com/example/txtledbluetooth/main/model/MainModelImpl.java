package com.example.txtledbluetooth.main.model;

import android.content.Context;

import com.example.txtledbluetooth.R;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;

import java.util.List;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;

/**
 * Created by KomoriWu
 * on 2017-04-24.
 */

public class MainModelImpl implements MainModel {


    @Override
    public void initBle(final Context context, final BluetoothClient client, BleConnectOptions
            bleConnectOptions, String macAddress, final String name, final OnInitBleListener
                                onInitBleListener) {
        if (client.isBleSupported()) {
            if (client.isBluetoothOpened()) {
                onInitBleListener.onFailure(context.getString(R.string.open_ble));
            } else {
                client.connect(macAddress, bleConnectOptions, new BleConnectResponse() {
                    @Override
                    public void onResponse(int code, BleGattProfile profile) {
                        if (code == REQUEST_SUCCESS) {
                            List<BleGattService> services = profile.getServices();
                            for (BleGattService service : services) {
                                List<BleGattCharacter> characters = service.getCharacters();
                                for (BleGattCharacter character : characters) {
                                    //sql
                                }
                            }
                            onInitBleListener.onSuccess(name);
                        } else {
                            onInitBleListener.onFailure(context.getString(R.string.conn_timeout));
                        }
                    }
                });
            }
        } else {
            onInitBleListener.OnException(context.getString(R.string.no_support_ble));
        }

    }

    public interface OnInitBleListener {
        void onSuccess(String name);

        void onFailure(String message);

        void OnException(String exception);

    }
}
