package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.PurchaseBean;
import com.mvc.cryptovault_android.bean.TogeBean;
import com.mvc.cryptovault_android.event.TogeFragmentEvent;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.listener.IPayWindowListener;
import com.mvc.cryptovault_android.utils.PointLengthFilter;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.utils.ViewDrawUtils;
import com.mvc.cryptovault_android.view.ClearEditText;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.mvc.cryptovault_android.view.PopViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CrowdfundingAppointmentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBackM;
    private TextView mTitleM;
    private TextView mNumHint;
    private TextView mErrorHint;
    private LinearLayout mTitleLayoutM;
    private ImageView mInfoIconM;
    private TextView mInfoTitleM;
    private TextView mInfoMaxM;
    private TextView mInfoMinM;
    private TextView mInfoBlM;
    private RelativeLayout mInfoLayoutM;
    private ClearEditText mBwPriceM;
    private TextView mPriceM;
    private TextView mAvailableM;
    private TextView mSubmitM;
    private TogeBean.DataBean dataBean;
    private int maxPurchase;
    private int minPurchase;
    private PurchaseBean purchaseBean;
    private PopupWindow mPopView;
    private Dialog mReservationDialog;
    private DialogHelper dialogHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crewfunding;
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    protected void initData() {
        Intent intent = getIntent();
        dataBean = intent.getParcelableExtra("databean");
        Glide.with(this).load(dataBean.getProjectImage()).into(mInfoIconM);
        mInfoBlM.setText("兑换比例 1" + dataBean.getTokenName() + " = " + dataBean.getRatio() + dataBean.getBaseTokenName());
        RetrofitUtils.client(ApiStore.class).getPurchaseOnID(getToken(), dataBean.getProjectId())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(purchaseBean -> {
                    if (purchaseBean.getCode() == 200) {
                        this.purchaseBean = purchaseBean;
                        maxPurchase = purchaseBean.getData().getLimitValue();
                        minPurchase = purchaseBean.getData().getProjectMin();
                        mInfoMaxM.setText("最多预约：" + maxPurchase);
                        mInfoMinM.setText("最少预约：" + minPurchase);
                        mAvailableM.setText("可用余额：" + TextUtils.doubleToFour(purchaseBean.getData().getBalance()));
                    }
                }, throwable -> {
                    LogUtils.e("CrowdfundingAppointment", throwable.getMessage());
                });
        mBwPriceM.setFilters(new InputFilter[]{new PointLengthFilter()});
        mBwPriceM.addTextChangedListener(new EditTextChange() {
            @TargetApi(Build.VERSION_CODES.M)
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String updateTv = s.toString();
                if (!updateTv.equals("")) {
                    ViewDrawUtils.setRigthDraw(getDrawable(R.drawable.clean_icon_edit), mBwPriceM);
                    Double currentNum = Double.valueOf(updateTv);
                    if (currentNum > maxPurchase) {
                        mNumHint.setText("超出最大预约数量！");
                        mNumHint.setVisibility(View.VISIBLE);
                        mSubmitM.setEnabled(false);
                    } else if (currentNum < minPurchase) {
                        mNumHint.setText("低于最小预约数量！");
                        mNumHint.setVisibility(View.VISIBLE);
                        mSubmitM.setEnabled(false);
                    } else if (currentNum == 0) {
                        mSubmitM.setEnabled(false);
                    } else {
                        mNumHint.setVisibility(View.INVISIBLE);
                        mSubmitM.setEnabled(true);
                    }
                    mPriceM.setText(TextUtils.doubleToDouble(currentNum * dataBean.getRatio()));
                    if (currentNum * dataBean.getRatio() > purchaseBean.getData().getBalance()) {
                        mAvailableM.setText("可用金额不足！");
                        mAvailableM.setTextColor(getColor(R.color.red));
                        mSubmitM.setEnabled(false);
                    } else {
                        if (purchaseBean != null) {
                            mAvailableM.setText("可用余额：" + TextUtils.doubleToFour(purchaseBean.getData().getBalance()));
                            mAvailableM.setTextColor(getColor(R.color.trand_gray));
                            mSubmitM.setEnabled(true);
                        }
                    }
                } else {
                    ViewDrawUtils.clearDraw(mBwPriceM);
                    mNumHint.setVisibility(View.INVISIBLE);
                    mPriceM.setText("0.00");
                    if (purchaseBean != null) {
                        mAvailableM.setText("可用余额：" + TextUtils.doubleToFour(purchaseBean.getData().getBalance()));
                        mAvailableM.setTextColor(getColor(R.color.trand_gray));
                        mSubmitM.setEnabled(true);
                    }
                }
            }
        });
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mBackM = findViewById(R.id.m_back);
        mTitleM = findViewById(R.id.m_title);
        mNumHint = findViewById(R.id.num_hint);
        mErrorHint = findViewById(R.id.error_hint);
        mTitleLayoutM = findViewById(R.id.m_title_layout);
        mInfoIconM = findViewById(R.id.m_info_icon);
        mInfoTitleM = findViewById(R.id.m_info_title);
        mInfoMaxM = findViewById(R.id.m_info_max);
        mInfoMinM = findViewById(R.id.m_info_min);
        mInfoBlM = findViewById(R.id.m_info_bl);
        mInfoLayoutM = findViewById(R.id.m_info_layout);
        mBwPriceM = findViewById(R.id.m_bw_price);
        mPriceM = findViewById(R.id.m_price);
        mAvailableM = findViewById(R.id.m_available);
        mSubmitM = findViewById(R.id.m_submit);
        mBackM.setOnClickListener(this);
        mSubmitM.setOnClickListener(this);
        mBwPriceM.setFocusable(true);
        mBwPriceM.setFocusableInTouchMode(true);
        mBwPriceM.requestFocus();
        dialogHelper = DialogHelper.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                // TODO 18/12/12
                if (mPopView != null) {
                    KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                }
                finish();
                break;
            case R.id.m_submit:
                // TODO 18/12/12
                String currentNum = mBwPriceM.getText().toString();
                if (currentNum.equals("")) {
                    Toast.makeText(this, "购买数量不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPopView = PopViewHelper.getInstance()
                        .create(this
                                , R.layout.layout_paycode
                                , "确认预约"
                                , "总计需支付"
                                , mPriceM.getText().toString() + dataBean.getBaseTokenName()
                                , currentNum + dataBean.getTokenName()
                                , new IPayWindowListener() {
                                    @Override
                                    public void onclick(View view) {
                                        switch (view.getId()) {
                                            case R.id.pay_close:
                                                mPopView.dismiss();
                                                Toast.makeText(CrowdfundingAppointmentActivity.this, "取消交易", Toast.LENGTH_SHORT).show();
                                                break;
                                            case R.id.pay_text:
                                                KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                                setAlpha(0.5f);
                                                break;
                                            case R.id.pay_forget:
                                                dialogHelper.create(CrowdfundingAppointmentActivity.this, R.layout.layout_forgetpwd_dialog).show();
                                                dialogHelper.dismissDelayed(null, 2000);
                                                break;
                                        }
                                    }

                                    @Override
                                    public void dismiss() {
                                        setAlpha(1f);
                                    }
                                }, num -> {
                                    KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                    mPopView.dismiss();
                                    mReservationDialog = dialogHelper.create(CrowdfundingAppointmentActivity.this, R.drawable.pending_icon, "正在预约");
                                    mReservationDialog.show();
                                    try {
                                        sendReservationRequest(currentNum, num);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                mPopView.showAtLocation(mSubmitM, Gravity.BOTTOM, 0, 0);
                mPopView.getContentView().post(() ->
                        KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text))
                );
                setAlpha(0.5f);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void sendReservationRequest(String currentNum, String num) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("password", num);
        object.put("value", currentNum);
        RequestBody body = RequestBody.create(MediaType.parse("text/html"), object.toString());
        RetrofitUtils.client(ApiStore.class).sendReservationRequest(getToken(), body, dataBean.getProjectId())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(updateBean -> {
                    EventBus.getDefault().post(new TogeFragmentEvent());
                    if (updateBean.getCode() == 200) {
                        dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.success_icon, "预约成功");
                        new Handler().postDelayed(() -> {
                            mReservationDialog.dismiss();
                            mErrorHint.setVisibility(View.INVISIBLE);
                            finish();
                        }, 1000);
                    } else {
                        dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.miss_icon, "预约失败");
                        mErrorHint.setVisibility(View.VISIBLE);
                        mErrorHint.setText(updateBean.getMessage());
                        new Handler().postDelayed(() -> {
                            mReservationDialog.dismiss();
                            mErrorHint.setVisibility(View.INVISIBLE);
                        }, 2000);
                    }
                }, throwable -> {
                    dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.miss_icon, "预约失败");
                    mErrorHint.setVisibility(View.VISIBLE);
                    mErrorHint.setText(throwable.getMessage());
                    new Handler().postDelayed(() -> {
                        mReservationDialog.dismiss();
                        mErrorHint.setVisibility(View.INVISIBLE);
                    }, 2000);
                });
    }
}
