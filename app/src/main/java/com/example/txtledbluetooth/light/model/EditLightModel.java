package com.example.txtledbluetooth.light.model;

import com.inuker.bluetooth.library.BluetoothClient;

import java.util.UUID;

/**
 * Created by KomoriWu
 * on 2017-04-27.
 */

public interface EditLightModel {
    void setLightSpeed(BluetoothClient client, String macAddress, UUID serviceUUID,
                       UUID characterUUID, String command);
    void setLightBrightness(BluetoothClient client, String macAddress, UUID serviceUUID,
                       UUID characterUUID, String command);
}
