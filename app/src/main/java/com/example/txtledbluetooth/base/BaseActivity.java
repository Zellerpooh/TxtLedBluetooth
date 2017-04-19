package com.example.txtledbluetooth.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by KomoriWu
 * on 2017-04-18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
}
