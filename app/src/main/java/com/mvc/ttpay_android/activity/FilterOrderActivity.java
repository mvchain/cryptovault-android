package com.mvc.ttpay_android.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.adapter.rvAdapter.FilterAdapter;
import com.mvc.ttpay_android.base.BaseActivity;
import com.mvc.ttpay_android.bean.FilterBean;
import com.mvc.ttpay_android.bean.TrandChildBean;
import com.mvc.ttpay_android.event.TrandOrderEvent;
import com.mvc.ttpay_android.utils.JsonHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.mvc.ttpay_android.common.Constant.SP.TRAND_VRT_LIST;

public class FilterOrderActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBackFilter;
    private RelativeLayout mToolbarFilter;
    private RecyclerView mTypeFilter;
    private RecyclerView mTypeVrtFilter;
    private RecyclerView mTypeBalanceFilter;
    private TextView mTypeSubmitFilter;
    private List<FilterBean> mType = new ArrayList<>();
    private List<FilterBean> mTypeVrt = new ArrayList<>();
    private List<FilterBean> mTypeBalance = new ArrayList<>();
    private FilterAdapter allFilterAdapter;
    private FilterAdapter vrtFilterAdapter;
    private FilterAdapter balanceFilterAdapter;
    private String[] types = {MyApplication.getAppContext().getString(R.string.all), MyApplication.getAppContext().getString(R.string.buy), MyApplication.getAppContext().getString(R.string.sold)};
    private String paitId = "";
    private String transactionType = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        List<TrandChildBean.DataBean> vrtBean = ((TrandChildBean) JsonHelper.stringToJson(SPUtils.getInstance().getString(TRAND_VRT_LIST), TrandChildBean.class)).getData();
        for (int i = 0; i < types.length; i++) {
            FilterBean allfilterBean = new FilterBean();
            allfilterBean.setTitle(types[i]);
            allfilterBean.setCheck(false);
            mType.add(allfilterBean);
        }
        for (int i = 0; i < vrtBean.size(); i++) {
            TrandChildBean.DataBean dataBean = vrtBean.get(i);
            FilterBean filterBean = new FilterBean();
            filterBean.setCheck(false);
            filterBean.setPariId(dataBean.getPairId());
            filterBean.setTitle(dataBean.getPair());
            mTypeVrt.add(filterBean);
        }
        allFilterAdapter = new FilterAdapter(R.layout.item_filter_rv, mType);
        vrtFilterAdapter = new FilterAdapter(R.layout.item_filter_rv, mTypeVrt);
        balanceFilterAdapter = new FilterAdapter(R.layout.item_filter_rv, mTypeBalance);
        mTypeFilter.setAdapter(allFilterAdapter);
        mTypeVrtFilter.setAdapter(vrtFilterAdapter);
//        mTypeBalanceFilter.setAdapter(balanceFilterAdapter);
        initAdapterClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initAdapterClick() {
        allFilterAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.filter_item:
                    for (FilterBean bean : mType) {
                        bean.setCheck(false);
                    }
                    FilterBean filterBean = mType.get(position);
                    filterBean.setCheck(!filterBean.isCheck());
                    transactionType = position - 1 < 0 ? "" : String.valueOf(position);
                    allFilterAdapter.notifyDataSetChanged();
                    mTypeSubmitFilter.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.white));
                    mTypeSubmitFilter.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_filter_blue_22dp_submit));
                    mTypeSubmitFilter.setEnabled(true);
                    break;
            }
        });
        vrtFilterAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.filter_item:
                    for (FilterBean bean : mTypeVrt) {
                        bean.setCheck(false);
                    }
                    for (FilterBean bean : mTypeBalance) {
                        bean.setCheck(false);
                    }
                    FilterBean filterBean = mTypeVrt.get(position);
                    filterBean.setCheck(!filterBean.isCheck());
                    vrtFilterAdapter.notifyDataSetChanged();
                    balanceFilterAdapter.notifyDataSetChanged();
                    paitId = mTypeVrt.get(position).getPariId() + "";
                    mTypeSubmitFilter.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.white));
                    mTypeSubmitFilter.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_filter_blue_22dp_submit));
                    mTypeSubmitFilter.setEnabled(true);
                    break;
            }
        });
        balanceFilterAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.filter_item:
                    for (FilterBean bean : mTypeBalance) {
                        bean.setCheck(false);
                    }
                    for (FilterBean bean : mTypeVrt) {
                        bean.setCheck(false);
                    }
                    FilterBean filterBean = mTypeBalance.get(position);
                    filterBean.setCheck(!filterBean.isCheck());
                    vrtFilterAdapter.notifyDataSetChanged();
                    balanceFilterAdapter.notifyDataSetChanged();
                    paitId = mTypeBalance.get(position).getPariId() + "";
                    mTypeSubmitFilter.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.white));
                    mTypeSubmitFilter.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.shape_filter_blue_22dp_submit));
                    mTypeSubmitFilter.setEnabled(true);
                    break;
            }
        });
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mBackFilter = findViewById(R.id.filter_back);
        mTypeFilter = findViewById(R.id.filter_type);
        mToolbarFilter = findViewById(R.id.filter_toolbar);
        mTypeVrtFilter = findViewById(R.id.filter_type_vrt);
        mTypeSubmitFilter = findViewById(R.id.filter_type_submit);
        mTypeBalanceFilter = findViewById(R.id.filter_type_balance);
        mBackFilter.setOnClickListener(this);
        mTypeSubmitFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_back:
                // TODO 18/12/15
                finish();
                break;
            case R.id.filter_type_submit:
                // TODO 18/12/15
                TrandOrderEvent trandOrderEvent = new TrandOrderEvent();
                trandOrderEvent.setPariId(paitId);
                trandOrderEvent.setTransactionType(transactionType);
                EventBus.getDefault().post(trandOrderEvent);
                finish();
                break;
        }
    }
}
