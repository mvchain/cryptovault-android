package com.mvc.cryptovault_android.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.MsgAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.contract.MsgContract;
import com.mvc.cryptovault_android.presenter.MsgPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.mvc.cryptovault_android.common.Constant.SP.READ_MSG;

public class MsgActivity extends BaseMVPActivity<MsgContract.MsgPresenter> implements MsgContract.IMsgView, View.OnClickListener {
    private ImageView mBackMsg;
    private TextView mTitleMsg;
    private RecyclerView mRvMsg;
    private List<MsgBean.DataBean> mBeans;
    private MsgAdapter msgAdapter;
    private View mBarStatus;
    private SwipeRefreshLayout mSwipMsg;
    private TextView mMsgNull;
    private boolean isRefresh = false;

    @Override

    protected void initData() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msg;
    }


    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return MsgPresenter.newIntance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msg_back:
                // TODO 18/11/28
                finish();
                break;
        }
    }

    @Override
    protected void initMVPData() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        isRefresh = true;
        mPresenter.getMsg(0, 0, 10);
    }

    @Override
    protected void initMVPView() {
        mBackMsg = findViewById(R.id.msg_back);
        mTitleMsg = findViewById(R.id.msg_title);
        mBarStatus = findViewById(R.id.status_bar);
        mRvMsg = findViewById(R.id.msg_rv);
        mSwipMsg = findViewById(R.id.msg_swip);
        mMsgNull = findViewById(R.id.msg_null);
        mBackMsg.setOnClickListener(this);
        mBeans = new ArrayList<>();
        mRvMsg.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MsgAdapter(R.layout.item_msg_list, mBeans);
        mRvMsg.setAdapter(msgAdapter);
        mSwipMsg.post(() -> mSwipMsg.setRefreshing(true));
        mSwipMsg.setOnRefreshListener(this::refresh);
        initRecyclerLoadmore();
    }

    @Override
    public void showSuccess(MsgBean msgs) {
        if (isRefresh) {
            mBeans.clear();
            isRefresh = false;
        }
        mSwipMsg.post(() -> mSwipMsg.setRefreshing(false));
        mRvMsg.setVisibility(View.VISIBLE);
        mMsgNull.setVisibility(View.INVISIBLE);
        mBeans.addAll(msgs.getData());
        msgAdapter.notifyDataSetChanged();
    }

    private void initRecyclerLoadmore() {
        mRvMsg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == msgAdapter.getItemCount() && msgAdapter.getItemCount() >= 10 && !isRefresh) {
                        //发送网络请求获取更多数据
                        mPresenter.getMsg(mBeans.get(mBeans.size() - 1).getCreatedAt(), 1, 10);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    @Override
    public void showNullMsg() {
        isRefresh = false;
        mSwipMsg.post(() -> mSwipMsg.setRefreshing(false));
        if (mBeans.size() > 0) {
//            Toast.makeText(this, "没有新消息", Toast.LENGTH_SHORT).show();
        } else {
            mRvMsg.setVisibility(View.INVISIBLE);
            mMsgNull.setVisibility(View.VISIBLE);
        }
    }

    public void refresh() {
        isRefresh = true;
        mPresenter.getMsg(0, 0, 10);
    }

    @Override
    protected void onDestroy() {
        SPUtils.getInstance().put(READ_MSG, false);
        setResult(200);
        super.onDestroy();
    }
}
