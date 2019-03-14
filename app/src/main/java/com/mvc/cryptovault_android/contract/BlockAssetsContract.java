package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.bean.VPBalanceBean;

import io.reactivex.Observable;

public interface BlockAssetsContract {
    abstract class BlockAssetsPresenter extends BasePresenter<BlockAssetsModel, BlockAssetsView> {

    }

    interface BlockAssetsModel extends IBaseModel {
    }

    interface BlockAssetsView extends IBaseActivity {
    }
}
