package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.DetailBean;

import io.reactivex.Observable;

public interface DetailContract {
    abstract class DetailPresenter extends BasePresenter<IDetailModel, IDetailView> {
        public abstract void getDetailOnID(String token, int id);

    }

    interface IDetailModel extends IBaseModel {
        Observable<DetailBean> getDetailOnID(String token, int id);
    }

    interface IDetailView extends IBaseActivity {
        void showDetail(DetailBean bean);
    }
}
