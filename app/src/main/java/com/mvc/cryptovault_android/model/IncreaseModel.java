package com.mvc.cryptovault_android.model;

import com.google.gson.Gson;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.IncreaseBean;
import com.mvc.cryptovault_android.contract.IncreaseContract;
import com.mvc.cryptovault_android.utils.DataTempCacheMap;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class IncreaseModel extends BaseModel implements IncreaseContract.IIncreaseModel {
    private CurrencyBean currencyBean;
    private AssetListBean assetListBean;
    private ArrayList<IncreaseBean> mList = new ArrayList<>();
    private ArrayList<IncreaseBean> mSearchList = new ArrayList<>();

    {
        String currency_list = (String) DataTempCacheMap.get("currency_list").getValue();
        String asset_list = (String) DataTempCacheMap.get("asset_list").getValue();
        Gson gson = new Gson();
        currencyBean = gson.fromJson(currency_list, CurrencyBean.class);
        assetListBean = gson.fromJson(asset_list, AssetListBean.class);
    }

    public static IncreaseModel getInstance() {
        return new IncreaseModel();
    }

    @Override
    public Observable<List<IncreaseBean>> getCurrencyAll() {
        List<CurrencyBean.DataBean> currdata = currencyBean.getData();
        List<AssetListBean.DataBean> assetBean = assetListBean.getData();
        for (int i = 0; i < currdata.size(); i++) {
            IncreaseBean increaseBean = new IncreaseBean();
            increaseBean.setVisi(i >= 2);
            increaseBean.setResId(currdata.get(i).getTokenImage());
            increaseBean.setCurrencyId(currdata.get(i).getTokenId());
            increaseBean.setTitle(currdata.get(i).getTokenCnName());
            increaseBean.setContent(currdata.get(i).getTokenCnName());
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
        return Observable.just(mList).map(increaseBeans -> mList);
    }

    @Override
    public Observable<List<IncreaseBean>> getCurrencySerachList(String serach) {
        mSearchList.clear();
        if (!serach.equals("")) {
            for (int i = 2; i < mList.size(); i++) {
                IncreaseBean increaseBean = mList.get(i);
                if (increaseBean.getContent().contains(serach) || increaseBean.getTitle().contains(serach)) {
                    mSearchList.add(increaseBean);
                }
            }
        }
        return Observable.just(mSearchList).map(increaseBeans -> mSearchList);
    }
}
