package com.example.txtledbluetooth.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.txtledbluetooth.R;

import butterknife.BindView;

/**
 * Created by KomoriWu
 * on 2017-04-18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public TextView tvTitle;

    public abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            setTitle("");
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
