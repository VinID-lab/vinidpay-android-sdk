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
        const val EXTRA_ORDER_STATUS = "EXTRA_ORDER_STATUS"
        const val EXTRA_STRING_ORDER_STATUS = "EXTRA_STRING_ORDER_STATUS"
        const val ORDER_STATUS_FAILED = 0
        const val ORDER_STATUS_SUCCESS = 1
        const val ORDER_STATUS_CANCELED = 2
    }
}
