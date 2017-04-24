package com.example.txtledbluetooth.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.bean.Lighting;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KomoriWu
 * on 2017-04-18.
 */

public class Utils {
    public static final String ITEM_RIGHT_TEXT = "item_right_text";
    public static final String AUDIO_PROMPTS_DEFAULT_MODEL = "Voice and Tones";
    public static final String BLE_NAME = "Creative Halo-207";
    public static final String BLE_ADDRESS = "ble_address";

    public static DisplayImageOptions getImageOptions(int defaultIconId) {
        return getImageOptions(defaultIconId, 0);
    }

    public static DisplayImageOptions getImageOptions(int defaultIconId, int cornerRadiusPixels) {
        return new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels))
                .showImageOnLoading(defaultIconId)
                .showImageOnFail(defaultIconId)
                .showImageForEmptyUri(defaultIconId)
                .cacheInMemory(true)
                .cacheOnDisc()
                .build();
    }

    public static void showAlertDialog(Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    public static ArrayList<Lighting> getLightList(Context context) {
        String[] lightNames = context.getResources().getStringArray(R.array.lighting_name);
        int[] lightIcons = {R.mipmap.icon_moon_light, R.mipmap.icon_fireworks,
                R.mipmap.icon_blue_skies, R.mipmap.icon_rainbow, R.mipmap.icon_pulsate,
                R.mipmap.icon_glow, R.mipmap.icon_monochrome};
        ArrayList<Lighting> lightingList = new ArrayList<>();
        boolean isEdit;
        for (int i = 0; i < lightIcons.length; i++) {
            if (i == 5) {
                isEdit = true;
            } else {
                isEdit = false;
            }
            lightingList.add(i, new Lighting(lightNames[i], lightIcons[i], isEdit));
        }
        return lightingList;
    }

    public static HashMap<String, String> getBleAddressName(Context context) {
        BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        String address = bluetoothAdapter.getAddress();
        String name = bluetoothAdapter.getName();
        HashMap<String, String> map = new HashMap<>();
        map.put(BLE_ADDRESS, address);
        map.put(BLE_NAME, name);
        return map;
    }


    public static BleConnectOptions getBleConnectOptions() {
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)   // 连接如果失败重试3次
                .setConnectTimeout(10000)   // 连接超时10s
                .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                .setServiceDiscoverTimeout(10000)  // 发现服务超时20s
                .build();
        return options;
    }

}
