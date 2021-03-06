package com.example.txtledbluetooth.light;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.base.BaseActivity;
import com.example.txtledbluetooth.light.presenter.EditLightPresenter;
import com.example.txtledbluetooth.light.presenter.EditLightPresenterImpl;
import com.example.txtledbluetooth.light.view.EditLightView;
import com.example.txtledbluetooth.utils.BleCommandUtils;
import com.example.txtledbluetooth.utils.Utils;
import com.example.txtledbluetooth.widget.ColorPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditLightActivity extends BaseActivity implements View.OnClickListener,
        PopupWindowAdapter.OnPopupItemClickListener, EditLightView, RadioGroup.
                OnCheckedChangeListener, TextView.OnEditorActionListener,
        SeekBar.OnSeekBarChangeListener {
    private static final int START_SORT = 1;
    private static final int SORT_DELAY_MILLISECONDS = 300;
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
    RadioButton rbBoard1;
    @BindView(R.id.rb_board2)
    RadioButton rbBoard2;
    @BindView(R.id.rb_board3)
    RadioButton rbBoard3;
    @BindView(R.id.rb_board4)
    RadioButton rbBoard4;
    @BindView(R.id.rb_board5)
    RadioButton rbBoard5;
    @BindView(R.id.rb_board6)
    RadioButton rbBoard6;
    @BindView(R.id.rb_board7)
    RadioButton rbBoard7;
    @BindView(R.id.layout_color_rgb)
    LinearLayout layoutColorRgb;
    @BindView(R.id.layout_speed)
    LinearLayout layoutSpeed;
    @BindView(R.id.et_r)
    EditText etColorR;
    @BindView(R.id.et_g)
    EditText etColorG;
    @BindView(R.id.et_b)
    EditText etColorB;
    @BindView(R.id.et_well)
    EditText etColorWell;
    @BindView(R.id.sb_speed)
    SeekBar seekBarSpeed;
    @BindView(R.id.sb_brightness)
    SeekBar seekBarBright;
    private View mBgView;
    private EditLightPresenter mEditLightPresenter;
    private int mPosition;
    private int mSpeed;
    private int mBrightness;
    private long mFirstDrag;
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == START_SORT) {
                if ((System.currentTimeMillis() - mFirstDrag) >= SORT_DELAY_MILLISECONDS) {
                    mEditLightPresenter.updateLightColor(BleCommandUtils.getLightNo(mPosition),
                            (int) radioGroup.getTag(), msg.obj.toString());
                }
            }

        }
    };

    @Override
    public void init() {
        setContentView(R.layout.activity_edit_light);
        ButterKnife.bind(this);
        initToolbar();
        tvTitle.setText(getIntent().getStringExtra(Utils.LIGHT_MODEL_NAME));
        tvRevert.setVisibility(View.VISIBLE);
        tvRevert.setText(getString(R.string.revert));
        mPosition = getIntent().getIntExtra(Utils.LIGHT_MODEL_ID, 0);
        initPopupWindow();
        radioGroup.setOnCheckedChangeListener(this);
        mEditLightPresenter = new EditLightPresenterImpl(this, this, mColorPicker);

        onPopupWindowItemClick(0, tvChoseType.getText().toString());
        etColorWell.setOnEditorActionListener(this);
        seekBarSpeed.setOnSeekBarChangeListener(this);
        seekBarBright.setOnSeekBarChangeListener(this);
        setViewBoardDefaultColor();
    }

    @OnClick({R.id.tv_toolbar_right, R.id.tv_chose_color_type})
    @Override
    public void onClick(View view) {
        mEditLightPresenter.viewOnclick(view, viewBoard1);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        mBgView = viewBoard1;
        switch (i) {
            case R.id.rb_board1:
                radioGroup.setTag(0);
                mBgView = viewBoard1;
                break;
            case R.id.rb_board2:
                radioGroup.setTag(1);
                mBgView = viewBoard2;
                break;
            case R.id.rb_board3:
                radioGroup.setTag(2);
                mBgView = viewBoard3;
                break;
            case R.id.rb_board4:
                radioGroup.setTag(3);
                mBgView = viewBoard4;
                break;
            case R.id.rb_board5:
                radioGroup.setTag(4);
                mBgView = viewBoard5;
                break;
            case R.id.rb_board6:
                radioGroup.setTag(5);
                mBgView = viewBoard6;
                break;
            case R.id.rb_board7:
                radioGroup.setTag(6);
                mBgView = viewBoard7;
                break;
        }
        mEditLightPresenter.viewOnclick(radioButton, mBgView);
    }

    public void initPopupWindow() {
        mPopupItems = Utils.getPopWindowItems(this, mPosition);
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
    public void setTvColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        String r1 = Integer.toHexString(r);
        String g1 = Integer.toHexString(g);
        String b1 = Integer.toHexString(b);
        String colorStr = r1 + g1 + b1;
        etColorR.setText(r + "");
        etColorG.setText(g + "");
        etColorB.setText(b + "");
        etColorWell.setText(colorStr);

        mFirstDrag = System.currentTimeMillis();
        Message message = mHandler.obtainMessage();
        message.what = START_SORT;
//        message.obj = "(" + r + "," + g + "," + b + ")";
        message.obj = colorStr;
        mHandler.sendMessageDelayed(message, SORT_DELAY_MILLISECONDS);

    }

    @Override
    public void revertColor() {
        radioGroup.check(R.id.rb_board1);
        setViewBoardDefaultColor();
        setEtDefaultData();
        mEditLightPresenter.updateLightColor(BleCommandUtils.getLightNo(mPosition),
                (int) radioGroup.getTag(), getString(R.string.red_color));
    }

    private void setViewBoardDefaultColor() {
        viewBoard1.setBackgroundColor(getResources().getColor(R.color.red));
        viewBoard2.setBackgroundColor(getResources().getColor(R.color.orange));
        viewBoard3.setBackgroundColor(getResources().getColor(R.color.yellow));
        viewBoard4.setBackgroundColor(getResources().getColor(R.color.green));
        viewBoard5.setBackgroundColor(getResources().getColor(R.color.blue));
        viewBoard6.setBackgroundColor(getResources().getColor(R.color.indigo));
        viewBoard7.setBackgroundColor(getResources().getColor(R.color.purple));
    }

    @Override
    public void onPopupWindowItemClick(int position, String type) {
        tvChoseType.setText(type);
        radioGroup.check(R.id.rb_board1);
        initEditLightUi(type);
        initBleLightColor(position);
        mPopWindow.dismiss();
    }

    private void initBleLightColor(int position) {
        mEditLightPresenter.initBleLightColor(BleCommandUtils.getLightNo(mPosition), position);
    }

    private void initEditLightUi(String type) {
        if (type.equals(getString(R.string.random)) || type.contains(getString(R.string.white)) || type.contains(getString(R.string.default_)) ||
                type.contains(getString(R.string.moon_light)) || type.contains(getString(R.string.full))) {
            mEditLightPresenter.setIsSetOnColorSelectListener(false);
            //fireworks
            if (mPosition == 1 || mPosition == 2 || mPosition == 3 || mPosition == 4) {
                layoutSpeed.setVisibility(View.VISIBLE);
            } else {
                layoutSpeed.setVisibility(View.GONE);
            }
            if (mPosition == 6) {
                rbBoard1.setVisibility(View.VISIBLE);
                rbBoard2.setVisibility(View.VISIBLE);
                rbBoard3.setVisibility(View.VISIBLE);
                rbBoard4.setVisibility(View.VISIBLE);
                rbBoard5.setVisibility(View.VISIBLE);
                rbBoard6.setVisibility(View.GONE);
                rbBoard7.setVisibility(View.GONE);
                viewBoard1.setVisibility(View.VISIBLE);
                viewBoard2.setVisibility(View.VISIBLE);
                viewBoard3.setVisibility(View.VISIBLE);
                viewBoard4.setVisibility(View.VISIBLE);
                viewBoard5.setVisibility(View.VISIBLE);
                viewBoard6.setVisibility(View.GONE);
                viewBoard7.setVisibility(View.GONE);
            } else {
                rbBoard1.setVisibility(View.GONE);
                rbBoard2.setVisibility(View.GONE);
                rbBoard3.setVisibility(View.GONE);
                rbBoard4.setVisibility(View.GONE);
                rbBoard5.setVisibility(View.GONE);
                rbBoard6.setVisibility(View.GONE);
                rbBoard7.setVisibility(View.GONE);
                viewBoard1.setVisibility(View.GONE);
                viewBoard2.setVisibility(View.GONE);
                viewBoard3.setVisibility(View.GONE);
                viewBoard4.setVisibility(View.GONE);
                viewBoard5.setVisibility(View.GONE);
                viewBoard6.setVisibility(View.GONE);
                viewBoard7.setVisibility(View.GONE);
            }
            if (mPosition == 0 || mPosition == 1 || mPosition == 2 || mPosition == 3) {
//                tvRevert.setClickable(false);
                setEtNoData();
            }
        } else if (type.contains("1") || type.contains(getString(R.string.colored))) {
            mEditLightPresenter.setIsSetOnColorSelectListener(true);
            //moonlight
            if (mPosition == 0) {
                layoutSpeed.setVisibility(View.GONE);
            } else {
                layoutSpeed.setVisibility(View.VISIBLE);
            }
            rbBoard1.setVisibility(View.VISIBLE);
            rbBoard2.setVisibility(View.GONE);
            rbBoard3.setVisibility(View.GONE);
            rbBoard4.setVisibility(View.GONE);
            rbBoard5.setVisibility(View.GONE);
            rbBoard6.setVisibility(View.GONE);
            rbBoard7.setVisibility(View.GONE);
            viewBoard1.setVisibility(View.VISIBLE);
            viewBoard2.setVisibility(View.GONE);
            viewBoard3.setVisibility(View.GONE);
            viewBoard4.setVisibility(View.GONE);
            viewBoard5.setVisibility(View.GONE);
            viewBoard6.setVisibility(View.GONE);
            viewBoard7.setVisibility(View.GONE);
        } else if (type.contains("2")) {
            mEditLightPresenter.setIsSetOnColorSelectListener(true);
            layoutSpeed.setVisibility(View.VISIBLE);
            rbBoard1.setVisibility(View.VISIBLE);
            rbBoard2.setVisibility(View.VISIBLE);
            rbBoard3.setVisibility(View.GONE);
            rbBoard4.setVisibility(View.GONE);
            rbBoard5.setVisibility(View.GONE);
            rbBoard6.setVisibility(View.GONE);
            rbBoard7.setVisibility(View.GONE);
            viewBoard1.setVisibility(View.VISIBLE);
            viewBoard2.setVisibility(View.VISIBLE);
            viewBoard3.setVisibility(View.GONE);
            viewBoard4.setVisibility(View.GONE);
            viewBoard5.setVisibility(View.GONE);
            viewBoard6.setVisibility(View.GONE);
            viewBoard7.setVisibility(View.GONE);
        } else if (type.contains("3")) {
            mEditLightPresenter.setIsSetOnColorSelectListener(true);
            layoutSpeed.setVisibility(View.VISIBLE);
            rbBoard1.setVisibility(View.VISIBLE);
            rbBoard2.setVisibility(View.VISIBLE);
            rbBoard3.setVisibility(View.VISIBLE);
            rbBoard4.setVisibility(View.GONE);
            rbBoard5.setVisibility(View.GONE);
            rbBoard6.setVisibility(View.GONE);
            rbBoard7.setVisibility(View.GONE);
            viewBoard1.setVisibility(View.VISIBLE);
            viewBoard2.setVisibility(View.VISIBLE);
            viewBoard3.setVisibility(View.VISIBLE);
            viewBoard4.setVisibility(View.GONE);
            viewBoard5.setVisibility(View.GONE);
            viewBoard6.setVisibility(View.GONE);
            viewBoard7.setVisibility(View.GONE);
        } else if (type.contains("7")) {
            mEditLightPresenter.setIsSetOnColorSelectListener(true);
            layoutSpeed.setVisibility(View.VISIBLE);
            rbBoard1.setVisibility(View.VISIBLE);
            rbBoard2.setVisibility(View.VISIBLE);
            rbBoard3.setVisibility(View.VISIBLE);
            rbBoard4.setVisibility(View.VISIBLE);
            rbBoard5.setVisibility(View.VISIBLE);
            rbBoard6.setVisibility(View.VISIBLE);
            rbBoard7.setVisibility(View.VISIBLE);
            viewBoard1.setVisibility(View.VISIBLE);
            viewBoard2.setVisibility(View.VISIBLE);
            viewBoard3.setVisibility(View.VISIBLE);
            viewBoard4.setVisibility(View.VISIBLE);
            viewBoard5.setVisibility(View.VISIBLE);
            viewBoard6.setVisibility(View.VISIBLE);
            viewBoard7.setVisibility(View.VISIBLE);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mColorPicker.isRecycled()) {
            mColorPicker.recycle();
        }
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        String[] temp = etColorWell.getText().toString().split("");
        if (temp.length == 7) {
            String strR = Integer.valueOf(temp[1] + temp[2], 16).toString();
            String strG = Integer.valueOf(temp[3] + temp[4], 16).toString();
            String strB = Integer.valueOf(temp[5] + temp[6], 16).toString();
            etColorR.setText(strR);
            etColorG.setText(strG);
            etColorB.setText(strB);
            mBgView.setBackgroundColor(Color.rgb(Integer.parseInt(strR), Integer.parseInt(strG),
                    Integer.parseInt(strB)));
        } else {
            Toast.makeText(this, R.string.color_value_hint, Toast.LENGTH_SHORT).show();
        }
        etColorWell.setCursorVisible(false);
        Utils.hideKeyboard(this);
        return false;
    }

    public void setEtNoData() {
        etColorR.setText("");
        etColorG.setText("");
        etColorB.setText("");
        etColorWell.setText("");
    }

    private void setEtDefaultData() {
        etColorR.setText("255");
        etColorG.setText("0");
        etColorB.setText("0");
        etColorWell.setText("ff0000");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar.getId() == R.id.sb_speed) {
            mSpeed = i;
        } else if (seekBar.getId() == R.id.sb_brightness) {
            mBrightness = i;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.sb_speed) {
            mEditLightPresenter.setLightSpeed(BleCommandUtils.getLightNo(mPosition), mSpeed);
        } else if (seekBar.getId() == R.id.sb_brightness) {
            mEditLightPresenter.setLightBrightness(BleCommandUtils.getLightNo(mPosition),
                    mBrightness);
        }
    }
}
