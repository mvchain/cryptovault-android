package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.RecordingEvent;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.bean.TrandPurhBean;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.listener.IPayWindowListener;
import com.mvc.cryptovault_android.listener.PswMaxListener;
import com.mvc.cryptovault_android.utils.PointLengthFilter;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.mvc.cryptovault_android.view.PswText;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TrandPurhAndSellActivity extends BaseActivity implements View.OnClickListener {

    private TrandChildBean.DataBean data;
    private TextView mTitleTrand;
    private ImageView mBackTrand;
    private ImageView mHistroyTrand;
    private RelativeLayout mToolbarAbout;
    private TextView mHintPrice;
    private TextView mPrice;
    private TextView mPriceVrt;
    private TextView mTitlePrice;
    private TextView mPriceCurrent;
    private TextView mPricePurh;
    private SeekBar mSeekPurh;
    private TextView mSeekNumPurh;
    private LinearLayout mLayoutProcess;
    private TextView mNumPrice;
    private EditText mEditPurh;
    private TextView mPriceHintAll;
    private TextView mNumErrorHint;
    private TextView mAllPricePurh;
    private TextView mSubmitPurh;
    private boolean isNetWork;
    private int type;
    private String unitPrice;
    private String allPriceUnit;
    private PopupWindow mPopView;
    private DialogHelper dialogHelper;
    private Dialog mPurhDialog;
    private double tokenBalance;
    private double balance;
    private double currentPricePurh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purh_sell;
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    protected void initData() {
        data = getIntent().getParcelableExtra("data");
        mTitleTrand.setText(getIntent().getStringExtra("title"));
        type = getIntent().getIntExtra("type", 0);
        unitPrice = getIntent().getStringExtra("unit_price");
        allPriceUnit = getIntent().getStringExtra("allprice_unit");
        mEditPurh.setHint("输入" + (type == 1 ? "购买" : "出售") + "数量");
        mTitlePrice.setText((type == 1 ? "购买" : "出售") + "单价：");
        mNumPrice.setText((type == 1 ? "购买" : "出售") + "数量");
        RetrofitUtils.client(ApiStore.class).getTransactionInfo(getToken(), data.getPairId(), type)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(trandPurhBean -> {
                    if (trandPurhBean.getCode() == 200) {
                        TrandPurhBean.DataBean data = trandPurhBean.getData();
                        mPrice.setText(TextUtils.doubleToFour(data.getTokenBalance()));
                        mPriceVrt.setText(TextUtils.doubleToFour(data.getBalance()));
                        mHintPrice.setText("可用" + TrandPurhAndSellActivity.this.data.getTokenName());
                        mPriceCurrent.setText("当前价格" + TextUtils.doubleToFour(data.getPrice()) + "VRT");
                        this.tokenBalance = data.getTokenBalance();
                        this.balance = data.getBalance();
                        double seekMin = Math.abs(100 + data.getMin());
                        double seekMax = Math.abs(100 + data.getMax());
                        mSeekPurh.setMax((int) (seekMax - seekMin) * 100);
                        if ((int) data.getMin() == 0) {
                            mSeekPurh.setProgress(0);
                        } else if ((int) data.getMax() == 0) {
                            mSeekPurh.setProgress(mSeekPurh.getMax());
                        } else {
                            mSeekPurh.setProgress(mSeekPurh.getMax() / 2);
                        }
                        mAllPricePurh.setText("0.0000 " + allPriceUnit);
                        mPricePurh.setText(TextUtils.doubleToDouble(data.getPrice() * ((100 + data.getMin()) + (int) Double.parseDouble(TextUtils.doubleToDouble(mSeekPurh.getProgress() / 100))) / 100) + " VRT");
                        mSeekNumPurh.setText((100 + data.getMin()) + (int) Double.parseDouble(TextUtils.doubleToDouble(mSeekPurh.getProgress() / 100)) + "%");
                        mSeekPurh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                double currentPro = (100 + data.getMin()) + (int) Double.parseDouble(TextUtils.doubleToDouble(progress / 100));
                                mSeekNumPurh.setText(currentPro + "%");
                                mPricePurh.setText(TextUtils.doubleToFour(data.getPrice() * currentPro / 100) + " VRT");
                                currentPricePurh = data.getPrice() * currentPro / 100;
                                if (!mEditPurh.getText().toString().equals("")) {
                                    double allPrice = (data.getPrice() * currentPro / 100) * Double.valueOf(mEditPurh.getText().toString());
                                    mAllPricePurh.setText(TextUtils.doubleToFour(allPrice) + " " + allPriceUnit);
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });
                        mPrice.setText(TextUtils.doubleToFour(data.getTokenBalance()));
                        mEditPurh.setFilters(new InputFilter[]{new PointLengthFilter()});
                        mEditPurh.addTextChangedListener(new EditTextChange() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String numText = s.toString();
                                if (!numText.equals("")) {
                                    Double num = Double.valueOf(numText);
                                    if (num == 0) {
                                        mAllPricePurh.setText("0.0000 " + allPriceUnit);
                                        mSubmitPurh.setEnabled(false);
                                        mSubmitPurh.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
                                        mNumErrorHint.setVisibility(View.INVISIBLE);

                                    } else {
                                        double currentPro = (100 + data.getMin()) + (int) Double.parseDouble(TextUtils.doubleToDouble(mSeekPurh.getProgress() / 100));
                                        double allPrice = (data.getPrice() * currentPro / 100) * Double.valueOf(mEditPurh.getText().toString());
                                        mAllPricePurh.setText(TextUtils.doubleToFour(allPrice) + " " + allPriceUnit);
                                        if (type == 1 && allPrice > TrandPurhAndSellActivity.this.tokenBalance) {
                                            mSubmitPurh.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
                                            mSubmitPurh.setEnabled(false);
                                            mNumErrorHint.setVisibility(View.VISIBLE);
                                            mNumErrorHint.setText("超过最大购买金额");
                                        } else if (type == 2 && allPrice > TrandPurhAndSellActivity.this.balance) {
                                            mSubmitPurh.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
                                            mSubmitPurh.setEnabled(false);
                                            mNumErrorHint.setVisibility(View.VISIBLE);
                                            mNumErrorHint.setText("超过最大可出售数量");
                                        } else {
                                            mNumErrorHint.setVisibility(View.INVISIBLE);
                                            mSubmitPurh.setBackgroundResource(R.drawable.bg_login_submit);
                                            mSubmitPurh.setEnabled(true);
                                        }
                                    }
                                } else {
                                    mNumErrorHint.setVisibility(View.INVISIBLE);
                                    mAllPricePurh.setText("0.0000 " + allPriceUnit);
                                    mSubmitPurh.setEnabled(false);
                                    mSubmitPurh.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck);
                                }
                            }
                        });
                    } else {

                    }
                }, throwable -> {

                });

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mTitleTrand = findViewById(R.id.trand_title);
        mBackTrand = findViewById(R.id.trand_back);
        mHistroyTrand = findViewById(R.id.trand_histroy);
        mToolbarAbout = findViewById(R.id.about_toolbar);
        mHintPrice = findViewById(R.id.price_hint);
        mPrice = findViewById(R.id.price);
        mPriceVrt = findViewById(R.id.vrt_price);
        mTitlePrice = findViewById(R.id.price_title);
        mPriceCurrent = findViewById(R.id.current_price);
        mPricePurh = findViewById(R.id.purh_price);
        mSeekPurh = findViewById(R.id.purh_seek);
        mSeekNumPurh = findViewById(R.id.purh_seek_num);
        mLayoutProcess = findViewById(R.id.process_layout);
        mNumPrice = findViewById(R.id.price_num);
        mEditPurh = findViewById(R.id.purh_edit);
        mNumErrorHint = findViewById(R.id.num_error_hint);
        mPriceHintAll = findViewById(R.id.all_price_hint);
        mAllPricePurh = findViewById(R.id.purh_all_price);
        mSubmitPurh = findViewById(R.id.purh_submit);
        mBackTrand.setOnClickListener(this);
        mHistroyTrand.setOnClickListener(this);
        mSubmitPurh.setOnClickListener(this);
        dialogHelper = DialogHelper.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trand_back:
                // TODO 18/12/14
                if (mPopView != null) {
                    KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                }
                finish();
                break;
            case R.id.trand_histroy:
                // TODO 18/12/14
                Intent intent = new Intent(this, TrandOrderActivity.class);
                intent.putExtra("pairId", data.getPairId() + "");
                intent.putExtra("transactionType", type - 1 + "");
                startActivity(intent);
                break;
            case R.id.purh_submit:
                // TODO 18/12/14
                String currentNum = mEditPurh.getText().toString();
                String currentAllPrice = mAllPricePurh.getText().toString();
                if (currentNum.equals("") || Double.valueOf(currentNum) <= 0) {
                    dialogHelper.create(this, R.drawable.miss_icon, type == 1 ? "购买数量不正确" : "出售数量不正确").show();
                    dialogHelper.dismissDelayed(null, 1000);
                    return;
                }
                if (type == 1 && Double.valueOf(currentNum) > balance) {
                    dialogHelper.create(this, R.drawable.miss_icon, "最多可购买" + TextUtils.doubleToFour(balance)).show();
                    dialogHelper.dismissDelayed(null, 1000);
                    return;
                }
                if (type == 2 && Double.parseDouble(currentNum) * Double.parseDouble(mPricePurh.getText().toString().split(" ")[0]) > tokenBalance) {
                    dialogHelper.create(this, R.drawable.miss_icon, "最多可出售" + TextUtils.doubleToFour(tokenBalance)).show();
                    dialogHelper.dismissDelayed(null, 1000);
                    return;
                }
                String type = (this.type == 1 ? data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()) : unitPrice);
                String numType = (this.type == 1 ? data.getPair().substring(0, data.getPair().indexOf("/")) : data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()));
                String payNum = currentAllPrice.split(" ")[0];
                String unitPrice = mPricePurh.getText().toString().split(" ")[0];
                double allPrice = Double.parseDouble(unitPrice) * Double.parseDouble(currentNum);
                String allPrichType = this.type == 1 ? TextUtils.doubleToFour(allPrice) : payNum;
                LogUtils.e("TrandPurhAndSellActivit", "Double.parseDouble(currentNum) * Double.parseDouble(unitPrice):" + (Double.parseDouble(currentNum) * Double.parseDouble(unitPrice)));
                String buyPrice = this.type == 1 ? currentNum : TextUtils.doubleToFour(Double.parseDouble(currentNum) * Double.parseDouble(unitPrice));
                mPopView = createPopWindow(this, R.layout.layout_paycode
                        , this.type == 1 ? "确认购买" : "确认发布"
                        , "总计需支付"
                        , allPrichType + " " + type
                        , this.type == 1 ? "购买数量" : "总价"
                        , buyPrice + " " + numType
                        , this.type == 1 ? "购买单价" : "出售单价"
                        , unitPrice + " " + data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length())
                        , new IPayWindowListener() {
                            @Override
                            public void onclick(View view) {
                                switch (view.getId()) {
                                    case R.id.pay_close:
                                        mPopView.dismiss();
                                        Toast.makeText(TrandPurhAndSellActivity.this, "取消交易", Toast.LENGTH_SHORT).show();
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
                            mPurhDialog = dialogHelper.create(TrandPurhAndSellActivity.this, R.drawable.pending_icon, "正在发布");
                            mPurhDialog.show();
                            mPopView.dismiss();
                            JSONObject object = new JSONObject();
                            try {
                                object.put("id", 0);
                                object.put("pairId", data.getPairId());
                                object.put("password", num);
                                object.put("price", currentPricePurh);
                                object.put("transactionType", this.type);
                                object.put("value", currentNum);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestBody body = RequestBody.create(MediaType.parse("text/html"), object.toString());
                            if (this.type == 1) {
                                releasePurh(body);
                            } else {
                                releaseSell(body);
                            }
                        });
                mPopView.showAtLocation(mSubmitPurh, Gravity.BOTTOM, 0, 0);
                mPopView.getContentView().post(() ->
                        KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text))
                );
                setAlpha(0.5f);
                break;
        }
    }

    /**
     * 发布购买订单
     *
     * @param body
     */
    @SuppressLint("CheckResult")
    private void releasePurh(RequestBody body) {
        RetrofitUtils.client(ApiStore.class).releaseOrder(getToken(), body).compose(RxHelper.rxSchedulerHelper()).subscribe(updateBean -> {
            if (updateBean.getCode() == 200) {
                dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.success_icon, "发布成功");
                EventBus.getDefault().post(new RecordingEvent());
                dialogHelper.dismissDelayed(() -> {
                    KeyboardUtils.hideSoftInput(this);
                    finish();
                });
            } else if (updateBean.getCode() == 400) {
                dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                dialogHelper.dismissDelayed(() -> {
                    KeyboardUtils.hideSoftInput(this);
                });
            } else {
                dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                dialogHelper.dismissDelayed(() -> {
                    KeyboardUtils.hideSoftInput(this);
                });
            }
        }, throwable -> {
            dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.miss_icon, throwable.getMessage());
            dialogHelper.dismissDelayed(() -> {
                KeyboardUtils.hideSoftInput(this);
            });
        });
    }

    /**
     * 发布出售订单
     *
     * @param body
     */
    @SuppressLint("CheckResult")
    private void releaseSell(RequestBody body) {
        RetrofitUtils.client(ApiStore.class).releaseOrder(getToken(), body).compose(RxHelper.rxSchedulerHelper()).subscribe(updateBean -> {
            if (updateBean.getCode() == 200) {
                dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.success_icon, "发布成功");
                EventBus.getDefault().post(new RecordingEvent());
                dialogHelper.dismissDelayed(() -> {
                    KeyboardUtils.hideSoftInput(this);
                    finish();
                });
            } else if (updateBean.getCode() == 400) {
                dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                dialogHelper.dismissDelayed(() -> {
                    KeyboardUtils.hideSoftInput(this);
                });
            } else {
                dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                dialogHelper.dismissDelayed(() -> {
                    KeyboardUtils.hideSoftInput(this);
                });
            }
        }, throwable -> {
            dialogHelper.resetDialogResource(TrandPurhAndSellActivity.this, R.drawable.miss_icon, throwable.getMessage());
            dialogHelper.dismissDelayed(() -> {
                KeyboardUtils.hideSoftInput(this);
            });
            //能获取到报错信息
//            if(throwable instanceof HttpException){
//                HttpException httpException= (HttpException) throwable;
//                try {
//                    String errorBody= httpException.response().errorBody().string();
//                    //TODO: parse To JSON Obj
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        });
    }

    @SuppressLint("WrongConstant")
    public PopupWindow createPopWindow(Context context, int layoutId, String title, String childTitle, String price, String numhint, String numtv, String pricehint, String pricetv, IPayWindowListener iPayWindowListener, PswMaxListener maxListener) {
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        linear.findViewById(R.id.line).setVisibility(View.GONE);
        //初始化控件
        TextView mTitlePay = linear.findViewById(R.id.pay_title);
        mTitlePay.setText(title);
        ImageView mClosePay = linear.findViewById(R.id.pay_close);
        mClosePay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        TextView mChildTitlePay = linear.findViewById(R.id.pay_child_title);
        mChildTitlePay.setText(childTitle);
        TextView mPricePay = linear.findViewById(R.id.pay_price);
        mPricePay.setText(price);
        TextView mAddressPay = linear.findViewById(R.id.pay_address);
        mAddressPay.setText(numtv);
        LinearLayout mAddressLayoutPay = linear.findViewById(R.id.pay_address_layout);
        ((TextView) mAddressLayoutPay.findViewById(R.id.address)).setText(numhint);
        TextView mFeePay = linear.findViewById(R.id.pay_fee);
        mFeePay.setText(pricetv);
        LinearLayout mFeeLayoutPay = linear.findViewById(R.id.pay_fee_layout);
        ((TextView) mFeeLayoutPay.findViewById(R.id.price)).setText(pricehint);
        PswText mTextPay = linear.findViewById(R.id.pay_text);
        mTextPay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        mTextPay.setMaxListener(maxListener);
        TextView mForgetPay = linear.findViewById(R.id.pay_forget);
        mForgetPay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        PopupWindow mPopView = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopView.setOnDismissListener(() -> {
            if (iPayWindowListener != null) {
                iPayWindowListener.dismiss();
            }
        });
        mPopView.setContentView(linear);
        mPopView.setFocusable(true);
        mPopView.setBackgroundDrawable(new BitmapDrawable());
        mPopView.setOutsideTouchable(false);
        mPopView.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return mPopView;
    }
}
