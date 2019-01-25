package com.mvc.cryptovault_android.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.IDToTransferBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.BTCTransferContract;
import com.mvc.cryptovault_android.event.HistroyEvent;
import com.mvc.cryptovault_android.event.HistroyFragmentEvent;
import com.mvc.cryptovault_android.event.WalletMsgEvent;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.listener.IPayWindowListener;
import com.mvc.cryptovault_android.presenter.BTCTransferPresenter;
import com.mvc.cryptovault_android.utils.PointLengthFilter;
import com.mvc.cryptovault_android.utils.RxgularUtils;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.utils.ViewDrawUtils;
import com.mvc.cryptovault_android.view.ClearEditText;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.mvc.cryptovault_android.view.PopViewHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import static com.mvc.cryptovault_android.common.Constant.SP.UPDATE_PASSWORD_TYPE;
import static com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL;

public class BTCTransferActivity extends BaseMVPActivity<BTCTransferContract.BTCTransferPresenter> implements BTCTransferContract.BTCTransferView, View.OnClickListener {
    private ImageView mBackM;
    private TextView mTitleM;
    private ImageView mQCodeM;
    private LinearLayout mTitleLayoutBtc;
    private ClearEditText mTransAddressBtc;
    private EditText mTransPriceBtc;
    private TextView mPriceBtc;
    private TextView mSxfBtc;
    private TextView mSubmitBtc;
    private String hash;
    private int tokenId;
    private IDToTransferBean.DataBean mTransBean;
    private PopupWindow mPopView;
    private String tokenName;
    private DialogHelper dialogHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_btc_transfer;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return BTCTransferPresenter.newIntance();
    }

    @Override
    protected void initMVPData() {
        mTransAddressBtc.setText(hash);
        mPresenter.getDetail(tokenId);
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        dialogHelper = DialogHelper.getInstance();
        hash = getIntent().getStringExtra("hash");
        tokenName = getIntent().getStringExtra("tokenName");
        tokenId = getIntent().getIntExtra("tokenId", 0);
        mBackM = findViewById(R.id.m_back);
        mTitleM = findViewById(R.id.m_title);
        mQCodeM = findViewById(R.id.m_qcode);
        mTitleLayoutBtc = findViewById(R.id.vp_title_layout);
        mTransAddressBtc = findViewById(R.id.btc_trans_address);
        mTransPriceBtc = findViewById(R.id.btc_trans_price);
        mPriceBtc = findViewById(R.id.btc_price);
        mSxfBtc = findViewById(R.id.btc_sxf);
        mSubmitBtc = findViewById(R.id.btc_submit);
        mTitleM.setText(tokenName + "转账");
        mTransPriceBtc.setFilters(new InputFilter[]{new PointLengthFilter()});
        mTransPriceBtc.addTextChangedListener(new EditTextChange() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String chagePrice = s.toString();
                if (!chagePrice.equals("") && mTransBean != null) {
                    if (TextUtils.stringToDouble(chagePrice) > mTransBean.getBalance()) {
                        mPriceBtc.setText("余额不足");
                        mPriceBtc.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.red));
                        mSubmitBtc.setEnabled(false);
                    } else {
                        mPriceBtc.setText(String.format("可用%s：" + TextUtils.doubleToFour(mTransBean.getBalance()), tokenName));
                        mPriceBtc.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.login_edit_bg));
                        mSubmitBtc.setEnabled(true);
                    }
                }
            }
        });
        mTransAddressBtc.addTextChangedListener(new EditTextChange() {
            @TargetApi(Build.VERSION_CODES.M)
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String updateTv = s.toString();
                if (!updateTv.equals("")) {
                    ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(getBaseContext(), R.drawable.clean_icon_edit), mTransAddressBtc);
                } else {
                    ViewDrawUtils.clearDraw(mTransAddressBtc);
                }
            }
        });
        mBackM.setOnClickListener(this);
        mQCodeM.setOnClickListener(this);
        mSubmitBtc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                // TODO 18/12/07
                finish();
                break;
            case R.id.m_qcode:
                // TODO 18/12/07
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(new IPermissionRequest() {
                        @Override
                        public void toSetting() {
                            RsPermission.getInstance().toSettingPer();
                        }

                        @Override
                        public void cancle(int i) {
                            ToastUtils.showLong("未给予相机权限将无法扫描二维码");
                        }

                        @Override
                        public void success(int i) {
                            Intent intent = new Intent(BTCTransferActivity.this, QCodeActivity.class);
                            intent.putExtra("tokenId", tokenId);
                            startActivityForResult(intent, 200);
                        }
                    }).requestPermission(this, Manifest.permission.CAMERA);
                } else {
                    Intent intent = new Intent(BTCTransferActivity.this, QCodeActivity.class);
                    intent.putExtra("tokenId", tokenId);
                    startActivityForResult(intent, 200);
                }
                break;
            case R.id.btc_submit:
                // TODO 18/12/07
                String transAddress = mTransAddressBtc.getText().toString();
                String priceBtc = mTransPriceBtc.getText().toString();
                if (transAddress.equals("")) {
                    dialogHelper.create(this, R.drawable.miss_icon, "收款地址不能为空").show();
                    dialogHelper.dismissDelayed(null, 2000);
                    return;
                }
                if (priceBtc.equals("")) {
                    dialogHelper.create(this, R.drawable.miss_icon, "转账金额不能为空").show();
                    dialogHelper.dismissDelayed(null, 2000);
                    return;
                }
                if (tokenId == 4 || tokenId == 2) {
                    if (!RxgularUtils.isBTC(transAddress.trim())) {
                        dialogHelper.create(this, R.drawable.miss_icon, "无效地址").show();
                        dialogHelper.dismissDelayed(null, 2000);
                        return;
                    }
                } else {
                    if (!RxgularUtils.isETH(transAddress.trim())) {
                        dialogHelper.create(this, R.drawable.miss_icon, "无效地址").show();
                        dialogHelper.dismissDelayed(null, 2000);
                        return;
                    }
                }

