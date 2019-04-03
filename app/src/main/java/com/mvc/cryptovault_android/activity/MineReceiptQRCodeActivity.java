package com.mvc.cryptovault_android.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.ReceiptBean;
import com.mvc.cryptovault_android.contract.ReceiptQRContract;
import com.mvc.cryptovault_android.presenter.MineReceiptPresenter;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;
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
    private String tokenName;
    private LinearLayout mLayoutShare;
    private DialogHelper dialogHelper;

    @Override
    protected void initMVPData() {
        tokenId = getIntent().getIntExtra("tokenId", 0);
        mPresenter.getMineQcode(tokenId);
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        dialogHelper = DialogHelper.Companion.getInstance();
        tokenName = getIntent().getStringExtra("tokenName");
        mBackM = findViewById(R.id.m_back);
        mLayoutShare = findViewById(R.id.share_layout);
        mTitleM = findViewById(R.id.m_title);
        mShareM = findViewById(R.id.m_share);
        mContentM = findViewById(R.id.m_content);
        mHashM = findViewById(R.id.m_hash);
        mQcImgM = findViewById(R.id.m_qc_img);
        mProtraitM = findViewById(R.id.m_protrait);
        mTitleM.setText(tokenName + "收款");
        mContentM.setText(tokenName + "收款地址");
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
                /** * 分享图片 */
                RsPermission.getInstance().setiPermissionRequest(new IPermissionRequest() {
                    @Override
                    public void toSetting() {
                        RsPermission.getInstance().toSettingPer();
                    }

                    @Override
                    public void cancle(int i) {
                        dialogHelper.create(MineReceiptQRCodeActivity.this, R.drawable.miss_icon, "权限不足").show();
                        dialogHelper.dismissDelayed(null, 2000);
                    }

                    @Override
                    public void success(int i) {
                        mLayoutShare.setDrawingCacheEnabled(true);
                        Bitmap drawingCache = Bitmap.createBitmap(mLayoutShare.getDrawingCache());
                        Intent parintent = new Intent();
                        Uri parseUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), drawingCache, null, null));
                        parintent.setAction(Intent.ACTION_SEND);//设置分享行为
                        parintent.setType("image/*");  //设置分享内容的类型
                        parintent.putExtra(Intent.EXTRA_STREAM, parseUri);
                        //创建分享的Dialog
                        Intent share_intent = Intent.createChooser(parintent, "分享到:");
                        startActivity(share_intent);
                        drawingCache.recycle();
                        mLayoutShare.setDrawingCacheEnabled(false);
                    }
                }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.m_hash:
                // TODO 18/12/07
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("hash", mHashM.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showLong("内容已复制至剪贴板");
                break;
        }
    }

    @Override
    public void showSuccess(ReceiptBean receiptBean) {
        String hash = receiptBean.getData();
        mHashM.setText(hash);
        Bitmap mBitmap = CodeUtils.createImage(hash, 400, 400, null);
        Glide.with(this).load(mBitmap).into(mQcImgM);
    }

    @Override
    public void showError() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
