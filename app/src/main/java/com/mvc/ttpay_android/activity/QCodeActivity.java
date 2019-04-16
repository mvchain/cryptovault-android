package com.mvc.ttpay_android.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.base.BaseActivity;
import com.mvc.ttpay_android.utils.ImagePickerLoader;
import com.mvc.ttpay_android.utils.RxgularUtils;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;

public class QCodeActivity extends BaseActivity implements View.OnClickListener {

    private CaptureFragment captureFragment;
    private ImageView mBackQcode;
    private TextView mPhotoQcode;
    private int tokenId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qcode;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        tokenId = getIntent().getIntExtra("tokenId", 0);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.qcode_layout, captureFragment).commit();
        mBackQcode = findViewById(R.id.qcode_back);
        mPhotoQcode = findViewById(R.id.qcode_photo);
        mBackQcode.setOnClickListener(this);
        mPhotoQcode.setOnClickListener(this);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            if (tokenId == 0) {
                bundle.putBoolean("QODE", false);
            } else if (tokenId == 4 || tokenId == 2) {
                if (!RxgularUtils.isBTC(result)) {
                    bundle.putBoolean("QODE", false);
                } else {
                    bundle.putBoolean("QODE", true);
                }
            } else if (tokenId != 4) {
                if (!RxgularUtils.isETH(result)) {
                    bundle.putBoolean("QODE", false);
                } else {
                    bundle.putBoolean("QODE", true);
                }
            } else {
                bundle.putBoolean("QODE", true);
            }
            resultIntent.putExtras(bundle);
            QCodeActivity.this.setResult(200, resultIntent);
            QCodeActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            QCodeActivity.this.setResult(200, resultIntent);
            QCodeActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qcode_back:
                // TODO 18/12/07
                finish();
                break;
            case R.id.qcode_photo:
                // TODO 18/12/07
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(new IPermissionRequest() {
                        @Override
                        public void toSetting() {
                            RsPermission.getInstance().toSettingPer();
                        }

                        @Override
                        public void cancle(int i) {
                            ToastUtils.showLong(getString(R.string.cannot_read_permission));
                        }

                        @Override
                        public void success(int i) {
                            startPhotoActivity();
                        }
                    }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    startPhotoActivity();
                }
                break;
        }
    }

    /**
     * jump to photo
     */
    private void startPhotoActivity() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        Intent intent = new Intent(QCodeActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (requestCode == 100) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    Intent intent = new Intent();
                    intent.putExtra(CodeUtils.RESULT_STRING, images.get(0).path);
                    setResult(200, intent);
                    finish();
                } else {
                    ToastUtils.showLong(getString(R.string.image_error));
                }
            }
        }
    }
}
