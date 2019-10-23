package com.example.apptoapptest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testsdk.VinIDPayParams
import kotlinx.android.synthetic.main.activity_main.*
import com.example.testsdk.VinIDPaySdk
import com.example.testsdk.utils.VinIDPayConst

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        mTxtView.setOnClickListener {
            val params = VinIDPayParams.Builder()
                .setOrderId(mEditTxt.text.toString())
                .setSignature("ac")
                .build()
            VinIDPaySdk.getInstance(params).startPaymentForResult(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VinIDPayConst.REQUEST_CODE_VINIDPAY_PAY) {
                data?.extras?.keySet()?.forEach {
                    println("ZZLL key $it | data ${data.extras?.get(it)}")
                }
                val status = data?.getStringExtra(VinIDPayConst.EXTRA_STRING_ORDER_STATUS)
                mTxtView.text = status
            }
        }
    }
}
