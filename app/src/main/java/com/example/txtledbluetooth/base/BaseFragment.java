package com.example.txtledbluetooth.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txtledbluetooth.R;

/**
 * Created by KomoriWu
 * on 2017-04-18.
 */

public abstract class BaseFragment extends Fragment {

    public abstract View initView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        return view;
    }

    public void replaceFragment(BaseFragment fragment, int resId) {
        replaceFragment(fragment, resId, true);
    }

    public void replaceFragment(BaseFragment fragment, int resId, boolean addToBackStack) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.screen_left_out, R.anim.screen_right_in,
                R.anim.screen_left_in, R.anim.screen_right_out);
        transaction.replace(resId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }
}
