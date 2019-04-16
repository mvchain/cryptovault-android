package com.mvc.ttpay_android.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.base.BaseMVPActivity;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.bean.DetailBean;
import com.mvc.ttpay_android.contract.IDetailContract;
import com.mvc.ttpay_android.presenter.DetailPresenter;
import com.mvc.ttpay_android.utils.TextUtils;
import com.mvc.ttpay_android.view.DialogHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;

public class DetailActivity extends BaseMVPActivity<IDetailContract.DetailPresenter> implements IDetailContract.IDetailView, View.OnClickListener {
    private ImageView mBackDetail;
    private ImageView mShareDetail;
    private ImageView mIconDetail;
    private TextView mTitleDetail;
    private TextView mTimeDetail;
    private TextView mPriceTitleDetail;
    private TextView mPriceContentDetail;
    private LinearLayout mPriceLayoutDetail;
    private TextView mFeesTitleDetail;
    private TextView mFeesContentDetail;
    private LinearLayout mFeesLayoutDetail;
    private TextView mColladdTitleDetail;
    private TextView mColladdContentDetail;
    private LinearLayout mCollLayoutDetail;
    private TextView mHashTitleDetail;
    private TextView mHashContentDetail;
    private LinearLayout mHashLayoutDetail;
    private int id;
    private DialogHelper dialogHelper;
    private LinearLayout mLayoutShare;
    private TextView mTvTitle;

