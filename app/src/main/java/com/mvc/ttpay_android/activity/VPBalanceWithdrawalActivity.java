package com.mvc.ttpay_android.activity;

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

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.base.BaseMVPActivity;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.bean.VPBalanceBean;
import com.mvc.ttpay_android.bean.UpdateBean;
import com.mvc.ttpay_android.contract.IBalanceContract;
import com.mvc.ttpay_android.event.HistroyEvent;
import com.mvc.ttpay_android.event.HistroyFragmentEvent;
import com.mvc.ttpay_android.event.WalletMsgEvent;
import com.mvc.ttpay_android.listener.EditTextChange;
import com.mvc.ttpay_android.listener.IPayWindowListener;
import com.mvc.ttpay_android.presenter.VPBalancePresenter;
import com.mvc.ttpay_android.utils.PointLengthFilter;
import com.mvc.ttpay_android.utils.TextUtils;
import com.mvc.ttpay_android.view.DialogHelper;
import com.mvc.ttpay_android.view.PopViewHelper;

import org.greenrobot.eventbus.EventBus;

import static com.mvc.ttpay_android.common.Constant.SP.UPDATE_PASSWORD_TYPE;
import static com.mvc.ttpay_android.common.Constant.SP.USER_EMAIL;

/**
 * 余额提取
 */
public class VPBalanceWithdrawalActivity extends BaseMVPActivity<IBalanceContract.BalancePresenter> implements IBalanceContract.BalanceView, View.OnClickListener {
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
        dialogHelper = DialogHelper.Companion.getInstance();
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
                    if (TextUtils.INSTANCE.stringToDouble(chagePrice) > vpBalanceBean.getData()) {
                        mPriceVp.setText(getString(R.string.insufficient_balance));
                        mPriceVp.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.red));
                        mSubmitVp.setEnabled(false);
                    } else {
                        mPriceVp.setText(getString(R.string.balance) + "：" + TextUtils.INSTANCE.doubleToEight(vpBalanceBean.getData()));
                        mPriceVp.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.login_edit_bg));
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
        mPriceVp.setText(getString(R.string.balance) + "：" + TextUtils.INSTANCE.doubleToEight(data.getData()));
    }

    @Override
    public void callBack(UpdateBean bean) {
        if (bean.getCode() == 200) {
            Dialog dialog = dialogHelper.create(this, R.drawable.success_icon, getString(R.string.successful_extraction));
            dialog.show();
            dialogHelper.dismissDelayed(() -> {
                EventBus.getDefault().post(new HistroyEvent(mBwPriceVp.getText().toString().trim()));
                EventBus.getDefault().post(new HistroyFragmentEvent());
                EventBus.getDefault().post(new WalletMsgEvent());
                finish();
            }, 1500);
            new Handler().postDelayed(() -> dialog.dismiss(), 1000);
        } else {
            ToastUtils.showLong(bean.getMessage());
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
                    ToastUtils.showLong(getString(R.string.the_withdrawal_balance_cannot_be_empty));
                    return;
                }
                mPopView = PopViewHelper.Companion.getInstance()
                        .create(this
                                , R.layout.layout_paycode
                                , getString(R.string.confirm_removed)
                                , getString(R.string.withdrawal_amount)
                                , vpEditPrice + " "+getString(R.string.balance)
                                , ""
                                , ""
                                , false
                                , new IPayWindowListener() {
                                    @Override
                                    public void onclick(View view) {
                                        switch (view.getId()) {
                                            case R.id.pay_close:
                                                mPopView.dismiss();
                                                ToastUtils.showLong(getString(R.string.cancel_the_deal));
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
                                    String email = SPUtils.getInstance().getString(USER_EMAIL);
                                    KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                    mPresenter.sendDebitMsg(EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(num)), vpEditPrice);
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
