package com.mvc.cryptovault_android.model;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.IncreaseBean;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.contract.IncreaseContract;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.mvc.cryptovault_android.common.Constant.SP.CURRENCY_LIST;

public class IncreaseModel extends BaseModel implements IncreaseContract.IIncreaseModel {
    private AssetListBean assetListBean;
    private ArrayList<IncreaseBean> mList = new ArrayList<>();
    private ArrayList<IncreaseBean> mSearchList = new ArrayList<>();

    {
        String asset_list = SPUtils.getInstance().getString(Constant.SP.ASSETS_LIST);
        Gson gson = new Gson();
        assetListBean = gson.fromJson(asset_list, AssetListBean.class);
    }

    public static IncreaseModel getInstance() {
        return new IncreaseModel();
    }

    @Override
    public Observable<List<IncreaseBean>> getCurrencyAll(String token) {
        return RetrofitUtils.client(ApiStore.class).getCurrencyAll(token)
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap((Function<CurrencyBean, ObservableSource<List<IncreaseBean>>>) currencyBean -> {
                    SPUtils.getInstance().put(CURRENCY_LIST, JsonHelper.jsonToString(currencyBean));
                    List<CurrencyBean.DataBean> currdata = currencyBean.getData();
                    List<AssetListBean.DataBean> assetBean = assetListBean.getData();
                    for (int i = 0; i < currdata.size(); i++) {
                        IncreaseBean increaseBean = new IncreaseBean();
                        increaseBean.setVisi(i >= 2);
                        increaseBean.setResId(currdata.get(i).getTokenImage());
                        increaseBean.setCurrencyId(currdata.get(i).getTokenId());
                        increaseBean.setTitle(currdata.get(i).getTokenName());
                        increaseBean.setZhContent(currdata.get(i).getTokenCnName());
                        increaseBean.setEnContent(currdata.get(i).getTokenEnName());
                        for (int j = 0; j < assetBean.size(); j++) {
                            if (currdata.get(i).getTokenId() == assetBean.get(j).getTokenId()) {
                                increaseBean.setAdd(false);
                                break;
                            } else {
                                increaseBean.setAdd(true);
                            }
                        }
                        mList.add(increaseBean);
                    }
                    return Observable.just(mList);
                });
    }

    @Override
    public Observable<List<IncreaseBean>> getCurrencySerachList(String serach) {
        mSearchList.clear();
        if (!serach.equals("")) {
            for (int i = 2; i < mList.size(); i++) {
                IncreaseBean increaseBean = mList.get(i);
                if (increaseBean.getZhContent().contains(serach) || increaseBean.getTitle().contains(serach)) {
                    mSearchList.add(increaseBean);
                }
            }
        }
        return Observable.just(mSearchList).map(increaseBeans -> mSearchList);
    }
}
