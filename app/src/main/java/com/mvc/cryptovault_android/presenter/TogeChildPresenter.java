package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TogeChildContract;
import com.mvc.cryptovault_android.model.TogeChildModel;

public class TogeChildPresenter extends TogeChildContract.TogeChildPresenter {

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
    public void getComingSoon(String token, int pageSize, int projectId, int projectType, int type) {
        rxUtils.register(mIModel.getComingSoon(token, pageSize, projectId, projectType, type)
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

    @Override
    public void getProcess(String token, int pageSize, int projectId, int projectType, int type) {
        rxUtils.register(mIModel.getProcess(token, pageSize, projectId, projectType, type)
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

    @Override
    public void getToEnd(String token, int pageSize, int projectId, int projectType, int type) {
        rxUtils.register(mIModel.getToEnd(token, pageSize, projectId, projectType, type)
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
