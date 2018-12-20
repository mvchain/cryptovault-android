package com.mvc.cryptovault_android.activity;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.DetailBean;
import com.mvc.cryptovault_android.contract.DetailContract;
import com.mvc.cryptovault_android.presenter.DetailPresenter;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;

public class DetailActivity extends BaseMVPActivity<DetailContract.DetailPresenter> implements DetailContract.IDetailView, View.OnClickListener {
    private View mBarStatus;
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
    private boolean isTransfer;
    private DialogHelper dialogHelper;
    private LinearLayout mLayoutShare;

    @Override
    protected void initMVPData() {
        id = getIntent().getIntExtra("id", -1);
        isTransfer = getIntent().getBooleanExtra("transType", false);
        if (id != -1) {
            mPresenter.getDetailOnID(getToken(), id);
        }
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        dialogHelper = DialogHelper.getInstance();
        mBarStatus = findViewById(R.id.status_bar);
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
                        dialogHelper.create(DetailActivity.this, R.drawable.miss_icon, "权限不足").show();
                        dialogHelper.dismissDelayed(null, 1000);
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
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showDetail(DetailBean bean) {
        if (isTransfer) {
//            detail_colladd_title
            mColladdTitleDetail.setText("收款地址");
        } else {
            mColladdTitleDetail.setText("付款地址");
        }
        DetailBean.DataBean data = bean.getData();
        int classify = data.getClassify();
        //区块链和转账
        if (classify == 0 || classify == 3) {
            switch (data.getStatus()) {
                case 0:
                case 1:
                    mIconDetail.setImageDrawable(getDrawable(R.drawable.pending_status_icon));
                    mTitleDetail.setText("转账中");
                    break;
                case 2:
                    mIconDetail.setImageDrawable(getDrawable(R.drawable.success_status_icon));
                    mTitleDetail.setText("转账成功");
                    break;
                case 9:
                    mIconDetail.setImageDrawable(getDrawable(R.drawable.defeat_status_icon));
                    mTitleDetail.setText("转账失败");
                    break;
            }
            mFeesContentDetail.setText(data.getFee() + " " + data.getFeeTokenType());
            mColladdContentDetail.setText(data.getToAddress());
            mColladdContentDetail.setOnLongClickListener(view -> {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("hash", mHashContentDetail.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(DetailActivity.this, "内容已复制至剪贴板", Toast.LENGTH_SHORT).show();
                return true;
            });
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
                Toast.makeText(DetailActivity.this, "内容已复制至剪贴板", Toast.LENGTH_SHORT).show();
                return true;
            });
        } else if (classify == 1) { //订单交易
            mFeesLayoutDetail.setVisibility(View.GONE);
            mHashLayoutDetail.setVisibility(View.GONE);
            mCollLayoutDetail.setVisibility(View.GONE);
        } else if (classify == 2) { //众筹交易
            mFeesLayoutDetail.setVisibility(View.GONE);
            mHashLayoutDetail.setVisibility(View.GONE);
            mCollLayoutDetail.setVisibility(View.GONE);
        }
        mPriceContentDetail.setText(data.getValue() + " " + data.getFeeTokenType());
        mTimeDetail.setText(TimeUtils.millis2String(data.getCreatedAt()));
    }
}
