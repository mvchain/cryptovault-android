package com.mvc.cryptovault_android.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.MsgAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.MsgContract;
import com.mvc.cryptovault_android.presenter.MsgPresenter;

import java.util.ArrayList;
import java.util.List;

public class MsgActivity extends BaseMVPActivity<MsgContract.MsgPresenter> implements MsgContract.IMsgView, View.OnClickListener {
    private ImageView mBackMsg;
    private TextView mTitleMsg;
    private RecyclerView mRvMsg;
    private List<String> strings;
    private MsgAdapter msgAdapter;

    @Override
    protected void initData() {
        LogUtils.e("MsgActivity", "mPresenter:" + mPresenter);
//        mPresenter.getMsg();
    }

    @Override
    protected void initView() {
        mBackMsg = findViewById(R.id.msg_back);
        mTitleMsg = findViewById(R.id.msg_title);
        mRvMsg = findViewById(R.id.msg_rv);
        mBackMsg.setOnClickListener(this);
        strings = new ArrayList<>();
        mRvMsg.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MsgAdapter(R.layout.item_msg_list, strings);
        mRvMsg.setAdapter(msgAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msg;
    }

    @Override
    public void showSuccess(List<String> msgs) {
        strings.addAll(msgs);
        msgAdapter.notifyDataSetChanged();
    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        LogUtils.e("MsgActivity", "initPresenter");
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
}
