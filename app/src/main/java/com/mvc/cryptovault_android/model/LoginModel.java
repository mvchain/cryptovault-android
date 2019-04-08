package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.*;
import com.mvc.cryptovault_android.contract.ILoginContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginModel extends BaseModel implements ILoginContract.ILoginModel {

    @Nullable
    public static LoginModel getInstance() {
        return new LoginModel();
    }

    @Override
    public Observable<LoginBean> getLoginStatus(String token, String email, String pwd, String code) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).getUserSalt(MyApplication.getTOKEN()
                , email)
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap((Function<HttpTokenBean, ObservableSource<LoginBean>>) salt -> {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("imageToken", token);
                        jsonObject.put("username", email);
                        jsonObject.put("password", pwd);
                        jsonObject.put("validCode", code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String loginInfo = jsonObject.toString();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), loginInfo);
                    return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).login(requestBody).compose(RxHelper.rxSchedulerHelper());
                })
                .map(loginBean -> loginBean);
    }

    @Override
    public Observable<HttpTokenBean> sendCode(String cellphone) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).sendValiCode(cellphone)
                .compose(RxHelper.rxSchedulerHelper())
                .map(httpTokenBean -> httpTokenBean);
    }

    @Override
    public Observable<LoginValidBean> getValid(String email) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).getValid(email).compose(RxHelper.rxSchedulerHelper())
                .map(loginValidBean -> loginValidBean);
    }

    @Override
    public Observable<HttpTokenBean> postValid(String geetest_challenge, String geetest_seccode, String geetest_validate, int status, String uid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("geetest_challenge", geetest_challenge);
            jsonObject.put("geetest_seccode", geetest_seccode);
            jsonObject.put("geetest_validate", geetest_validate);
            jsonObject.put("status", status);
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginInfo = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), loginInfo);
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).postValid(requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map(pstValid -> pstValid);
    }
}
