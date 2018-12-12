package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TogeHistroyContract;
import com.mvc.cryptovault_android.model.TogHisModel;

public class TogeHistroyPresenter extends TogeHistroyContract.TogeHistroyPresenter {
    public static BasePresenter newIntance() {
        return new TogeHistroyPresenter();
    }

    @Override
    protected TogeHistroyContract.ITogeHisMol getModel() {
        return TogHisModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getReservation(String token, int id, int pageSize, int type) {
        rxUtils.register(mIModel.getReservation(token, id, pageSize, type).subscribe(togeHisBean -> {
            if (togeHisBean.getCode() == 200) {
                mIView.showSuccess(togeHisBean.getData());
            }
        }, throwable -> LogUtils.e("TogeHistroyPresenter", throwable.getMessage())));
    }

}
