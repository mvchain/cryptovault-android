package com.mvc.cryptovault_android.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.ReceiptBean;
import com.mvc.cryptovault_android.contract.ReceiptQRContract;
import com.mvc.cryptovault_android.presenter.MineReceiptPresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class MineReceiptQRCodeActivity extends BaseMVPActivity<ReceiptQRContract.ReceiptQRPresenter> implements ReceiptQRContract.IReceiptQRView, View.OnClickListener {
    private ImageView mBackM;
    private TextView mTitleM;
    private ImageView mShareM;
    private TextView mContentM;
    private TextView mHashM;
    private ImageView mQcImgM;
    private ImageView mProtraitM;
    private int tokenId;

    @Override
    protected void initMVPData() {
        tokenId = getIntent().getIntExtra("tokenId", 0);
        mPresenter.getMineQcode(getToken(), tokenId);
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mBackM = findViewById(R.id.m_back);
        mTitleM = findViewById(R.id.m_title);
        mShareM = findViewById(R.id.m_share);
        mContentM = findViewById(R.id.m_content);
        mHashM = findViewById(R.id.m_hash);
        mQcImgM = findViewById(R.id.m_qc_img);
        mProtraitM = findViewById(R.id.m_protrait);
        mBackM.setOnClickListener(this);
        mShareM.setOnClickListener(this);
        mHashM.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receipt;
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
        return MineReceiptPresenter.newIntance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                // TODO 18/12/07
                finish();
                break;
            case R.id.m_share:
                // TODO 18/12/07

                break;
            case R.id.m_hash:
                // TODO 18/12/07
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("hash", mHashM.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(this, "内容已复制至剪贴板", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showSuccess(ReceiptBean receiptBean) {
        String hash = receiptBean.getData();
        mHashM.setText(hash);
        Bitmap mBitmap = CodeUtils.createImage(hash, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.vp_logo));
        Glide.with(this).load(mBitmap).into(mQcImgM);
    }
    @Override
    public void showError() {

    }
}
