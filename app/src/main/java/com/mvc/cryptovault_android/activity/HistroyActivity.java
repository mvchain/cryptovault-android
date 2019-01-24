package com.mvc.cryptovault_android.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.HistroyPagerAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.ExchangeRateBean;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.contract.HistroyContract;
import com.mvc.cryptovault_android.event.HistroyEvent;
import com.mvc.cryptovault_android.event.HistroyFragmentEvent;
import com.mvc.cryptovault_android.event.WalletFragmentEvent;
import com.mvc.cryptovault_android.fragment.HistroyChildFragment;
import com.mvc.cryptovault_android.listener.IPopViewListener;
import com.mvc.cryptovault_android.presenter.HistroyPresenter;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.TabLayoutUtils;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.utils.ViewDrawUtils;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.mvc.cryptovault_android.common.Constant.SP.ALLASSETS;
import static com.mvc.cryptovault_android.common.Constant.SP.SET_RATE;

public class HistroyActivity extends BaseMVPActivity<HistroyContract.HistroyPrecenter> implements HistroyContract.IHistroyView, View.OnClickListener {
    private ImageView mBackHis;
    private ImageView mQcodeHis;
    private TextView mPriceHis;
    private TextView mActualHis;
    private TabLayout mTabHis;
    private ViewPager mVpHis;
    private ArrayList<Fragment> fragments;
    private List<ExchangeRateBean.DataBean> mExchange;
    private HistroyPagerAdapter histroyPagerAdapter;
    private TextView mTitleHis;
    private TextView mTyleAssets;
    private TextView mOutHis;
    private TextView mInHis;
    private LinearLayout mLayoutSub;
    private Intent intent;
    private int type;
    private int tokenId;
    private String tokenName;
    private String rateType;
    private AssetListBean.DataBean dataBean;

    @Override
    protected void initMVPData() {
        HistroyChildFragment transferFragment = new HistroyChildFragment();
        Bundle transferBundle = new Bundle();
        transferBundle.putInt("tokenId", tokenId);
        transferBundle.putInt("action", 0);
        transferFragment.setArguments(transferBundle);
        fragments.add(transferFragment);
        HistroyChildFragment financesFragment = new HistroyChildFragment();
        Bundle financesBundle = new Bundle();
        financesBundle.putInt("tokenId", tokenId);
        financesBundle.putInt("action", 4);
        financesFragment.setArguments(financesBundle);
        fragments.add(financesFragment);
        HistroyChildFragment transactionFragment = new HistroyChildFragment();
        Bundle transactionBundle = new Bundle();
        transactionBundle.putInt("tokenId", tokenId);
        transactionBundle.putInt("action", 1);
        transactionFragment.setArguments(transactionBundle);
        fragments.add(transactionFragment);
        HistroyChildFragment crowdfundingFragment = new HistroyChildFragment();
        Bundle crowdfundingBundle = new Bundle();
        crowdfundingBundle.putInt("tokenId", tokenId);
        crowdfundingBundle.putInt("action", 2);
        crowdfundingFragment.setArguments(crowdfundingBundle);
        fragments.add(crowdfundingFragment);
        mVpHis.setAdapter(histroyPagerAdapter);
        mTabHis.setupWithViewPager(mVpHis);
    }

    @Override
    protected void initMVPView() {
        mExchange = new ArrayList<>();
        fragments = new ArrayList<>();
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        histroyPagerAdapter = new HistroyPagerAdapter(getSupportFragmentManager(), fragments);
        mBackHis = findViewById(R.id.his_back);
        mTitleHis = findViewById(R.id.his_title);
        mTyleAssets = findViewById(R.id.type_assets);
        mQcodeHis = findViewById(R.id.his_qcode);
        mPriceHis = findViewById(R.id.his_price);
        mActualHis = findViewById(R.id.his_actual);
        mTabHis = findViewById(R.id.his_tab);
        mVpHis = findViewById(R.id.his_vp);
        mOutHis = findViewById(R.id.his_out);
        mInHis = findViewById(R.id.his_in);
        mLayoutSub = findViewById(R.id.sub_layout);
        mBackHis.setOnClickListener(this);
        mQcodeHis.setOnClickListener(this);
        mOutHis.setOnClickListener(this);
        mInHis.setOnClickListener(this);
        initIntent();
    }

