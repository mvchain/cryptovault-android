package com.mvc.cryptovault_android.utils

import android.content.Context
import android.content.res.Configuration
import android.support.annotation.NonNull
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.mvc.cryptovault_android.bean.LanguageEvent
import com.mvc.cryptovault_android.common.Constant
import com.mvc.cryptovault_android.common.Constant.LANGUAGE.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class LanguageUtils {
    companion object {
        //获取用户设置的Local
        fun getUserSetLocal(): String {
            return SPUtils.getInstance().getString(DEFAULT_LANGUAGE)
        }

        fun wrapConfiguration(context: Context, config: Configuration): Context {
            return context.createConfigurationContext(config)
        }

        fun wrapLocale(context: Context, locale: Locale): Context {
            var res = context.resources
            var config = res.configuration
            config.setLocale(locale)
            return wrapConfiguration(context, config)
        }

        fun changeLocale(language: String, accept_language: String, configuration: Configuration, baseContext: Context) {
            var locale = Locale(language)
            configuration.setLocale(locale)
            baseContext.createConfigurationContext(configuration)
            SPUtils.getInstance().put(DEFAULT_LANGUAGE, language)
            SPUtils.getInstance().put(DEFAULT_ACCEPT_LANGUAGE, accept_language)
            SPUtils.getInstance().put(DEFAULT_ENGLUSH, "custom")
            EventBus.getDefault().post(LanguageEvent())
        }
    }
}