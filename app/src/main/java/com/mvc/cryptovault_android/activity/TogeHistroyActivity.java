package com.mvc.cryptovault_android.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.TogeHisAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.TogeHisBean;
import com.mvc.cryptovault_android.contract.TogeHistroyContract;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.presenter.TogeHistroyPresenter;
import com.mvc.cryptovault_android.view.RuleRecyclerLines;

import java.util.ArrayList;
import java.util.List;

public class TogeHistroyActivity extends BaseMVPActivity<TogeHistroyContract.TogeHistroyPresenter> implements TogeHistroyContract.ITogeHisView, View.OnClickListener {
    private ImageView mBackTogehis;
    private TextView mTitleTogehis;
    private EditText mEditTogehis;
    private ImageView mSerachTogehis;
    private RecyclerView mRvTogehis;
    private RecyclerView mSerachRvTogehis;
    private TextView mSerachNullTogehis;
    private SwipeRefreshLayout mSerachRefresh;
    private List<TogeHisBean.DataBean> beans;
    private List<TogeHisBean.DataBean> searchBean;
    private TogeHisAdapter hisAdapter;
    private TogeHisAdapter searchAdapter;
    private boolean isSerach = false;
    private boolean isRefresh = false;

    @Override
    protected void initMVPData() {
        isRefresh = true;
        mPresenter.getReservation(getToken(), 0, 10, 0);
    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        beans = new ArrayList<>();
        searchBean = new ArrayList<>();
        mBackTogehis = findViewById(R.id.togehis_back);
        mTitleTogehis = findViewById(R.id.togehis_title);
        mEditTogehis = findViewById(R.id.togehis_edit);
        mSerachTogehis = findViewById(R.id.togehis_serach);
        mRvTogehis = findViewById(R.id.togehis_rv);
        mSerachRvTogehis = findViewById(R.id.togehis_serach_rv);
        mSerachNullTogehis = findViewById(R.id.togehis_serach_null);
        mSerachRefresh = findViewById(R.id.togehis_refresh);
        mBackTogehis.setOnClickListener(this);
        mSerachTogehis.setOnClickListener(this);
        hisAdapter = new TogeHisAdapter(R.layout.item_toge_histroy, beans);
        searchAdapter = new TogeHisAdapter(R.layout.item_toge_histroy, searchBean);
        mRvTogehis.setAdapter(hisAdapter);
        mSerachRvTogehis.setAdapter(searchAdapter);
        mSerachRvTogehis.addItemDecoration(new RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1));
        mSerachRefresh.setOnRefreshListener(this::refresh);
        mSerachRefresh.post(() -> mSerachRefresh.setRefreshing(true));
        initRecyclerLoadmore();
        initSearch();
    }

    private void refresh() {
        isRefresh = true;
        mPresenter.getReservation(getToken(), 0, 10, 0);
    }

    private void initSearch() {
        mEditTogehis.addTextChangedListener(new EditTextChange() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchTv = s.toString();
                if (searchTv.equals("")) {
                    mSerachNullTogehis.setVisibility(View.GONE);
                    mSerachRvTogehis.setVisibility(View.GONE);
                    mRvTogehis.setVisibility(View.VISIBLE);
                } else {
                    searchBean.clear();
                    searchAdapter.notifyDataSetChanged();
                    mRvTogehis.setVisibility(View.GONE);
                    for (int i = 0; i < beans.size(); i++) {
                        TogeHisBean.DataBean dataBean = beans.get(i);
                        if (dataBean.getProjectName().toLowerCase().contains(searchTv.toLowerCase())) {
                            searchBean.add(dataBean);
                        }
                    }
                    if (searchBean.size() == 0) {
                        mSerachNullTogehis.setVisibility(View.VISIBLE);
                        mSerachRvTogehis.setVisibility(View.GONE);
                    } else {
                        mSerachNullTogehis.setVisibility(View.GONE);
                        mSerachRvTogehis.setVisibility(View.VISIBLE);
                        searchAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initRecyclerLoadmore() {
        mRvTogehis.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager.getItemCount() >= 10 && layoutManager.findLastVisibleItemPosition() >= layoutManager.getItemCount() * 0.7 && !isRefresh) {
                        mPresenter.getReservation(getToken(), beans.get(beans.size() - 1).getId(), 10, 1);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_togehis;
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
        return TogeHistroyPresenter.newIntance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.togehis_back:
                // TODO 18/12/07
                finish();
                break;
            case R.id.togehis_serach:
                // TODO 18/12/07
                isSerach = !isSerach;
                if (isSerach) {
                    mSerachTogehis.setImageDrawable(getDrawable(R.drawable.cancel_icon_black));
                    mEditTogehis.setVisibility(View.VISIBLE);
                    mTitleTogehis.setVisibility(View.GONE);
                    mEditTogehis.setFocusable(true);
                    mEditTogehis.requestFocus();
                    KeyboardUtils.showSoftInput(this);
                } else {
                    mSerachTogehis.setImageDrawable(getDrawable(R.drawable.serch_icon_black));
                    mEditTogehis.setVisibility(View.GONE);
                    mTitleTogehis.setVisibility(View.VISIBLE);
//                    在关闭搜索框的时候搜索内容也关闭掉
                    mSerachNullTogehis.setVisibility(View.GONE);
                    mSerachRvTogehis.setVisibility(View.GONE);
                    mRvTogehis.setVisibility(View.VISIBLE);
                    KeyboardUtils.hideSoftInput(this);
                }
                break;
        }
    }

    @Override
    public void showSuccess(List<TogeHisBean.DataBean> beanList) {
        if (isRefresh) {
            isRefresh = false;
            beans.clear();
        }
        mSerachRefresh.post(() -> mSerachRefresh.setRefreshing(false));
        beans.addAll(beanList);
        hisAdapter.notifyDataSetChanged();
    }

}
