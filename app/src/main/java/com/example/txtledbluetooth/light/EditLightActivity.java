package com.example.txtledbluetooth.light;

import android.view.View;
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseActivity;
import com.example.txtledbluetooth.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditLightActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_right)
    TextView tvRevert;

    @Override
    public void init() {
        setContentView(R.layout.activity_edit_light);
        ButterKnife.bind(this);
        initToolbar();
        tvTitle.setText(getIntent().getStringExtra(Utils.LIGHT_MODEL_NAME));
        tvRevert.setVisibility(View.VISIBLE);
        tvRevert.setText(getString(R.string.revert));
    }

}
