package com.example.txtledbluetooth.application;

import android.app.Application;
import android.content.Context;

import com.inuker.bluetooth.library.BluetoothClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by KomoriWu
 * on 2017-04-18.
 */

public class MyApplication extends Application {
    private static ImageLoader mImageLoader;
    private static BluetoothClient mBluetoothClient;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static ImageLoader getImageLoader(Context context) {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = ImageLoader.getInstance();
                    mImageLoader.init(ImageLoaderConfiguration.createDefault(context.
                            getApplicationContext()));
                }
            }
        }
        return mImageLoader;
    }

    public static BluetoothClient getBluetoothClient(Context context) {
        if (mBluetoothClient == null) {
            synchronized (BluetoothClient.class) {
                if (mBluetoothClient == null) {
                    mBluetoothClient= new BluetoothClient(context);
                }
            }
        }
        return mBluetoothClient;
    }


}
