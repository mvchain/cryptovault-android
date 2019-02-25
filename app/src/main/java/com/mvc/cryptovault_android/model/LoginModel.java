package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.LoginValidBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.LoginContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginModel extends BaseModel implements LoginContract.ILoginModel {

    @Nullable
    public static LoginModel getInstance() {
        return new LoginModel();
    }

    @Override
    public Observable<LoginBean> getLoginStatus(String token,String email, String pwd, String code) {
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
        return RetrofitUtils.client(ApiStore.class).login(requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map(loginBean -> loginBean);
    }

    @Override
    public Observable<HttpTokenBean> sendCode(String cellphone) {
        return RetrofitUtils.client(ApiStore.class).sendValiCode(cellphone)
                .compose(RxHelper.rxSchedulerHelper())
                .map(httpTokenBean -> httpTokenBean);
    }

    @Override
    public Observable<LoginValidBean> getValid(String email) {
        return RetrofitUtils.client(ApiStore.class).getValid(email).compose(RxHelper.rxSchedulerHelper())
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
        return RetrofitUtils.client(ApiStore.class).postValid(requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map(pstValid -> pstValid);
    }
}
