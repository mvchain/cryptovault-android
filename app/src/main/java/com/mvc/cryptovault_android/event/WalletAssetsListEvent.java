package com.mvc.cryptovault_android.event;

import com.mvc.cryptovault_android.bean.AssetListBean;

import java.util.List;

public class WalletAssetsListEvent {
    private String addId;
    private String removeId;
    private List<AssetListBean.DataBean> newsData;

    public List<AssetListBean.DataBean> getNewsData() {
        return newsData;
    }

    public void setNewsData(List<AssetListBean.DataBean> newsData) {
        this.newsData = newsData;
    }

    public WalletAssetsListEvent(List<AssetListBean.DataBean> newsData) {
        this.newsData = newsData;
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getRemoveId() {
        return removeId;
    }

    public void setRemoveId(String removeId) {
        this.removeId = removeId;
    }
}
