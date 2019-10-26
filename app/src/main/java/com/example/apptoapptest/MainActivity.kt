package com.example.apptoapptest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testsdk.VinIDPayParams
import com.example.testsdk.VinIDPaySdk
import com.example.testsdk.utils.VinIDPayConstants
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
                            .setVinIDPayParams(params)
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
        if (requestCode == VinIDPayConstants.REQUEST_CODE_VINIDPAY_PAY) {
            when (resultCode) {
                VinIDPayConstants.ORDER_STATUS_SUCCESS -> {
                    // SUCCESS
                    returnedValue.text = "SUCCESS"
                }

                VinIDPayConstants.ORDER_STATUS_CANCELED -> {
                    // CANCELED
                    returnedValue.text = "CANCELED"
                }

                else -> {
                    // FAILED
                    returnedValue.text =
                        data?.getStringExtra(VinIDPayConstants.EXTRA_STRING_ORDER_STATUS)
                }
            }
        }

    }
}
