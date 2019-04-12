package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseActivity;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.DetailBean;

import io.reactivex.Observable;

public interface IDetailContract {
    abstract class DetailPresenter extends BasePresenter<IDetailModel, IDetailView> {
        public abstract void getDetailOnID(int id);

    }

    interface IDetailModel extends IBaseModel {
        Observable<DetailBean> getDetailOnID(int id);
    }

    interface IDetailView extends IBaseActivity {
        void showDetail(DetailBean bean);
    }
}
