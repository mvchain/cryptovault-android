package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputFilter;
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
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.RecorBean;
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

import static com.mvc.cryptovault_android.common.Constant.SP.RECORDING_UNIT;

public class TrandPurhAndSellItemActivity extends BaseActivity implements View.OnClickListener {

    private DialogHelper dialogHelper;
    private PopupWindow mPopView;
    private TextView mTitleTrand;
    private ImageView mBackTrand;
    private ImageView mHistroyTrand;
    private TextView mHintError;
    private TextView mHintPrice;
    private TextView mPrice;
    private TextView mHintVrt;
    private TextView mPriceVrt;
    private TextView mNumPrice;
    private EditText mEditPurh;
    private TextView mAllPricePurh;
    private TextView mSubmitPurh;
    private Dialog mPurhDialog;
    private TrandChildBean.DataBean data;
    private RecorBean.DataBean recorBean;
    private int type;
    private double price;
    private int pairId;
    private String unitPrice;
    private String allPriceUnit;
    private double tokenBalance;
    private double balance;
    private double currentPrice;
    private TextView mHintBusiness;
    private TextView mNameBusiness;
    private TextView mHintRemaining;
    private TextView mNumRemaining;
    private TextView mPriceHintSale;
    private TextView mPriceNumSale;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_purh_sell_item;
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    protected void initData() {
        data = getIntent().getParcelableExtra("data");
        recorBean = getIntent().getParcelableExtra("recorBean");
        mTitleTrand.setText((type == 1 ? "出售" : "购买") + getIntent().getStringExtra("title"));
        type = getIntent().getIntExtra("type", 0);
        pairId = getIntent().getIntExtra("id", 0);
        unitPrice = getIntent().getStringExtra("unit_price");
        allPriceUnit = getIntent().getStringExtra("allprice_unit");
        mEditPurh.setHint("输入" + (type == 1 ? "购买" : "出售") + "数量");
        mHintBusiness.setText((type == 1 ? "卖家：" : "买家："));
        mHintRemaining.setText("剩余" + (type == 1 ? "出售" : "购买") + "量");
        mPriceHintSale.setText((type == 1 ? "出售" : "购买") + "价格");
        mNameBusiness.setText(recorBean.getNickname());
        mNumRemaining.setText(recorBean.getLimitValue() + " " + getIntent().getStringExtra("title"));
        mPriceNumSale.setText(TextUtils.doubleToFour(recorBean.getTotal() * recorBean.getPrice()) + " " + SPUtils.getInstance().getString(RECORDING_UNIT));
        this.currentPrice = recorBean.getTotal() * recorBean.getPrice();
        mNumPrice.setText((type == 1 ? "购买" : "出售") + "量");
        RetrofitUtils.client(ApiStore.class).getTransactionInfo(getToken(), data.getPairId(), type)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(trandPurhBean -> {
                    if (trandPurhBean.getCode() == 200) {
                        TrandPurhBean.DataBean data = trandPurhBean.getData();
                        this.tokenBalance = data.getTokenBalance();
                        this.balance = data.getBalance();
                        mPrice.setText(TextUtils.doubleToFour(data.getTokenBalance()));
                        mPriceVrt.setText(TextUtils.doubleToFour(data.getBalance()));
                        mHintPrice.setText("可用" + TrandPurhAndSellItemActivity.this.data.getTokenName());
                        price = data.getPrice();
                        mAllPricePurh.setText("0.00 " + allPriceUnit);
                        mPrice.setText(TextUtils.doubleToFour(data.getTokenBalance()));
                        mEditPurh.setFilters(new InputFilter[]{new PointLengthFilter()});
                        mEditPurh.addTextChangedListener(new EditTextChange() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String numText = s.toString();
                                if (!numText.equals("")) {
                                    Double num = Double.valueOf(numText);
                                    if (num == 0) {
                                        mAllPricePurh.setText("0.00 " + allPriceUnit);
                                    } else {
                                        mAllPricePurh.setText(TextUtils.doubleToDouble(recorBean.getTotal() * recorBean.getPrice() * num) + " " + allPriceUnit);
                                    }
                                } else {
                                    mAllPricePurh.setText("0.00 " + allPriceUnit);
                                }
                            }
                        });
                    } else {

                    }
                }, throwable -> {
                    LogUtils.e("TrandPurhAndSellItemAct", throwable.getMessage());
                });
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        dialogHelper = DialogHelper.getInstance();
        mTitleTrand = findViewById(R.id.trand_title);
        mBackTrand = findViewById(R.id.trand_back);
        mHistroyTrand = findViewById(R.id.trand_histroy);
        mHintPrice = findViewById(R.id.price_hint);
        mPrice = findViewById(R.id.price);
        mHintVrt = findViewById(R.id.vrt_hint);
        mPriceVrt = findViewById(R.id.vrt_price);
        mHintBusiness = findViewById(R.id.business_hint);
        mNameBusiness = findViewById(R.id.business_name);
        mHintRemaining = findViewById(R.id.remaining_hint);
        mNumRemaining = findViewById(R.id.remaining_num);
        mPriceHintSale = findViewById(R.id.sale_price_hint);
        mPriceNumSale = findViewById(R.id.sale_price_num);
        mNumPrice = findViewById(R.id.price_num);
        mEditPurh = findViewById(R.id.purh_edit);
        mAllPricePurh = findViewById(R.id.purh_all_price);
        mHintError = findViewById(R.id.error_hint);
        mSubmitPurh = findViewById(R.id.purh_submit);
        mBackTrand.setOnClickListener(this);
        mSubmitPurh.setOnClickListener(this);
        mHistroyTrand.setOnClickListener(this);
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
                LogUtils.e("TrandPurhAndSellItemAct", "type:" + type);
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
                if (type == 1 && Double.valueOf(currentNum) > tokenBalance) {
                    dialogHelper.create(this, R.drawable.miss_icon, "最多可购买" + tokenBalance).show();
                    dialogHelper.dismissDelayed(null, 1000);
                    return;
                }
                if (type == 2 && Double.valueOf(currentNum) > balance) {
                    dialogHelper.create(this, R.drawable.miss_icon, "最多可出售" + balance).show();
                    dialogHelper.dismissDelayed(null, 1000);
                    return;
                }
                String type = (this.type == 1 ? data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()) : unitPrice);
                String numType = (this.type == 1 ? data.getPair().substring(0, data.getPair().indexOf("/")) : data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()));
                String payNum = currentAllPrice.split(" ")[0];
                double allPrice = currentPrice * Double.parseDouble(currentNum);
                String allPrichType = this.type == 1 ? TextUtils.doubleToFour(allPrice) : payNum;
                String buyPrice = this.type == 1 ? currentNum : TextUtils.doubleToFour(Double.parseDouble(currentNum) * currentPrice);
                mPopView = createPopWindow(this, R.layout.layout_paycode
                        , this.type == 1 ? "确认购买" : "确认发布"
                        , "总计需支付"
                        , allPrichType + " " + type
                        , this.type == 1 ? "购买数量" : "总价"
                        , buyPrice + " " + numType
                        , this.type == 1 ? "购买单价" : "出售单价"
                        , TextUtils.doubleToFour(currentPrice) + " " + data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length())
                        , new IPayWindowListener() {
                            @Override
                            public void onclick(View view) {
                                switch (view.getId()) {
                                    case R.id.pay_close:
                                        mPopView.dismiss();
                                        Toast.makeText(TrandPurhAndSellItemActivity.this, "取消交易", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.pay_text:
                                        KeyboardUtils.showSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
                                        setAlpha(0.5f);
                                        break;
                                    case R.id.pay_forget:
                                        dialogHelper.create(TrandPurhAndSellItemActivity.this, R.layout.layout_forgetpwd_dialog).show();
                                        dialogHelper.dismissDelayed(null, 2000);
                                        break;
                                }
                            }

                            @Override
                            public void dismiss() {
                                setAlpha(1f);
                            }
                        }, num -> {
                            mPurhDialog = dialogHelper.create(TrandPurhAndSellItemActivity.this, R.drawable.pending_icon, "正在发布");
                            mPurhDialog.show();
                            mPopView.dismiss();
                            JSONObject object = new JSONObject();
                            try {
                                object.put("id", pairId);
                                object.put("pairId", data.getPairId());
                                object.put("password", num);
                                object.put("price", price);
                                object.put("transactionType", this.type);
                                object.put("value", currentNum);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestBody body = RequestBody.create(MediaType.parse("text/html"), object.toString());
                            KeyboardUtils.hideSoftInput(mPopView.getContentView().findViewById(R.id.pay_text));
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
                dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.success_icon, (type == 1 ? "购买" : "出售") + "成功");
                EventBus.getDefault().post(new RecordingEvent());
                mHintError.setVisibility(View.INVISIBLE);
                dialogHelper.dismissDelayed(() -> finish(), 1000);
            } else if (updateBean.getCode() == 400) {
                dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                mHintError.setVisibility(View.INVISIBLE);
                dialogHelper.dismissDelayed(null, 2000);
            } else {
                dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                mHintError.setVisibility(View.VISIBLE);
                mHintError.setText(updateBean.getMessage());
                dialogHelper.dismissDelayed(null, 2000);
            }
        }, throwable -> {
            dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.miss_icon, throwable.getMessage());
            dialogHelper.dismissDelayed(null, 2000);
            LogUtils.e("TrandPurhAndSellActivit", throwable.getMessage());
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
                dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.success_icon, (type == 1 ? "购买" : "出售") + "成功");
                mHintError.setVisibility(View.INVISIBLE);
                EventBus.getDefault().post(new RecordingEvent());
                dialogHelper.dismissDelayed(() -> finish(), 1000);
            } else if (updateBean.getCode() == 400) {
                dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                mHintError.setVisibility(View.INVISIBLE);
                dialogHelper.dismissDelayed(null, 2000);
            } else {
                dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.miss_icon, updateBean.getMessage());
                mHintError.setVisibility(View.VISIBLE);
                mHintError.setText(updateBean.getMessage());
                dialogHelper.dismissDelayed(null, 2000);
            }
        }, throwable -> {
            dialogHelper.resetDialogResource(TrandPurhAndSellItemActivity.this, R.drawable.miss_icon, throwable.getMessage());
            dialogHelper.dismissDelayed(null, 2000);
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
