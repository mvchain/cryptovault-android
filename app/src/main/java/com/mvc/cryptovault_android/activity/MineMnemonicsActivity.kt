package com.mvc.cryptovault_android.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.MineMnemonicsAdapter
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.SP.REG_MINEMNEMONICS
import com.mvc.cryptovault_android.common.Constant.SP.REG_PRIVATEKEY
import kotlinx.android.synthetic.main.activity_mine_mnemonics.*
import java.util.ArrayList

class MineMnemonicsActivity : BaseActivity(), View.OnClickListener {
    var menJson = SPUtils.getInstance().getString(REG_MINEMNEMONICS)!!
    var privatekey = SPUtils.getInstance().getString(REG_PRIVATEKEY)!!
    private lateinit var mnemonicss: ArrayList<String>
    override fun getLayoutId(): Int {
        return R.layout.activity_mine_mnemonics
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        mnemonicss = menJson.split(",") as ArrayList
        mnemonicss.removeAt(mnemonicss.size - 1)
        mnemonics_list.adapter = MineMnemonicsAdapter(R.layout.item_mnemonics_rv, mnemonicss)
        private_key.text = privatekey
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.private_key -> {
                var clipManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipText = ClipData.newPlainText("private_key", private_key.text.toString())
                clipManager.primaryClip = clipText
                Toast.makeText(this, "内容已复制至剪贴板", Toast.LENGTH_SHORT).show()
            }
            R.id.submit -> {
                var intent = Intent(this, VerificationMnemonicActivity::class.java)
                intent.putStringArrayListExtra("menmonicss", mnemonicss)
                startActivity(intent)
            }
        }
    }
}