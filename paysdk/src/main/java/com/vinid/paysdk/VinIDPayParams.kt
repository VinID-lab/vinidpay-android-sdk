package com.vinid.paysdk

import java.lang.IllegalArgumentException

/**
 * Wrapper class contains parameters to send to VinID app to start payment
 * Do not change constants in this class
 * Use {@link VinIDPayParamsBuilder} to create a VinIDPayParams object
 *
 *  @param orderId id of order
 *  @param signature signature provided by your backend with key pairs provided
 *  Detailed documents:
 *
 * Copyright (c) 2019 VinID. All rights reserved.
 */
data class VinIDPayParams internal constructor(
    val orderId: String? = null,
    val signature: String? = null
) {
    companion object {
        const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"
        const val EXTRA_SIGNATURE = "EXTRA_SIGNATURE"
    }

    /**
     * Builder class to create VinIDPayParams object ( using builder pattern )
     * Params meaning are listed in {@link VinIDPayParams}
     * Detailed documents:
     *
     * Example:
     * #Kotlin: val params = VinIDPayParamsBuilder().setOrderId().build()
     * #Java: VinIDPayParams params = VinIDPayParamsBuilder().setOrderId().build()
     *
     * Copyright (c) 2019 VinID. All rights reserved.
     */
    class Builder {
        private var orderId: String? = null
        private var signature: String? = null

        /**
         * Setter for orderId
         * @param orderId String? ...
         */
        fun setOrderId(orderId: String?) = setOrThrowExceptionIfNull(Builder::orderId.name, orderId) { this.orderId = orderId }

        /**
         * Setter for signature
         * @param signature String? ...
         */
        fun setSignature(signature: String?) = setOrThrowExceptionIfNull(Builder::signature.name, signature) { this.signature = signature }

        private fun setOrThrowExceptionIfNull(fieldName: String, data: Any?, setFunc: () -> Unit): Builder {
            data?.let {
                setFunc.invoke()
            } ?: run {
                throw IllegalArgumentException(">>> VinIDPayParamsBuilder: $fieldName must not be null")
            }
            return this
        }

        private fun validateParams(): Boolean {
            return !(orderId.isNullOrEmpty() || signature.isNullOrEmpty())
        }

        /**
         * Use to build VinIDPayParams from builder
         */
        fun build(): VinIDPayParams {
            if (validateParams()) {
                return VinIDPayParams(orderId, signature)
            } else {
                throw IllegalArgumentException(">>> VinIDPayParamsBuilder build: some required fields are left null or empty")
            }
        }
    }
}

