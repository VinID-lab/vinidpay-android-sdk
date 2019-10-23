package com.example.testsdk.utils

/**
 * Contains constants for VinIDPayConst
 * Be careful, changing or using your own constants when start payment with VinID may leads to crashes
 *
 * Detail documents;
 *
 * Copyright (c) 2019 VinID. All rights reserved.
 */
class VinIDPayConst {
    companion object {
        //
        const val SCHEMA = "one"
        const val HOST = "vinid.net"
        const val PATH_CHECKOUT = "checkout"
        //
        const val REQUEST_CODE_VINIDPAY_PAY = 824
        const val VINID_INSTALL_PACKAGE_NAME = "com.vingroup.vinid"
        // extra keys when get result from resultIntent
        const val EXTRA_STRING_ORDER_STATUS = "EXTRA_STRING_ORDER_STATUS"
    }
}