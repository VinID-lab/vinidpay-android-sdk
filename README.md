# VinIDPay Android SDK

VinIDPay Android SDK helps you handle payments with VinID Android app.

## Requirements
- Minimum SDK version: 16
- Recommend SDK version: 21 // Current minimum SDK version of VinID App
- Minimum VinID App's version: // TBD

## Installation

#### Remote Repo

In root project's `build.gradle`:

```
allprojects {
		repositories {
			...
			maven { url "https://android-nexus.vinid.dev/repository/android-bifrost/" }
		}
	}
```

In app's `build.gradle`:

```
dependencies {
    implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation "com.vingroup.vinid.pay:pay-sdk:1.0.0"
    ...
}
```

*NOTE: Because this SDK is written in Kotlin so make sure your app supports kotlin's standard lib.*

#### Manual Install

You can pull this repo and build the AAR file manually.
Recommended requirement:
- Gradle 5.4.1

## Usage
The current version only supports Checkout flow by 2 following steps:

- Step 1: Create Checkout params:

```kotlin
  val params = VinIDPayParams.Builder()
    .setOrderId([Your Order ID])
    .setSignature([Your Signature])
    .build()
```

- Step 2: pass params to`VinIDPaySdk` and start Checkout flow from VinID App:

```kotlin
  VinIDPaySdk.Builder()
    .setVinIDPayParams(params)
    .build()
    .startPaymentForResult(YourActivity.this)
```

`startPaymentForResult()` will run a Deeplink and forward you to Checkout flow of VinID app installed in your device. 
When the flow is finished (Which can be SUCCESS/FAILED/ABORTED), you can handle the result by using `onActivityResult()` with `requestCode == inIDPayConstants.REQUEST_CODE_VINIDPAY_PAY`:

```kotlin
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
```

At current version, we always `return resultCode == Activity.RESULT_OK`, and you can filter the transaction result from:

```kotlin
data?.getStringExtra(VinIDPayConstants.EXTRA_RETURN_TRANSACTION_STATUS)
```

In case the `transactionResult == VinIDPayConstants.TRANSACTION_FAIL`, you can check the error code:

```kotlin
data.getIntExtra(VinIDPayConstants.EXTRA_RETURN_ERROR_CODE,0)
```

## Sandbox mode (Development Mode)
If you want to test the transaction on our UAT app (Dev version), you can set Environment mode param while building `VinIDPaySdk`:

```kotlin
  VinIDPaySdk.Builder()
    .setVinIDPayParams(params)
    .setEnvironmentMode(VinIDPaySdk.EnvironmentMode.DEV)    <--- HERE
    .build()
    .startPaymentForResult(this)
```

By default, the SDK will set to `VinIDPaySdk.EnvironmentMode.PRODUCTION`.

*NOTE: This mode is only used to check if DEV/PROD VinID app is installed or not and wont affect to your checkout flow. It means the deeplink you send can be handled by both DEV and PROD app.*
