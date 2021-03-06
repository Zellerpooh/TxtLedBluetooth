package com.example.txtledbluetooth.light;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseFragment;
import com.example.txtledbluetooth.bean.Lighting;
import com.example.txtledbluetooth.bean.MusicInfo;
import com.example.txtledbluetooth.light.presenter.LightPresenter;
import com.example.txtledbluetooth.light.presenter.LightPresenterImpl;
import com.example.txtledbluetooth.light.view.LightView;
import com.example.txtledbluetooth.utils.MusicUtils;
import com.example.txtledbluetooth.utils.SharedPreferenceUtils;
import com.example.txtledbluetooth.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KomoriWu
 * on 2017-04-19.
 */

public class LightFragment extends BaseFragment implements LightView, LightAdapter.
        OnItemClickListener, LightAdapter.OnIvRightClickListener {
    @BindView(R.id.tv_switch)
    TextView tvSwitch;
    @BindView(R.id.switch_view)
    Switch aSwitch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LightAdapter mLightAdapter;
    private LightPresenter mLightPresenter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light, null);
        ButterKnife.bind(this, view);
        mLightPresenter = new LightPresenterImpl(this, getActivity());

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tvSwitch.setText(isChecked ? getString(R.string.on_capital) :
                        getString(R.string.off_capital));
                mLightPresenter.operateSwitchBluetooth(isChecked);
            }
        });
        return view;
    }

    @Override
    public void showLightData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLightAdapter = new LightAdapter(getActivity(), this, this);
        recyclerView.setAdapter(mLightAdapter);
        new initDataAsyncTask().execute();
    }

    private class initDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Lighting>> {
        ArrayList<Lighting> lightingList;
        List<Boolean> list;

        @Override
        protected ArrayList<Lighting> doInBackground(Void... voids) {
            lightingList = Utils.getLightList(getActivity());
            list = new ArrayList<>();
            for (int i = 0; i < lightingList.size(); i++) {
                if (SharedPreferenceUtils.getClickPosition(getActivity()) == i) {
                    list.add(true);
                } else {
                    list.add(false);
                }
            }
            return lightingList;
        }

        @Override
        protected void onPostExecute(ArrayList<Lighting> lightingArrayList) {
            mLightAdapter.setLightingList(lightingArrayList, list);
        }
    }


    @Override
    public void editLight(int id) {
        String[] lightNames = getActivity().getResources().getStringArray(R.array.lighting_name);
        Intent intent = new Intent(getActivity(), EditLightActivity.class);
        intent.putExtra(Utils.LIGHT_MODEL_ID, id);
        intent.putExtra(Utils.LIGHT_MODEL_NAME, lightNames[id]);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        mLightPresenter.operateItemBluetooth(position);
    }

    @Override
    public void onIvRightClick(View view, int position) {
        mLightPresenter.operateTvRightBluetooth(position);
    }
}
