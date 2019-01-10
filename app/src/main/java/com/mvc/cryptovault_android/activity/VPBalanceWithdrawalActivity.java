package com.mvc.cryptovault_android.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
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
import com.mvc.cryptovault_android.bean.VPBalanceBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.BalanceContract;
import com.mvc.cryptovault_android.event.HistroyEvent;
import com.mvc.cryptovault_android.event.HistroyFragmentEvent;
import com.mvc.cryptovault_android.event.WalletMsgEvent;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.listener.IPayWindowListener;
import com.mvc.cryptovault_android.presenter.VPBalancePresenter;
import com.mvc.cryptovault_android.utils.PointLengthFilter;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.mvc.cryptovault_android.view.PopViewHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * 余额提取
 */
public class VPBalanceWithdrawalActivity extends BaseMVPActivity<BalanceContract.BalancePresenter> implements BalanceContract.BalanceView, View.OnClickListener {
    private ImageView mBackM;
    private TextView mTitleM;
    private LinearLayout mTitleLayoutVP;
    private EditText mBwPriceVp;
    private TextView mPriceVp;
    private TextView mSubmitVp;
    private PopupWindow mPopView;
    private VPBalanceBean vpBalanceBean;
    private DialogHelper dialogHelper;

    @Override
    protected void initMVPData() {
        mPresenter.getBalance();
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        dialogHelper = DialogHelper.getInstance();
        mBackM = findViewById(R.id.m_back);
        mBackM.setOnClickListener(this);
        mTitleM = findViewById(R.id.m_title);
        mTitleLayoutVP = findViewById(R.id.vp_title_layout);
        mBwPriceVp = findViewById(R.id.vp_bw_price);
        mPriceVp = findViewById(R.id.vp_price);
        mSubmitVp = findViewById(R.id.vp_submit);
        mSubmitVp.setOnClickListener(this);
        mBwPriceVp.setFilters(new InputFilter[]{new PointLengthFilter()});
        mBwPriceVp.addTextChangedListener(new EditTextChange() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String chagePrice = s.toString();
                if (!chagePrice.equals("") && vpBalanceBean != null) {
                    if (TextUtils.stringToDouble(chagePrice) > vpBalanceBean.getData()) {
                        mPriceVp.setText("余额不足");
                        mPriceVp.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.red));
                        mSubmitVp.setEnabled(false);
                    } else {
                        mPriceVp.setText("余额：" + TextUtils.doubleToFour(vpBalanceBean.getData()));
                        mPriceVp.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.login_edit_bg));
                        mSubmitVp.setEnabled(true);
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vp_balance;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void showSuccess(VPBalanceBean data) {
        this.vpBalanceBean = data;
        mPriceVp.setText("余额：" + TextUtils.doubleToFour(data.getData()));
    }

    @Override
    public void callBack(UpdateBean bean) {
        if (bean.getCode() == 200) {
            Dialog dialog = dialogHelper.create(this, R.drawable.success_icon, "提取成功");
            dialog.show();
            dialogHelper.dismissDelayed(() -> {
                EventBus.getDefault().post(new HistroyEvent(mBwPriceVp.getText().toString().trim()));
                EventBus.getDefault().post(new HistroyFragmentEvent());
                EventBus.getDefault().post(new WalletMsgEvent());
                finish();
            }, 1500);
            new Handler().postDelayed(() -> dialog.dismiss(), 1000);
        } else {
            Toast.makeText(this, bean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return VPBalancePresenter.newIntance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                // TODO 18/12/11
                finish();
                break;
            case R.id.vp_submit:
                // TODO 18/12/11
                String vpEditPrice = mBwPriceVp.getText().toString();
                if (vpEditPrice.equals("")) {
                    Toast.makeText(this, "取出余额不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPopView = PopViewHelper.getInstance()
                        .create(this
                                , R.layout.layout_paycode
                                , "确认取出"
                                , "取出金额"
                                , vpEditPrice + " 余额"
                                , ""
                                , ""
                                , false
                                , new IPayWindowListener() {
                                    @Override
                                    public void onclick(View view) {
                                        switch (view.getId()) {
                                            case R.id.pay_close:
                                                mPopView.dismiss();
                                                Toast.makeText(VPBalanceWithdrawalActivity.this, "取消交易", Toast.LENGTH_SHORT).show();
                                                break;
                                            case R.id.pay_text:
                                                KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                                setAlpha(0.5f);
                                                break;
                                            case R.id.pay_forget:
                                                dialogHelper.create(VPBalanceWithdrawalActivity.this, R.layout.layout_forgetpwd_dialog).show();
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
                                    mPresenter.sendDebitMsg(num, vpEditPrice);
                                    mPopView.dismiss();
                                });
                mPopView.showAtLocation(mSubmitVp, Gravity.BOTTOM, 0, 0);
                mPopView.getContentView().post(() ->
                        KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text))
                );
                setAlpha(0.5f);
                break;
        }
    }
}