//                startActivity(PayCodeActivity.class);
                mPopView = PopViewHelper.getInstance()
                        .create(this
                                , R.layout.layout_paycode
                                , "确认转账"
                                , "转账金额"
                                , priceBtc + mTransBean.getFeeTokenName()
                                , transAddress
                                , TextUtils.doubleToSix(mTransBean.getFee()) + mTransBean.getFeeTokenName()
                                , true
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
                                    dialogHelper.create(this, R.drawable.pending_icon, "转账中").show();
                                    String email = SPUtils.getInstance().getString(USER_EMAIL);
                                    KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                    mPresenter.sendTransferMsg(transAddress.trim(), EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(num)), tokenId, priceBtc);
                                    mPopView.dismiss();
                                });
                mPopView.showAtLocation(mSubmitBtc, Gravity.BOTTOM, 0, 0);
                mPopView.getContentView().post(() ->
                        KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text))
                );
                setAlpha(0.5f);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case 200:
                    boolean qode = data.getBooleanExtra("QODE", false);
                    if (!qode) {
                        dialogHelper.create(this, R.drawable.miss_icon, "无效地址").show();
                        dialogHelper.dismissDelayed(null, 2000);
                        return;
                    }
                    String hash = data.getStringExtra(CodeUtils.RESULT_STRING);
                    mTransAddressBtc.setText(hash.trim());
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showSuccess(IDToTransferBean.DataBean data) {
        this.mTransBean = data;
        mPriceBtc.setText(String.format("可用%s：" + TextUtils.doubleToFour(data.getBalance()), tokenName));
        mSxfBtc.setText(TextUtils.doubleToSix(data.getFee()) + " " + data.getFeeTokenName());
    }

    @Override
    public void transferCallBack(UpdateBean bean) {
        if (bean.getCode() == 200) {
            dialogHelper.resetDialogResource(this, R.drawable.success_icon, "操作成功");
            dialogHelper.dismissDelayed(() -> {
                EventBus.getDefault().post(new HistroyEvent(mTransPriceBtc.getText().toString().trim()));
                EventBus.getDefault().post(new HistroyFragmentEvent());
                EventBus.getDefault().post(new WalletMsgEvent());
                finish();
            }, 3000);
        } else {
            dialogHelper.resetDialogResource(this, R.drawable.miss_icon, bean.getMessage());
            dialogHelper.dismissDelayed(null, 2000);
        }
    }
}
