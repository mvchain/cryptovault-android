package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.AboutActivity;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.contract.MineContract;
import com.mvc.cryptovault_android.presenter.MinePresenter;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseMVPFragment<MineContract.MinePresenter> implements MineContract.IMineView, View.OnClickListener {

    private CircleImageView mImgUser;
    private TextView mNameUser;
    private TextView mPhoneUser;
    private SuperTextView mLanguageSys;
    private SuperTextView mAbout;

    @Override
    protected void initData() {
        super.initData();
        getUserInfo();
    }

    private void getUserInfo() {
        String token = SPUtils.getInstance().getString("token");
        mPresenter.getUserInfo(token);
    }

    @Override
    protected void initView() {
        mImgUser = rootView.findViewById(R.id.user_img);
        mNameUser = rootView.findViewById(R.id.user_name);
        mPhoneUser = rootView.findViewById(R.id.user_phone);
        mLanguageSys = rootView.findViewById(R.id.sys_language);
        mAbout = rootView.findViewById(R.id.about);
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
        UserInfoBean.DataBean data = user.getData();
        mNameUser.setText(data.getNickname());
        mPhoneUser.setText(data.getUsername());
        Glide.with(activity).load(data.getHeadImage()).into(mImgUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about:
                startActivity(new Intent(activity,AboutActivity.class));
                break;
        }
    }
}
