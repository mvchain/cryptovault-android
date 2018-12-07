package com.mvc.cryptovault_android.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.BTCTransferContract;
import com.mvc.cryptovault_android.presenter.BTCTransferPresenter;
import com.mvc.cryptovault_android.utils.PointLengthFilter;
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
        mTransAddressBtc.setText(hash);
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mBackM = findViewById(R.id.m_back);
        mTitleM = findViewById(R.id.m_title);
        mQCodeM = findViewById(R.id.m_qcode);
        mTitleLayoutBtc = findViewById(R.id.btc_title_layout);
        mTransAddressBtc = findViewById(R.id.btc_trans_address);
        mTransPriceBtc = findViewById(R.id.btc_trans_price);
        mPriceBtc = findViewById(R.id.btc_price);
        mSxfBtc = findViewById(R.id.btc_sxf);
        mSubmitBtc = findViewById(R.id.btc_submit);
        mTransPriceBtc.setFilters(new InputFilter[]{new PointLengthFilter()});
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
}
