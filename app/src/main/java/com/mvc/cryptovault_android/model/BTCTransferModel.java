package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.IDToTransferBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.BTCTransferContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BTCTransferModel extends BaseModel implements BTCTransferContract.BTCTransferModel {
    public static BTCTransferModel getInstance() {
        return new BTCTransferModel();
    }

    @Override
    public Observable<IDToTransferBean> getDetail(String token, int id) {
        return RetrofitUtils.client(ApiStore.class).getTranstion(token, id).compose(RxHelper.rxSchedulerHelper()).map(idbean -> idbean);
    }

    @Override
    public Observable<UpdateBean> sendTransferMsg(String token, String address, String password, int tokenId, String value) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", token);
            object.put("address", address);
            object.put("password", password);
            object.put("tokenId", tokenId);
            object.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/html"), object.toString());
        return RetrofitUtils.client(ApiStore.class).sendTransferRequest(token, body).compose(RxHelper.rxSchedulerHelper()).map(updateBean -> updateBean);
    }
}
