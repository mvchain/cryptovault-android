package com.mvc.cryptovault_android.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.IDToTransferBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.BTCTransferContract;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.listener.IPayWindowListener;
import com.mvc.cryptovault_android.presenter.BTCTransferPresenter;
import com.mvc.cryptovault_android.utils.PointLengthFilter;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.mvc.cryptovault_android.view.PopViewHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class BTCTransferActivity extends BaseMVPActivity<BTCTransferContract.BTCTransferPresenter> implements BTCTransferContract.BTCTransferView, View.OnClickListener {
    private ImageView mBackM;
    private TextView mTitleM;
    private ImageView mQCodeM;
    private LinearLayout mTitleLayoutBtc;
    private EditText mTransAddressBtc;
    private EditText mTransPriceBtc;
    private TextView mPriceBtc;
    private TextView mSxfBtc;
    private TextView mSubmitBtc;
    private String hash;
    private int tokenId;
    private IDToTransferBean.DataBean mTransBean;
    private PopupWindow mPopView;

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
        hash = getIntent().getStringExtra("hash");
        tokenId = getIntent().getIntExtra("tokenId", 0);
        mTransAddressBtc.setText(hash);
        mPresenter.getDetail(getToken(), tokenId);
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mBackM = findViewById(R.id.m_back);
        mTitleM = findViewById(R.id.m_title);
        mQCodeM = findViewById(R.id.m_qcode);
        mTitleLayoutBtc = findViewById(R.id.vp_title_layout);
        mTransAddressBtc = findViewById(R.id.btc_trans_address);
        mTransPriceBtc = findViewById(R.id.btc_trans_price);
        mPriceBtc = findViewById(R.id.btc_price);
        mSxfBtc = findViewById(R.id.btc_sxf);
        mSubmitBtc = findViewById(R.id.btc_submit);
        mTransPriceBtc.setFilters(new InputFilter[]{new PointLengthFilter()});
        mTransPriceBtc.addTextChangedListener(new EditTextChange() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String chagePrice = s.toString();
                if (!chagePrice.equals("")) {
                    if (TextUtils.stringToDouble(chagePrice) > mTransBean.getBalance()) {
                        mPriceBtc.setText("余额不足");
                        mPriceBtc.setTextColor(getColor(R.color.red));
                        mSubmitBtc.setEnabled(false);
                    } else {
                        mPriceBtc.setText("余额：" + TextUtils.doubleToFour(mTransBean.getBalance()));
                        mPriceBtc.setTextColor(getColor(R.color.login_edit_bg));
                        mSubmitBtc.setEnabled(true);
                    }
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
                RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(new IPermissionRequest() {
                    @Override
                    public void toSetting() {
                        RsPermission.getInstance().toSettingPer();
                    }

                    @Override
                    public void cancle(int i) {
                        Toast.makeText(BTCTransferActivity.this, "未给予相机权限将无法扫描二维码", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void success(int i) {
                        Intent intent = new Intent(BTCTransferActivity.this, QCodeActivity.class);
                        startActivityForResult(intent, 200);
                    }
                }).requestPermission(this, Manifest.permission.CAMERA);
                break;
            case R.id.btc_submit:
                // TODO 18/12/07
                String transAddress = mTransAddressBtc.getText().toString();
                String priceBtc = mTransPriceBtc.getText().toString();
                if (transAddress.equals("")) {
                    Toast.makeText(this, "收款地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (priceBtc.equals("")) {
                    Toast.makeText(this, "转账金额不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                startActivity(PayCodeActivity.class);
                mPopView = PopViewHelper.getInstance()
                        .create(this
                                , R.layout.layout_paycode
                                , "确认转账"
                                , "转账金额"
                                , priceBtc + mTransBean.getFeeTokenName()
                                , transAddress
                                , mTransBean.getFee() + mTransBean.getFeeTokenName()
                                , true
                                , new IPayWindowListener() {

                                    @Override
                                    public void onclick(View view) {
                                        switch (view.getId()) {
                                            case R.id.pay_close:
                                                mPopView.dismiss();
                                                Toast.makeText(BTCTransferActivity.this, "取消交易", Toast.LENGTH_SHORT).show();
                                                break;
                                            case R.id.pay_text:
                                                KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                                setAlpha(0.5f);
                                                break;
                                            case R.id.pay_forget:
                                                break;
                                        }
                                    }

                                    @Override
                                    public void dismiss() {
                                        setAlpha(1f);
                                    }
                                }, num -> {
                                    mPresenter.sendTransferMsg(getToken(), transAddress, num, tokenId, priceBtc);
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
                    String hash = data.getStringExtra(CodeUtils.RESULT_STRING);
                    mTransAddressBtc.setText(hash);
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
        mPriceBtc.setText("余额：" + TextUtils.doubleToFour(data.getBalance()));
        mSxfBtc.setText(data.getFee() + " " + data.getFeeTokenName());
    }

    @Override
    public void transferCallBack(UpdateBean bean) {
        if (bean.getCode() == 200) {
            Dialog dialog = DialogHelper.getInstance().create(this, R.drawable.pending_icon, "转账成功");
            dialog.show();
            new Handler().postDelayed(() -> dialog.dismiss(), 1000);
        } else if (bean.getCode() == 400) {
            Toast.makeText(this, bean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
