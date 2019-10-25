package com.example.apptoapptest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testsdk.VinIDPayParams
import com.example.testsdk.VinIDPaySdk
import com.example.testsdk.utils.VinIDPayConst
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        mTxtView.setOnClickListener {
            when {
                mEditTxt.text.isEmpty() -> Toast.makeText(
                    this,
                    "Order id should not be empty",
                    Toast.LENGTH_SHORT
                ).show()

                mEditTxt2.text.isEmpty() -> Toast.makeText(
                    this,
                    "Signature should not be empty",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {
                    val params = VinIDPayParams.Builder()
                        .setOrderId(mEditTxt.text.toString())
                        .setSignature(mEditTxt2.text.toString())
                        .build()

                    try {
                        VinIDPaySdk.Builder()
                            .setParams(params)
                            .build()
                            .startPaymentForResult(this)
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }
                }
            }
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
                returnedValue.text = status
            }
        }
    }
}
