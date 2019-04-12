package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseFragment
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.*

import io.reactivex.Observable

interface IWalletContract {
    abstract class WalletPresenter : BasePresenter<IWalletModel, IWalletView>() {
        abstract fun getAllAsset()

        abstract fun getAssetList()

        abstract fun getWhetherToSignIn()

        abstract fun putSignIn()

        abstract fun getBanner()

        abstract fun getMsg(timestamp: Long, type: Int, pagesize: Int)
    }

    interface IWalletModel : IBaseModel {
        /**
         * remove All asset
         *
         * @return
         */
        val allAsset: Observable<AllAssetBean>

        /**
         * remove asset list
         *
         * @return
         */
        val assetList: Observable<AssetListBean>

        /**
         * 获取用户是否签到
         *
         * @return
         */
        val whetherToSignIn: Observable<UpdateBean>

        /**
         * 发起签到请求
         *
         * @return
         */
        fun putSignIn(): Observable<UpdateBean>

        /**
         * 发起签到请求
         *
         * @return
         */
        fun getBanner(): Observable<BannerBean>

        /**
         * 请求数据
         *
         * @return
         */
        fun getMsg(timestamp: Long, type: Int, pagesize: Int): Observable<MsgBean>


    }

    interface IWalletView : IBaseFragment {
        fun refreshAssetList(asset: AssetListBean)

        fun refreshAllAsset(allAssetBean: AllAssetBean)

        fun refreshMsg(msgBean: MsgBean)

        fun showSignin(isSignin: Boolean)

        fun signRequest(isSignin: Boolean)

        fun bannerList(bannerBean: ArrayList<BannerBean.DataBean>)

        fun showNullAsset()

        fun serverError()
    }
}
