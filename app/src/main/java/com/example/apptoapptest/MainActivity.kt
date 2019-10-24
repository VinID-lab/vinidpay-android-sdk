package com.example.apptoapptest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            if (mEditTxt.text.isEmpty()) {
                Toast.makeText(this, "Ko duoc bo trong order id", Toast.LENGTH_SHORT).show()
            } else if (mEditTxt2.text.isEmpty()) {
                Toast.makeText(this, "Ko duoc bo trong signature", Toast.LENGTH_SHORT).show()
            } else {
                val params = VinIDPayParams.Builder()
                    .setOrderId(mEditTxt.text.toString())
                    .setSignature(mEditTxt2.text.toString())
                    .build()
                VinIDPaySdk.getInstance(params).startPaymentForResult(this)
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