    @Override
    protected void initMVPData() {
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            mPresenter.getDetailOnID(id);
        }
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        dialogHelper = DialogHelper.Companion.getInstance();
        mBackDetail = findViewById(R.id.detail_back);
        mBackDetail.setOnClickListener(this);
        mShareDetail = findViewById(R.id.detail_share);
        mShareDetail.setOnClickListener(this);
        mIconDetail = findViewById(R.id.detail_icon);
        mTitleDetail = findViewById(R.id.detail_title);
        mTimeDetail = findViewById(R.id.detail_time);
        mPriceTitleDetail = findViewById(R.id.detail_price_title);
        mPriceContentDetail = findViewById(R.id.detail_price_content);
        mPriceLayoutDetail = findViewById(R.id.detail_price_layout);
        mFeesTitleDetail = findViewById(R.id.detail_fees_title);
        mFeesContentDetail = findViewById(R.id.detail_fees_content);
        mFeesLayoutDetail = findViewById(R.id.detail_fees_layout);
        mColladdTitleDetail = findViewById(R.id.detail_colladd_title);
        mColladdContentDetail = findViewById(R.id.detail_colladd_content);
        mCollLayoutDetail = findViewById(R.id.detail_coll_layout);
        mHashTitleDetail = findViewById(R.id.detail_hash_title);
        mHashContentDetail = findViewById(R.id.detail_hash_content);
        mHashLayoutDetail = findViewById(R.id.detail_hash_layout);
        mLayoutShare = findViewById(R.id.share_layout);
        mTvTitle = findViewById(R.id.title_tv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
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
        return DetailPresenter.newIntance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                // TODO 18/12/01
                finish();
                break;
            case R.id.detail_share:
                // TODO 18/12/01
                // TODO 18/12/07
                /** * 分享图片 */
                RsPermission.getInstance().setiPermissionRequest(new IPermissionRequest() {
                    @Override
                    public void toSetting() {
                        RsPermission.getInstance().toSettingPer();
                    }

                    @Override
                    public void cancle(int i) {
                        dialogHelper.create(DetailActivity.this, R.drawable.miss_icon, getString(R.string.insufficient_permissions)).show();
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
                        Intent share_intent = Intent.createChooser(parintent, getString(R.string.share_to));
                        startActivity(share_intent);
                        drawingCache.recycle();
                        mLayoutShare.setDrawingCacheEnabled(false);
                    }
                }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showDetail(DetailBean bean) {
        DetailBean.DataBean data = bean.getData();
        int transactionType = data.getTransactionType();
        if (transactionType == 1) {
            mColladdTitleDetail.setText(getString(R.string.payment_address));
        } else {
            mColladdTitleDetail.setText(getString(R.string.collection_address_2));
        }
        int classify = data.getClassify();
        //区块链和转账
        if (classify == 0) {
            switch (data.getStatus()) {
                case 0:
                case 1:
                    mIconDetail.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.pending_status_icon));
                    mTitleDetail.setText(transactionType == 1 ? getString(R.string.in_the_collection) : getString(R.string.transfer_load));
                    break;
                case 2:
                    mIconDetail.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.success_status_icon));
                    mTitleDetail.setText(transactionType == 1 ? getString(R.string.successful_payment) : getString(R.string.successful_transfer));
                    break;
                case 9:
                    mIconDetail.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.defeat_status_icon));
                    mTitleDetail.setText(transactionType == 1 ? getString(R.string.failed_payment) : getString(R.string.failed_transfer));
                    break;
            }
            mFeesContentDetail.setText(TextUtils.INSTANCE.doubleToEight(data.getFee()) + " " + data.getFeeTokenType());
            String address = data.getToAddress();
            String fromAddress = data.getFromAddress();
            if (!address.equals("")) {
                mColladdContentDetail.setText(transactionType == 1 ? fromAddress : address);
                mColladdContentDetail.setOnLongClickListener(view -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("hash", mColladdContentDetail.getText().toString());
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    dialogHelper.create(this, R.drawable.success_icon, getString(R.string.copied_to_the_clipboard));
                    dialogHelper.dismissDelayed(null, 2000);
                    return true;
                });
            } else {
                mColladdContentDetail.setText(getString(R.string.no));
            }
            String hash = data.getHash();
            if (!hash.equals("")) {
                mHashContentDetail.setText(data.getHash());
                mHashContentDetail.setOnClickListener(v -> {
                    Uri uri = Uri.parse(bean.getData().getHashLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                });
                mHashContentDetail.setOnLongClickListener(view -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("hash", mHashContentDetail.getText().toString());
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    dialogHelper.create(this, R.drawable.success_icon, getString(R.string.copied_to_the_clipboard));
                    dialogHelper.dismissDelayed(null, 2000);
                    return true;
                });
            } else {

                mHashContentDetail.setText(getString(R.string.no));
            }
        } else if (classify == 1) { //订单交易
            mIconDetail.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.jy_icon));
            mTitleDetail.setText(data.getOrderRemark() + " "+getString(R.string.navi_trand));
            mFeesLayoutDetail.setVisibility(View.GONE);
            mHashLayoutDetail.setVisibility(View.GONE);
            mCollLayoutDetail.setVisibility(View.GONE);
        } else if (classify == 2) { //众筹交易
            mIconDetail.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.zc_icon));
            StringBuffer buffer = new StringBuffer();
            switch (data.getStatus()) {
                case 9:
                    buffer.append(getString(R.string.return_));
                    break;
                case 2:
                    buffer.append(getString(R.string.currency_));
                    break;
                case 0:
                    buffer.append(getString(R.string.reservation));
                    break;
            }
            mTitleDetail.setText(data.getOrderRemark() + " "+getString(R.string.navi_toge) + buffer.toString());
            mFeesLayoutDetail.setVisibility(View.GONE);
            mHashLayoutDetail.setVisibility(View.GONE);
            mCollLayoutDetail.setVisibility(View.GONE);
        } else if (classify == 3) {
            mIconDetail.setImageDrawable(data.getStatus() == 2 ? ContextCompat.getDrawable(getBaseContext(), R.drawable.success_icon) : ContextCompat.getDrawable(getBaseContext(), R.drawable.miss_icon));
            mTitleDetail.setText(getString(R.string.deposit) + (data.getStatus() == 9 ? getString(R.string.failed): getString(R.string.success)));
            mTvTitle.setText(data.getOrderRemark() + " " + (data.getStatus() == 9 ? getString(R.string.expenditure): getString(R.string.income)));
            mFeesLayoutDetail.setVisibility(View.GONE);
            mHashLayoutDetail.setVisibility(View.GONE);
            mCollLayoutDetail.setVisibility(View.GONE);
        } else if (classify == 4) {
            mIconDetail.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.financial_selected_bold));
            StringBuffer buffer = new StringBuffer();
            switch (data.getStatus()) {
                case 4:
                    buffer.append(getString(R.string.take_out));
                    break;
                case 5:
                    buffer.append(getString(R.string.commission));
                    break;
                case 6:
                    buffer.append(getString(R.string.earnings));
                    break;
            }
            mTitleDetail.setText(data.getOrderRemark() + " " + buffer);
            mFeesLayoutDetail.setVisibility(View.GONE);
            mHashLayoutDetail.setVisibility(View.GONE);
            mCollLayoutDetail.setVisibility(View.GONE);
        }
        mPriceContentDetail.setText((transactionType == 1 ? "+" : "-") + TextUtils.INSTANCE.doubleToEight(data.getValue()) + " " + data.getTokenName());
        mTimeDetail.setText(TimeUtils.millis2String(data.getCreatedAt()));
    }
}
