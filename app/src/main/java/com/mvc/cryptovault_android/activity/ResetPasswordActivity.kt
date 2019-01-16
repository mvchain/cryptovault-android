package com.mvc.cryptovault_android.activity

import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity

import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reset_password.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class ResetPasswordActivity : BaseActivity(), View.OnClickListener {
    private var dialogHelper: DialogHelper? = null
    private lateinit var account: String
    private var type: Int = 0
    private lateinit var value: String
    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        account = intent.getStringExtra("token")
        type = intent.getIntExtra("type", -1)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reset_password
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.submit -> {
                value = login_pwd.text.toString()
                if (value == "") {
                    dialogHelper?.create(this, R.drawable.miss_icon, "登陆密码不可为空")?.show()
                    dialogHelper?.dismissDelayed(null)
                    return
                }
                dialogHelper?.create(this, R.drawable.pending_icon_1, "重置中")?.show()
                var json = JSONObject()
                json.put("token", account)
                json.put("password", value)
                var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                RetrofitUtils.client(ApiStore::class.java).userForget(body)
                        .compose(RxHelper.rxSchedulerHelper())
                        .subscribe({ updateBean ->
                            if (updateBean.code === 200) {
                                dialogHelper?.resetDialogResource(this, R.drawable.pending_icon_1, "密码修改成功")
                                dialogHelper?.dismissDelayed {
                                    startTaskActivity(this)
                                }
                            } else {
                                dialogHelper?.resetDialogResource(this, R.drawable.pending_icon_1, updateBean.message)
                                dialogHelper?.dismissDelayed { null }
                            }
                        }, { error ->
                            dialogHelper?.resetDialogResource(this, R.drawable.pending_icon_1, error.message)
                            dialogHelper?.dismissDelayed { null }
                        })
            }
        }
    }
}
