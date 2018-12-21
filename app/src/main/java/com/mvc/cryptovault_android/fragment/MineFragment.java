package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.AboutActivity;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.contract.MineContract;
import com.mvc.cryptovault_android.presenter.MinePresenter;
import com.mvc.cryptovault_android.utils.JsonHelper;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mvc.cryptovault_android.common.Constant.SP.USER_INFO;

public class MineFragment extends BaseMVPFragment<MineContract.MinePresenter> implements MineContract.IMineView, View.OnClickListener {

    private CircleImageView mImgUser;
    private TextView mNameUser;
    private TextView mPhoneUser;
    private SuperTextView mLanguageSys;
    private SuperTextView mAbout;
    private SwipeRefreshLayout mSwipMine;

    @Override
    protected void initData() {
        super.initData();
        getUserInfo();
    }

    private void getUserInfo() {
        mPresenter.getUserInfo(getToken());
    }

    @Override
    protected void initView() {
        mImgUser = rootView.findViewById(R.id.user_img);
        mNameUser = rootView.findViewById(R.id.user_name);
        mPhoneUser = rootView.findViewById(R.id.user_phone);
        mLanguageSys = rootView.findViewById(R.id.sys_language);
        mAbout = rootView.findViewById(R.id.about);
        mSwipMine = rootView.findViewById(R.id.mine_swip);
        mSwipMine.post(() -> mSwipMine.setRefreshing(true));
        mSwipMine.setOnRefreshListener(this::refresh);
        mAbout.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_mine;
    }

    @Override
    public BasePresenter initPresenter() {
        return MinePresenter.newIntance();
    }

    @Override
    public void setUser(UserInfoBean user) {
        mSwipMine.post(() -> mSwipMine.setRefreshing(false));
        SPUtils.getInstance().put(USER_INFO, JsonHelper.jsonToString(user));
        UserInfoBean.DataBean data = user.getData();
        mNameUser.setText(data.getNickname());
        mPhoneUser.setText(data.getUsername());
        RequestOptions options = new RequestOptions().fallback( R.drawable.portrait_icon).placeholder(R.drawable.loading_img).error(R.drawable.portrait_icon);
        Glide.with(activity).load(data.getHeadImage()).apply(options).into(mImgUser);
    }

    private void refresh() {
        mPresenter.getUserInfo(getToken());
    }

    @Override
    public void serverError() {
        mSwipMine.post(() -> mSwipMine.setRefreshing(false));
        String userJson = SPUtils.getInstance().getString(USER_INFO);
        if (!userJson.equals("")) {
            UserInfoBean infoBean = (UserInfoBean) JsonHelper.stringToJson(SPUtils.getInstance().getString(USER_INFO), UserInfoBean.class);
            if (infoBean != null) {
                UserInfoBean.DataBean data = infoBean.getData();
                mNameUser.setText(data.getNickname());
                mPhoneUser.setText(data.getUsername());
                RequestOptions options = new RequestOptions().fallback(R.drawable.portrait_icon).placeholder(R.drawable.loading_img).error(R.drawable.portrait_icon);
                Glide.with(activity).load(data.getHeadImage()).apply(options).into(mImgUser);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                startActivity(new Intent(activity, AboutActivity.class));
                break;
        }
    }
}
