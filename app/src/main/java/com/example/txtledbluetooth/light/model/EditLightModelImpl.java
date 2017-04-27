package com.example.txtledbluetooth.light.model;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;

import java.util.UUID;

/**
 * Created by KomoriWu
 * on 2017-04-27.
 */

public class EditLightModelImpl implements EditLightModel {

    @Override
    public void setLightSpeed(BluetoothClient client, String macAddress, UUID serviceUUID,
                              UUID characterUUID, String command) {
        client.write(macAddress, serviceUUID,
                characterUUID, command.getBytes(),
                new BleWriteResponse() {
                    @Override
                    public void onResponse(int code) {

                    }
                });
    }
}
