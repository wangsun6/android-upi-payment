# Upi Payment


![API](https://img.shields.io/badge/API-16%2B-34bf49.svg)
[ ![Download](https://api.bintray.com/packages/wangsun6/android-upi-payment/com.wangsun.upi.payment/images/download.svg?version=latest) ](https://bintray.com/wangsun6/android-upi-payment/com.wangsun.upi.payment/latest/link)



### Demo:
![](/demo.gif)



## UpiPayment Library for Android (AndroidX)

A UpiPayment library for integrating upi payments using existing upi supported apps like googple pay, bhim etc.


## Setup
Step 1: Add the dependency

```gradle
dependencies {
    ...
    /*Upi Payment */
    implementation 'com.wangsun.upi.payment:upi-payment:0.0.2'
}
```

## Usage
Step 1: Declare and Initialize UpiPayment.

#### Java
```java

// note: always create new instance of PaymentDetail for every new payment/order
var payment = PaymentDetail(
    "wangsunhakhun@oksbi",  //vpa/upi = your vpa/upi
    "Wangsun Hakhun",       //name = your name
    "",                     //payeeMerchantCode = only if you have merchantCode else pass empty string
    "",                     //txnRefId =  if you pass empty string we will generate txnRefId for you
    "description",          //description =
    "2.00")                 //amount = format of amount should be in decimal format x.x (eg 530.00)

// note: always create new instance of UpiPayment for every new payment/order
new UpiPayment(this)
        .setPaymentDetail(payment)
        .setUpiApps(UpiPayment.getUPI_APPS())
        .setCallBackListener(new UpiPayment.OnUpiPaymentListener() {
            @Override
            public void onSubmitted(@NotNull TransactionDetails data) {
                //transaction pending: use data to get TransactionDetails
            }

            @Override
            public void onError(@NotNull String message) {
                //user backpress or transaction failed
            }

            @Override
            public void onSuccess(@NotNull TransactionDetails data) {
                //transaction success: use data to get TransactionDetails
            }
        }).pay();
```

#### Kotlin
```kotlin

// note: always create new instance of PaymentDetail for every new payment/order
var payment = PaymentDetail(
    vpa="wangsunhakhun@oksbi",
    name = "Wangsun Hakhun",
    payeeMerchantCode = "",       // only if you have merchantCode else pass empty string
    txnRefId = "",                // if you pass empty string we will generate txnRefId for you
    description = "description",
    amount = "2.00")              // format of amount should be in decimal format x.x (eg 530.00), max. 2 decimal places

// note: always create new instance of UpiPayment for every new payment/order
UpiPayment(this)
    .setPaymentDetail(payment)
    .setUpiApps(UpiPayment.UPI_APPS)
    .setCallBackListener(object : UpiPayment.OnUpiPaymentListener{
        override fun onSubmitted(data: TransactionDetails) {
            //transaction pending: use data to get TransactionDetails
        }
        override fun onSuccess(data: TransactionDetails) {
            //transaction success: use data to get TransactionDetails
        }
        override fun onError(message: String) {
            //user backpress or transaction failed
        }
    }).pay()
```

## Explanation:

#### 1. setPaymentDetail():
Set all payment details like vpa/upi, amount, name etc.
Note: always create new instance of PaymentDetail for every new payment/order.

#### 2. setUpiApps():
Set selected upiApps.

### Java
eg.
```java
//adding others upi apps to our default selected apps
//todo: check names of all apps first
//todo: name should be in lowercase
ArrayList<String> appList = UpiPayment.getUPI_APPS();
appList.add("new app name1");
appList.add("new app name2");

//adding new set of apps
ArrayList<String> newList = new ArrayList<String>();
newList.add("paytm")
newList.add("google pay")
newList.add("bhim")

//and pass this to: setUpiApps(newList): or setUpiApps(appList):

```

#### 3. setCallBackListener():
this will listen to the result of payment transaction(Only one callback will trigger for a single transaction).
1. **.onSuccess()**: trigger whenever transaction is successfully completed
2. **.onSubmitted()**: trigger whenever transaction is pending
3. **.onError()**: trigger whenever transaction is failed/user backpress/or other error

If you want to implement callBackListener() globally then implements UpiPayment.OnUpiPaymentListener.

### Java
eg.
```java
public class YourActivity extends AppCompatActivity implements UpiPayment.OnUpiPaymentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //override below functions
    @Override
    public void onSuccess(@NotNull TransactionDetails data) {}

    @Override
    public void onSubmitted(@NotNull TransactionDetails data) {}

    @Override
    public void onError(@NotNull String message) {}
}

```
---
### Extra function-> existingUpiApps(paymentDetail):
If developer wants to check existing upi apps.
Developer can hide(visibility) "Pay using Upi App" button  if there is not upi app present.

### Java
eg.
```java

ArrayList<String> existingUpiAppNames = UpiPayment.getExistingUpiApps(context);

```

---


### Quick Links

*  [ChangeLog](/CHANGELOG.md)

---

### To pick media files(audio,image,video) you can use [Turtlebody Media Picker](https://github.com/Turtlebody/android-media-picker) library.


### To pick doc files(txt,doc,docx,pdf etc) you can use [Turtlebody Doc Picker](https://github.com/Turtlebody/android-doc-picker) library.





