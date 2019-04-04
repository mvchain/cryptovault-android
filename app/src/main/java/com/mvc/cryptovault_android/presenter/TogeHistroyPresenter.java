package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.ITogeHistoryContract;
import com.mvc.cryptovault_android.model.TogHisModel;

public class TogeHistroyPresenter extends ITogeHistoryContract.TogeHistroyPresenter {
    public static BasePresenter newIntance() {
        return new TogeHistroyPresenter();
    }

    @Override
    protected ITogeHistoryContract.ITogeHisMol getModel() {
        return TogHisModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getReservation(int id, int pageSize,String projectName, int type) {
        rxUtils.register(mIModel.getReservation( id, pageSize, projectName,type).subscribe(togeHisBean -> {
            if(projectName==null){
                if (togeHisBean.getCode() == 200) {
                    mIView.showSuccess(togeHisBean.getData());
                }
            }else{
                if (togeHisBean.getCode() == 200) {
                    mIView.showSearchList(togeHisBean.getData());
                }
            }
        }, throwable -> LogUtils.e("TogeHistroyPresenter", throwable.getMessage())));
    }

}
