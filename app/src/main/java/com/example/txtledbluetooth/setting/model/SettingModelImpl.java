package com.example.txtledbluetooth.setting.model;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;

import java.util.UUID;

/**
 * Created by KomoriWu
 * on 2017-04-28.
 */

public class SettingModelImpl implements SettingModel {
    @Override
    public void WriteCommand(BluetoothClient client, String macAddress,
                             UUID serviceUUID, UUID characterUUID, String command) {
        client.write(macAddress, serviceUUID,
                characterUUID, command.getBytes(),
                new BleWriteResponse() {
                    @Override
                    public void onResponse(int code) {

                    }
                });
    }
}
