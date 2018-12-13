package com.mvc.cryptovault_android.activity;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.listener.IDialogViewClickListener;
import com.mvc.cryptovault_android.view.DialogHelper;

import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private View mBarStatus;
    private ImageView mIconAbout;
    private TextView mVersionAbout;
    private TextView mSingoutAbout;
    private ImageView mBackAbout;
    private Dialog mOutDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    protected void initData() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
    }

    protected void initView() {

        mBarStatus = findViewById(R.id.status_bar);
        mIconAbout = findViewById(R.id.about_icon);
        mVersionAbout = findViewById(R.id.about_version);
        mSingoutAbout = findViewById(R.id.about_singout);
        mSingoutAbout.setOnClickListener(this);
        mBackAbout = findViewById(R.id.about_back);
        mBackAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_back:
                // TODO 18/11/30
                finish();
                break;
            case R.id.about_singout:
                // TODO 18/11/30
                mOutDialog = DialogHelper.getInstance().create(this, "确定要登出VPay?", new IDialogViewClickListener() {
                    @Override
                    public void click(int viewId) {
                        switch (viewId) {
                            case R.id.hint_enter:
                                SPUtils.getInstance().remove(REFRESH_TOKEN);
                                SPUtils.getInstance().remove(TOKEN);
                                startTaskActivity(AboutActivity.this);
                                break;
                            case R.id.hint_cancle:
                                mOutDialog.dismiss();
                                break;
                        }
                    }
                });
                mOutDialog.show();
                break;
        }
    }
}
