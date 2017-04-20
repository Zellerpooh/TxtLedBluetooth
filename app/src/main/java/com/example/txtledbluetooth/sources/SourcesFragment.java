package com.example.txtledbluetooth.sources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseFragment;
import com.example.txtledbluetooth.widget.ItemLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KomoriWu
 * on 2017-04-20.
 */

public class SourcesFragment extends BaseFragment {
    public static final String SELECT_BLUETOOTH = "bluetooth";
    public static final String SELECT_AUX_IN = "aux-in";
    @BindView(R.id.item_bluetooth)
    ItemLayout itemBluetooth;
    @BindView(R.id.item_aux_in)
    ItemLayout itemAuxIn;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, null);
        ButterKnife.bind(this, view);
        itemBluetooth.setOnItemListener(new ItemLayout.OnItemListener() {
            @Override
            public void onClickItemListener(View v) {
                itemBluetooth.setIsItemSelected(true);
                itemAuxIn.setIsItemSelected(false);
            }
        });
        itemAuxIn.setOnItemListener(new ItemLayout.OnItemListener() {
            @Override
            public void onClickItemListener(View v) {
                itemBluetooth.setIsItemSelected(false);
                itemAuxIn.setIsItemSelected(true);
            }
        });
        return view;
    }
}
