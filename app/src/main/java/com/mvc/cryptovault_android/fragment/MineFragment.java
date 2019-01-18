package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.AboutActivity;
import com.mvc.cryptovault_android.activity.InvatitionActivity;
import com.mvc.cryptovault_android.activity.LanguageActivity;
import com.mvc.cryptovault_android.activity.SelectResetPasswordActivity;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.contract.MineContract;
import com.mvc.cryptovault_android.presenter.MinePresenter;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.LanguageUtils;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mvc.cryptovault_android.common.Constant.SP.USER_INFO;

public class MineFragment extends BaseMVPFragment<MineContract.MinePresenter> implements MineContract.IMineView, View.OnClickListener {

    private CircleImageView mImgUser;
    private TextView mNameUser;
    private TextView mPhoneUser;
    private SuperTextView mLanguageSys;
    private SuperTextView mAccountSecurity;
    private SuperTextView mInvitationRegistration;
    private SuperTextView mAbout;
    private SwipeRefreshLayout mSwipMine;
    private boolean createCarryOut;


    @Override
    protected void initData() {
        super.initData();
        getUserInfo();
    }

    private void getUserInfo() {
        mPresenter.getUserInfo();
    }

    @Override
    protected void initView() {
        createCarryOut = true;
        mImgUser = rootView.findViewById(R.id.user_img);
        mNameUser = rootView.findViewById(R.id.user_name);
        mPhoneUser = rootView.findViewById(R.id.user_phone);
        mLanguageSys = rootView.findViewById(R.id.sys_language);
        mAccountSecurity = rootView.findViewById(R.id.account_security);
        mInvitationRegistration = rootView.findViewById(R.id.invitation_registration);
        mAbout = rootView.findViewById(R.id.about);
        mSwipMine = rootView.findViewById(R.id.mine_swip);
        mSwipMine.post(() -> mSwipMine.setRefreshing(true));
        mSwipMine.setOnRefreshListener(this::refresh);
        mAccountSecurity.setOnClickListener(this);
        mInvitationRegistration.setOnClickListener(this);
        mLanguageSys.setOnClickListener(this);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipMine.post(() -> mSwipMine.setRefreshing(false));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && createCarryOut) {
            refresh();
        }
    }

    @Override
    public void setUser(UserInfoBean user) {
        mSwipMine.post(() -> mSwipMine.setRefreshing(false));
        SPUtils.getInstance().put(USER_INFO, JsonHelper.jsonToString(user));
        UserInfoBean.DataBean data = user.getData();
        mNameUser.setText(data.getNickname());
        mPhoneUser.setText(data.getUsername());
        RequestOptions options = new RequestOptions().fallback(R.drawable.portrait_icon).placeholder(R.drawable.loading_img).error(R.drawable.portrait_icon);
        Glide.with(activity).load(data.getHeadImage()).apply(options).into(mImgUser);
    }

    private void refresh() {
        mPresenter.getUserInfo();
    }

    @Override
    public void serverError() {
        mSwipMine.post(() -> mSwipMine.setRefreshing(false));
        String userJson = SPUtils.getInstance().getString(USER_INFO);
        if (!userJson.equals("")) {
            UserInfoBean infoBean = (UserInfoBean) JsonHelper.stringToJson(userJson, UserInfoBean.class);
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
            case R.id.account_security:
                startActivity(new Intent(activity, SelectResetPasswordActivity.class));
                break;
            case R.id.invitation_registration:
                startActivity(new Intent(activity, InvatitionActivity.class));
                break;
            case R.id.sys_language:
                startActivity(new Intent(activity, LanguageActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(activity, AboutActivity.class));
                break;

        }
    }
}
