package com.mvc.ttpay_android.model;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.AssetListBean;
import com.mvc.ttpay_android.bean.CurrencyBean;
import com.mvc.ttpay_android.bean.IncreaseBean;
import com.mvc.ttpay_android.common.Constant;
import com.mvc.ttpay_android.contract.IncreaseContract;
import com.mvc.ttpay_android.utils.JsonHelper;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.mvc.ttpay_android.common.Constant.SP.CURRENCY_LIST;

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
    public Observable<List<IncreaseBean>> getCurrencyAll() {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getCurrencyAll(MyApplication.getTOKEN())
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap((Function<CurrencyBean, ObservableSource<List<IncreaseBean>>>) currencyBean -> {
                    SPUtils.getInstance().put(CURRENCY_LIST, JsonHelper.jsonToString(currencyBean));
                    List<CurrencyBean.DataBean> currdata = currencyBean.getData();
                    List<AssetListBean.DataBean> assetBean = assetListBean.getData();
                    for (int i = 0; i < currdata.size(); i++) {
                        IncreaseBean increaseBean = new IncreaseBean();
                        increaseBean.setVisi(true);
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
    public Observable<List<IncreaseBean>> getCurrencySearchList(String search) {
        mSearchList.clear();
        if (!search.equals("")) {
            for (int i = 0; i < mList.size(); i++) {
                IncreaseBean increaseBean = mList.get(i);
                if (increaseBean.getZhContent().toLowerCase().contains(search.toLowerCase()) || increaseBean.getTitle().contains(search) || increaseBean.getZhContent().toLowerCase().contains(search.toLowerCase())) {
                    mSearchList.add(increaseBean);
                }
            }
        }
        return Observable.just(mSearchList).map(increaseBeans -> mSearchList);
    }
}