    @SuppressLint("SetTextI18n")
    private void initIntent() {
        intent = getIntent();
        type = intent.getIntExtra("type", 0);
        tokenId = intent.getIntExtra("tokenId", 0);
        tokenName = intent.getStringExtra("tokenName");
        dataBean = intent.getParcelableExtra("data");
        rateType = intent.getStringExtra("rate_type");
        mTitleHis.setText(tokenName);
        mTyleAssets.setText(tokenName + "资产");
        switch (type) {
            case 0:
                mLayoutSub.setVisibility(View.VISIBLE);
                mOutHis.setVisibility(View.GONE);
                mInHis.setVisibility(View.GONE);
                mQcodeHis.setVisibility(View.GONE);
                break;
            case 1:
                mLayoutSub.setVisibility(View.GONE);
                mQcodeHis.setVisibility(View.GONE);
                break;
            case 2:
                mLayoutSub.setVisibility(View.VISIBLE);
                mOutHis.setVisibility(View.VISIBLE);
                mInHis.setVisibility(View.VISIBLE);
                mQcodeHis.setVisibility(View.VISIBLE);
                break;
        }
        mPriceHis.setText(new SpanUtils().append(TextUtils.rateToPrice(dataBean.getRatio() * dataBean.getValue()) + " ").setFontSize(36,true)
                .append(rateType).setFontSize(10,true).create());
        mActualHis.setText(TextUtils.doubleToFour(dataBean.getValue()) + " " + dataBean.getTokenName());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_histroy;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public BasePresenter initPresenter() {
        return HistroyPresenter.newIntance();
    }

    @Override
    public void startActivity() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.his_back:
                // TODO 18/11/29
                finish();
                break;
            case R.id.his_qcode:
                // TODO 18/11/29
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
                            Intent intent = new Intent(HistroyActivity.this, QCodeActivity.class);
                            intent.putExtra("tokenId", tokenId);
                            startActivityForResult(intent, 200);
                        }
                    }).requestPermission(this, Manifest.permission.CAMERA);
                } else {
                    intent.setClass(HistroyActivity.this, QCodeActivity.class);
                    intent.putExtra("tokenId", tokenId);
                    startActivityForResult(intent, 200);
                }
                break;
            case R.id.his_out:// TODO 18/12/05
                intent.setClass(this, BTCTransferActivity.class);
                intent.putExtra("tokenId", tokenId);
                intent.putExtra("tokenName", tokenName);
                startActivity(intent);
                break;
            case R.id.his_in:// TODO 18/12/05
                intent.setClass(this, MineReceiptQRCodeActivity.class);
                intent.putExtra("tokenId", tokenId);
                intent.putExtra("tokenName", tokenName);
                startActivityForResult(intent, 300);
                break;
        }
    }

    @Override
    public void showSuccess(List<String> msgs) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case 200:
                    boolean qode = data.getBooleanExtra("QODE", false);
                    if (!qode) {
                        DialogHelper dialogHelper = DialogHelper.getInstance();
                        dialogHelper.create(this, R.drawable.miss_icon, "无效地址").show();
                        dialogHelper.dismissDelayed(null, 2000);
                        return;
                    }
                    String stringExtra = data.getStringExtra(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent(HistroyActivity.this, BTCTransferActivity.class);
                    intent.putExtra("hash", stringExtra);
                    intent.putExtra("tokenId", tokenId);
                    intent.putExtra("tokenName", tokenName);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Subscribe
    public void changePrice(HistroyEvent event) {
        String price = event.getPrice();
        mActualHis.setText(TextUtils.doubleToFour(Double.parseDouble(mActualHis.getText().toString().split(" ")[0]) - Double.parseDouble(price)));
        String newsPrice = mActualHis.getText().toString().split(" ")[0];
        mPriceHis.setText(TextUtils.rateToPrice(Double.parseDouble(newsPrice) * dataBean.getRatio()));
    }
}
