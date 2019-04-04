package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.VPBalanceBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.IBalanceContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VPBalanceModel extends BaseModel implements IBalanceContract.BalanceModel {
    public static VPBalanceModel getInstance() {
        return new VPBalanceModel();
    }

    @Override
    public Observable<VPBalanceBean> getBalance() {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getBalance(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper()).map(vpBalanceBean -> vpBalanceBean);
    }

    @Override
    public Observable<UpdateBean> sendDebitMsg(String password, String value) {
        JSONObject object = new JSONObject();
        try {
            object.put("password", password);
            object.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/html"), object.toString());
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).sendDebitMsg(MyApplication.getTOKEN(), body).compose(RxHelper.rxSchedulerHelper()).map(updateBean -> updateBean);
    }
}
