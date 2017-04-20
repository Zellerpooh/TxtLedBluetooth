package com.example.txtledbluetooth.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseFragment;
import com.example.txtledbluetooth.main.MainActivity;
import com.example.txtledbluetooth.widget.ItemLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KomoriWu
 * on 2017-04-19.
 */

public class SettingFragment extends BaseFragment {

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }
}
