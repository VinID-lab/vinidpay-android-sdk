package com.example.testsdk.utils

/**
 * Contains constants for VinID Pay SDK
 * Be careful, changing or using your own constants when start payment with VinID may leads to crashes
 *
 * Detail documents;
 *
 * Copyright (c) 2019 VinID. All rights reserved.
 */
class VinIDPayConstants {
    companion object {
        //
        const val SCHEMA = "one"
        const val HOST = "vinid.net"
        const val PATH_CHECKOUT = "checkout"
        //
        const val REQUEST_CODE_VINIDPAY_PAY = 824
        const val VINID_INSTALL_PACKAGE_NAME = "com.vingroup.vinid"
        const val VINID_INSTALL_PACKAGE_NAME_DEV = "com.vingroup.vinid.uat"

        // extra keys when get result from resultIntent
        const val EXTRA_RETURN_TRANSACTION_STATUS = "EXTRA_RETURN_TRANSACTION_STATUS"
        const val EXTRA_RETURN_ERROR_CODE = "EXTRA_RETURN_ERROR_CODE"
        // error code
        const val ERROR_ORDER_ID_NULL = 4002492
        const val ERROR_SIGNATURE_NULL = 4002493
        // transaction status
        const val TRANSACTION_PROCESSING = "PROCESSING"
        const val TRANSACTION_SUCCESS = "SUCCESS"
        const val TRANSACTION_FAIL = "FAIL"
        const val TRANSACTION_INIT = "INIT"
        const val TRANSACTION_HOLDING = "HOLDING"
        const val TRANSACTION_ABORT = "ABORT" // use for app to app when user abort payment
    }
}
