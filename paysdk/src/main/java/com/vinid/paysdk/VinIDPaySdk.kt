package com.vinid.paysdk

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.vinid.paysdk.utils.VinIDPayConstants

/**
 * Main class to start payment with VinID app
 * use #{@link #startPaymentForResult(Activity activity)} to begin payment
 * detailed documentation:
 *
 * Copyright (c) 2019 VinID. All rights reserved.
 */
class VinIDPaySdk internal constructor(
    private val params: VinIDPayParams,
    private val environmentMode: EnvironmentMode
) {

    class Builder {
        private var params: VinIDPayParams? = null
        private var environmentMode: EnvironmentMode =
            EnvironmentMode.PRODUCTION

        /**
         * Set params for the SDK before startPayment
         * @param params created by using VinIDPayParams.Builder()
         * @return builder
         */
        fun setVinIDPayParams(params: VinIDPayParams): Builder {
            this.params = params
            return this
        }

        /**
         * Set environment for checking destination app. Default is PRODUCTION
         *
         * @param environmentMode EnvironmentMode enum
         * @return
         */
        fun setEnvironmentMode(environmentMode: EnvironmentMode): Builder {
            this.environmentMode = environmentMode
            return this
        }

        /**
         * Build VinIDPaySdk using defined params
         * @return VinIDPaySdk object
         */
        fun build(): VinIDPaySdk {
            requireNotNull(params) { "${javaClass::getSimpleName}: params must not be null" }
            return VinIDPaySdk(params!!, environmentMode)
        }
    }

    /**
     *
     * Start VINID payment activity for result using {@link Activity.startActivityForResult() }
     * with request code: {@link VinIDPayConstants.REQUEST_CODE_VINIDPAY_PAY }
     *
     * If user have VinID app
     *      then start payment for result
     * else
     *      redirect to google play store to install VinID app page
     *
     * @param activity the activity to receive result
     *
     */
    fun startPaymentForResult(activity: Activity) {
        if (isVinIdAppInstalled(activity)) {
            startPayment(activity, params)
        } else {
            openVinIDInstallPage(activity)
        }
    }

    private fun startPayment(activity: Activity, params: VinIDPayParams) {
        val uri = Uri.Builder().scheme(VinIDPayConstants.SCHEMA).authority(
            VinIDPayConstants.HOST)
            .appendPath(VinIDPayConstants.PATH_CHECKOUT)
        val intent = Intent().apply {
            putExtra(VinIDPayParams.EXTRA_ORDER_ID, params.orderId)
            putExtra(VinIDPayParams.EXTRA_SIGNATURE, params.signature)
            data = uri.build()
        }

        activity.startActivityForResult(intent, VinIDPayConstants.REQUEST_CODE_VINIDPAY_PAY)
    }

    private fun openVinIDInstallPage(activity: Activity) {
        val action = Intent.ACTION_VIEW
        val packageName = getPackageName()
        try {
            activity.startActivity(Intent(action, Uri.parse("market://details?id=$packageName")))
        } catch (t: Throwable) {
            activity.startActivity(
                Intent(
                    action,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    private fun isVinIdAppInstalled(activity: Activity): Boolean {
        return try {
            val packageMan = activity.packageManager
            packageMan.getPackageInfo(getPackageName(), 0)
            true
        } catch (t: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun getPackageName(): String {
        return if (environmentMode == EnvironmentMode.DEV)
            VinIDPayConstants.VINID_INSTALL_PACKAGE_NAME_DEV
        else
            VinIDPayConstants.VINID_INSTALL_PACKAGE_NAME
    }

    enum class EnvironmentMode {
        DEV,
        PRODUCTION
    }
}