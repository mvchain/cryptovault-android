package com.mvc.ttpay_android.presenter;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.contract.ITogeChildContract;
import com.mvc.ttpay_android.model.TogeChildModel;

public class TogeChildPresenter extends ITogeChildContract.TogeChildPresenter {

    public static BasePresenter newIntance() {
        return new TogeChildPresenter();
    }

    @Override
    protected TogeChildModel getModel() {
        return TogeChildModel.getInstance();
    }

    @Override
    public void onStart() {

    }


    @Override
    public void getComingList(int pageSize, int projectId, int projectType, int type) {
        rxUtils.register(mIModel.getComingList(pageSize, projectId, projectType, type)
                .subscribe(togeBean ->
                        {
                            if (togeBean.getData().size()>0) {
                                mIView.showSuccess(togeBean.getData());
                            }else{
                                mIView.showNull();
                            }
                        }
                        , throwable -> {
                            mIView.showNull();
                        }));
    }
}
