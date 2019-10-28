package com.example.apptoapptest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vinid.paysdk.VinIDPayParams
import com.vinid.paysdk.VinIDPaySdk
import com.vinid.paysdk.utils.VinIDPayConstants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        mCheckoutBtn.setOnClickListener {
            when {
                mOrderIdEditText.text.isEmpty() -> Toast.makeText(
                    this,
                    "Order id should not be empty",
                    Toast.LENGTH_SHORT
                ).show()

                mSignatureEditText.text.isEmpty() -> Toast.makeText(
                    this,
                    "Signature should not be empty",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {
                    val params = VinIDPayParams.Builder()
                        .setOrderId(mOrderIdEditText.text.toString())
                        .setSignature(mSignatureEditText.text.toString())
                        .build()

                    try {
                        VinIDPaySdk.Builder()
                            .setVinIDPayParams(params)
//                            .setEnvironmentMode(VinIDPaySdk.EnvironmentMode.DEV)
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
                Activity.RESULT_OK -> {
                    val transactionStatus =
                        data?.getStringExtra(VinIDPayConstants.EXTRA_RETURN_TRANSACTION_STATUS)
                    transactionStatus?.let { status ->
                        when (status) {
                            VinIDPayConstants.TRANSACTION_SUCCESS -> mStatusTextView.text =
                                "SUCCESS"

                            VinIDPayConstants.TRANSACTION_ABORT -> mStatusTextView.text =
                                "CANCELED"

                            VinIDPayConstants.TRANSACTION_FAIL -> {
                                mStatusTextView.text =
                                    "FAILED with ERROR CODE: ${data.getIntExtra(
                                        VinIDPayConstants.EXTRA_RETURN_ERROR_CODE,
                                        0
                                    )}"
                            }

                            else -> {
                                // Defined later
                            }

                        }
                    }
                }
            }
        }
    }
}
