package com.example.txtledbluetooth.light;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseActivity;
import com.example.txtledbluetooth.light.presenter.EditLightPresenter;
import com.example.txtledbluetooth.light.presenter.EditLightPresenterImpl;
import com.example.txtledbluetooth.light.view.EditLightView;
import com.example.txtledbluetooth.utils.Utils;
import com.example.txtledbluetooth.widget.ColorPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditLightActivity extends BaseActivity implements View.OnClickListener,
        PopupWindowAdapter.OnPopupItemClickListener, EditLightView, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tv_toolbar_right)
    TextView tvRevert;
    @BindView(R.id.tv_chose_color_type)
    TextView tvChoseType;
    @BindView(R.id.view_board1)
    View viewBoard1;
    @BindView(R.id.view_board2)
    View viewBoard2;
    @BindView(R.id.view_board3)
    View viewBoard3;
    @BindView(R.id.view_board4)
    View viewBoard4;
    @BindView(R.id.view_board5)
    View viewBoard5;
    @BindView(R.id.view_board6)
    View viewBoard6;
    @BindView(R.id.view_board7)
    View viewBoard7;
    private PopupWindow mPopWindow;
    private String[] mPopupItems;
    @BindView(R.id.color_picker)
    ColorPicker mColorPicker;
    @BindView(R.id.rg_color_board)
    RadioGroup radioGroup;
    @BindView(R.id.rb_board1)
    RadioButton rBBoard1;
    @BindView(R.id.rb_board2)
    RadioButton rBBoard2;
    @BindView(R.id.rb_board3)
    RadioButton rBBoard3;
    @BindView(R.id.rb_board4)
    RadioButton rBBoard4;
    @BindView(R.id.rb_board5)
    RadioButton rBBoard5;
    @BindView(R.id.rb_board6)
    RadioButton rBBoard6;
    @BindView(R.id.rb_board7)
    RadioButton rBBoard7;
    @BindView(R.id.layout_color_rgb)
    LinearLayout layoutColorRgb;
    @BindView(R.id.tv_r)
    EditText tvColorR;
    @BindView(R.id.tv_g)
    EditText tvColorG;
    @BindView(R.id.tv_b)
    EditText tvColorB;
    @BindView(R.id.tv_well)
    EditText tvColorWell;
    private EditLightPresenter mEditLightPresenter;

    @Override
    public void init() {
        setContentView(R.layout.activity_edit_light);
        ButterKnife.bind(this);
        initToolbar();
        tvTitle.setText(getIntent().getStringExtra(Utils.LIGHT_MODEL_NAME));
        tvRevert.setVisibility(View.VISIBLE);
        tvRevert.setText(getString(R.string.revert));
        initPopupWindow(getIntent().getIntExtra(Utils.LIGHT_MODEL_ID, 0));
        radioGroup.setOnCheckedChangeListener(this);
        mEditLightPresenter = new EditLightPresenterImpl(this, this, mColorPicker);
    }

    @OnClick({R.id.tv_toolbar_right, R.id.tv_chose_color_type})
    @Override
    public void onClick(View view) {
        mEditLightPresenter.viewOnclick(view, null);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        View bgView = viewBoard1;
        switch (i) {
            case R.id.view_board1:
                bgView = viewBoard1;
                break;
            case R.id.view_board2:
                bgView = viewBoard2;
                break;
            case R.id.view_board3:
                bgView = viewBoard3;
                break;
            case R.id.view_board4:
                bgView = viewBoard4;
                break;
            case R.id.view_board5:
                bgView = viewBoard5;
                break;
            case R.id.view_board6:
                bgView = viewBoard6;
                break;
            case R.id.view_board7:
                bgView = viewBoard7;
                break;
        }
        mEditLightPresenter.viewOnclick(radioButton, bgView);
    }

    public void initPopupWindow(int position) {
        mPopupItems = Utils.getPopWindowItems(this, position);
        tvChoseType.setText(mPopupItems[0]);
        View popWindowView = getLayoutInflater().inflate(R.layout.popup_window, null);
        RecyclerView popupRecyclerView = (RecyclerView) popWindowView.findViewById(R.id.recycler_view);
        popupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        popupRecyclerView.setHasFixedSize(true);
        mPopWindow = new PopupWindow(popWindowView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PopupWindowAdapter windowAdapter = new PopupWindowAdapter(mPopupItems, this, this);
        popupRecyclerView.setAdapter(windowAdapter);
    }

    @Override
    public void showPopWindow() {
        mPopWindow.showAsDropDown(tvChoseType, 0, 0, Gravity.LEFT | Gravity.TOP);
    }

    @Override
    public void setViewColor(int color) {

    }

    @Override
    public void getColorPickerRgb(int color) {

    }

    @Override
    public void onPopupWindowItemClick(int position, String type) {
        tvChoseType.setText(type);
        if (type.equals(getString(R.string.random))) {
            rBBoard1.setVisibility(View.GONE);
            rBBoard2.setVisibility(View.GONE);
            rBBoard3.setVisibility(View.GONE);
            rBBoard4.setVisibility(View.GONE);
            rBBoard5.setVisibility(View.GONE);
            rBBoard6.setVisibility(View.GONE);
            rBBoard7.setVisibility(View.GONE);
            viewBoard1.setVisibility(View.GONE);
            viewBoard2.setVisibility(View.GONE);
            viewBoard3.setVisibility(View.GONE);
            viewBoard4.setVisibility(View.GONE);
            viewBoard5.setVisibility(View.GONE);
            viewBoard6.setVisibility(View.GONE);
            viewBoard7.setVisibility(View.GONE);
        } else if (type.contains("1")) {
            rBBoard1.setVisibility(View.VISIBLE);
            rBBoard2.setVisibility(View.GONE);
            rBBoard3.setVisibility(View.GONE);
            rBBoard4.setVisibility(View.GONE);
            rBBoard5.setVisibility(View.GONE);
            rBBoard6.setVisibility(View.GONE);
            rBBoard7.setVisibility(View.GONE);
            viewBoard1.setVisibility(View.VISIBLE);
            viewBoard2.setVisibility(View.GONE);
            viewBoard3.setVisibility(View.GONE);
            viewBoard4.setVisibility(View.GONE);
            viewBoard5.setVisibility(View.GONE);
            viewBoard6.setVisibility(View.GONE);
            viewBoard7.setVisibility(View.GONE);
        } else if (type.contains("3")) {
            rBBoard1.setVisibility(View.VISIBLE);
            rBBoard2.setVisibility(View.VISIBLE);
            rBBoard3.setVisibility(View.VISIBLE);
            rBBoard4.setVisibility(View.GONE);
            rBBoard5.setVisibility(View.GONE);
            rBBoard6.setVisibility(View.GONE);
            rBBoard7.setVisibility(View.GONE);
            viewBoard1.setVisibility(View.VISIBLE);
            viewBoard2.setVisibility(View.VISIBLE);
            viewBoard3.setVisibility(View.VISIBLE);
            viewBoard4.setVisibility(View.GONE);
            viewBoard5.setVisibility(View.GONE);
            viewBoard6.setVisibility(View.GONE);
            viewBoard7.setVisibility(View.GONE);
        } else if (type.contains("7")) {
            rBBoard1.setVisibility(View.VISIBLE);
            rBBoard2.setVisibility(View.VISIBLE);
            rBBoard3.setVisibility(View.VISIBLE);
            rBBoard4.setVisibility(View.VISIBLE);
            rBBoard5.setVisibility(View.VISIBLE);
            rBBoard6.setVisibility(View.VISIBLE);
            rBBoard7.setVisibility(View.VISIBLE);
            viewBoard1.setVisibility(View.VISIBLE);
            viewBoard2.setVisibility(View.VISIBLE);
            viewBoard3.setVisibility(View.VISIBLE);
            viewBoard4.setVisibility(View.VISIBLE);
            viewBoard5.setVisibility(View.VISIBLE);
            viewBoard6.setVisibility(View.VISIBLE);
            viewBoard7.setVisibility(View.VISIBLE);
        }
        mPopWindow.dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
