package com.mvc.cryptovault_android.utils;

import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.cryptovault_android.api.ApiStroe;
import com.mvc.cryptovault_android.bean.HttpTokenBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 判断token是否过期
 */
public class ParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 403) {//根据和服务端的约定判断token过期
            LogUtils.e("静默自动刷新Token,然后重新请求数据");
            //同步请求方式,获取最新的Token
            HttpTokenBean httpTokenBean = getNewToken();
            if (httpTokenBean.getCode() == 401) {
                Intent intent = new Intent();
                intent.setAction("android.login");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            } else {
                LogUtils.e("ParameterInterceptor", "重新请求数据");
                //使用新的Token,创建新的请求
                //重新请求
                SPUtils.getInstance().put("token", httpTokenBean.getData());
                String token = SPUtils.getInstance().getString("token");
                Request newRequest = chain.request()
                        .newBuilder()
                        .header("Authorization", token)
                        .build();
                return chain.proceed(newRequest);
            }
        }else if (response.code() == 401){
            Intent intent = new Intent();
            intent.setAction("android.login");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        }
        return response;
    }

    /**
     * 根据Response,判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        LogUtils.e("ParameterInterceptor", "response.code():" + response.code());
        if (response.code() == 403) {
            return true;
        }else if(response.code() == 401){
            Intent intent = new Intent();
            intent.setAction("android.login");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        }
        return false;
    }

    /**
     * 同步请求方式,获取最新的Token
     *
     * @return
     */
    private HttpTokenBean getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token,此处要用到同步的retrofit请求
//        Response_Login loginInfo = CacheManager.restoreLoginInfo(BaseApplication.getContext());
//        String username = loginInfo.getUserName();
//        String password = loginInfo.getPassword();
//        LogUtils.e("loginInfo=" + loginInfo.toString());
//        Call<Response_Login> call = WebHelper.getSyncInterface().synclogin(new Request_Login(username, password));
//        loginInfo = call.execute().body();
//        LogUtils.e("loginInfo=" + loginInfo.toString());
//        loginInfo.setPassword(password);
//        CacheManager.saveLoginInfo(loginInfo);
        JSONObject object = new JSONObject();
        String refreshToken = SPUtils.getInstance().getString("refreshToken");
        LogUtils.e("ParameterInterceptor", refreshToken);
        try {
            object.put("Authorization", refreshToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody token = RequestBody.create(MediaType.parse("text/html"), object.toString());
        return RetrofitUtils.client(ApiStroe.class).refreshToken(token).execute().body();
    }
}
