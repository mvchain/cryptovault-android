package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.MyApplication;
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

import static com.mvc.cryptovault_android.common.Constant.SP.UPDATE_PASSWORD_TYPE;
import static com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL;

public class CrowdfundingAppointmentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBackM;
    private TextView mTitleM;
    private TextView mNumHint;
    private LinearLayout mTitleLayoutM;
    private ImageView mInfoIconM;
    private TextView mInfoTitleM;
    private TextView mInfoMaxM;
    private TextView mInfoBlM;
    private LinearLayout mInfoLayoutM;
    private ClearEditText mBwPriceM;
    private TextView mPriceM;
    private TextView mAvailableM;
    private TextView mSubmitM;
    private TextView mPriceType;
    private TogeBean.DataBean dataBean;
    private double maxPurchase;
    private double minPurchase;
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
        mTitleM.setText("预约" + dataBean.getProjectName());
        mInfoTitleM.setText(dataBean.getProjectName());
        RequestOptions options = new RequestOptions().fallback(R.drawable.default_project).placeholder(R.drawable.loading_img).error(R.drawable.default_project);
        Glide.with(this).load(dataBean.getProjectImage()).apply(options).into(mInfoIconM);
        mInfoBlM.setText("兑换比例：1 " + dataBean.getTokenName() + " = " + dataBean.getRatio() + dataBean.getBaseTokenName());
        mPriceType.setText(dataBean.getBaseTokenName());
        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getPurchaseOnID(MyApplication.getTOKEN(), dataBean.getProjectId())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(purchaseBean -> {
                    if (purchaseBean.getCode() == 200) {
                        this.purchaseBean = purchaseBean;
                        maxPurchase = purchaseBean.getData().getLimitValue();
                        minPurchase = purchaseBean.getData().getProjectMin();
                        mInfoMaxM.setText("限购额：" + TextUtils.doubleToInt(dataBean.getProjectLimit() - maxPurchase) + "/" + TextUtils.doubleToInt(dataBean.getProjectLimit()));
                        mAvailableM.setText("可用" + dataBean.getBaseTokenName() + "：" + TextUtils.doubleToEight(purchaseBean.getData().getBalance()));
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
                    ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(getBaseContext(), R.drawable.clean_icon_edit), mBwPriceM);
                    Double currentNum = Double.valueOf(updateTv);
                    mPriceM.setText(TextUtils.doubleToEight(currentNum * dataBean.getRatio()));
                    if (currentNum * dataBean.getRatio() > purchaseBean.getData().getBalance()) {
                        mAvailableM.setText("可用" + dataBean.getBaseTokenName() + "不足！");
                        mAvailableM.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.red));
                        mSubmitM.setEnabled(false);
                        mSubmitM.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
                    } else {
                        if (purchaseBean != null) {
                            mAvailableM.setText("可用" + dataBean.getBaseTokenName() + "：" + TextUtils.doubleToEight(purchaseBean.getData().getBalance()));
                            mAvailableM.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.trand_gray));
                            mSubmitM.setEnabled(true);
                            mSubmitM.setBackgroundResource(R.drawable.bg_login_submit);
                        }
                    }
                    if (currentNum > maxPurchase) {
                        mNumHint.setText("超出最大预约数量！");
                        mNumHint.setVisibility(View.VISIBLE);
                        mSubmitM.setEnabled(false);
                        mSubmitM.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
                    } else if (currentNum < minPurchase) {
                        mNumHint.setText("最小预约数量：" + TextUtils.doubleToInt(minPurchase));
                        mNumHint.setVisibility(View.VISIBLE);
                        mSubmitM.setEnabled(false);
                        mSubmitM.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
                    } else if (currentNum == 0) {
                        mSubmitM.setEnabled(false);
                    } else {
                        mNumHint.setVisibility(View.INVISIBLE);
                        mSubmitM.setEnabled(true);
                        mSubmitM.setBackgroundResource(R.drawable.bg_login_submit);
                    }
                } else {
                    ViewDrawUtils.clearDraw(mBwPriceM);
                    mAvailableM.setText("可用" + dataBean.getBaseTokenName() + "：" + TextUtils.doubleToEight(purchaseBean.getData().getBalance()));
                    mAvailableM.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.trand_gray));
                    mNumHint.setVisibility(View.INVISIBLE);
                    mPriceM.setText("0.0000");
                    mSubmitM.setEnabled(false);
                    mSubmitM.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
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
        mTitleLayoutM = findViewById(R.id.m_title_layout);
        mInfoIconM = findViewById(R.id.m_info_icon);
        mInfoTitleM = findViewById(R.id.m_info_title);
        mInfoMaxM = findViewById(R.id.m_info_max);
        mInfoBlM = findViewById(R.id.m_info_bl);
        mInfoLayoutM = findViewById(R.id.m_info_layout);
        mBwPriceM = findViewById(R.id.m_bw_price);
        mPriceM = findViewById(R.id.m_price);
        mAvailableM = findViewById(R.id.m_available);
        mSubmitM = findViewById(R.id.m_submit);
        mPriceType = findViewById(R.id.price_type);
        mBackM.setOnClickListener(this);
        mSubmitM.setOnClickListener(this);
        mBwPriceM.setFocusable(true);
        mBwPriceM.setFocusableInTouchMode(true);
        mBwPriceM.requestFocus();
        dialogHelper = DialogHelper.Companion.getInstance();
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
                    ToastUtils.showLong("购买数量不可为空");
                    return;
                }
                mPopView = PopViewHelper.Companion.getInstance()
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
                                                ToastUtils.showLong("取消交易");
                                                break;
                                            case R.id.pay_text:
                                                KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                                setAlpha(0.5f);
                                                break;
                                            case R.id.pay_forget:
                                                SPUtils.getInstance().put(UPDATE_PASSWORD_TYPE, "2");
                                                startActivity(ForgetPasswordActivity.class);
                                                KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                                break;
                                        }
                                    }

                                    @Override
                                    public void dismiss() {
                                        setAlpha(1f);
                                    }
                                }, num -> {
                                    KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                    String email = SPUtils.getInstance().getString(USER_EMAIL);
                                    mPopView.dismiss();
                                    mReservationDialog = dialogHelper.create(CrowdfundingAppointmentActivity.this, R.drawable.pending_icon, "正在预约");
                                    mReservationDialog.show();
                                    try {
                                        sendReservationRequest(currentNum, EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(num)));
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
        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).sendReservationRequest(MyApplication.getTOKEN(), body, dataBean.getProjectId())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(updateBean -> {
                    EventBus.getDefault().post(new TogeFragmentEvent());
                    if (updateBean.getCode() == 200) {
                        dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.success_icon, "预约成功");
                        new Handler().postDelayed(() -> {
                            mReservationDialog.dismiss();
                            finish();
                        }, 1000);
                    } else {
                        if (!NetworkUtils.isConnected()) {
                            dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.miss_icon, "无网络连接，请检查网络");

                        } else {
                            dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.miss_icon, "预约失败");
                        }
                        new Handler().postDelayed(() -> {
                            mReservationDialog.dismiss();
                        }, 2000);
                    }
                }, throwable -> {
                    if (!NetworkUtils.isConnected()) {
                        dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.miss_icon, "无网络连接，请检查网络");
                    } else {
                        dialogHelper.resetDialogResource(CrowdfundingAppointmentActivity.this, R.drawable.miss_icon, "预约失败");
                    }
                    new Handler().postDelayed(() -> {
                        mReservationDialog.dismiss();
                    }, 2000);
                });
    }
}
