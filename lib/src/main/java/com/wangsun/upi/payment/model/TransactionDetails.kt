package com.wangsun.upi.payment.model

/**
 * Created by WANGSUN on 26-Sep-19.
 */

data class TransactionDetails(val transactionId: String?,
                              val responseCode: String?,
                              val approvalRefNo: String?,
                              val status: String?,
                              val transactionRefId: String?){
    var appName: String=""
}
