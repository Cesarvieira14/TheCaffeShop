package com.example.thecaffeshop.model

/*
 *   User Order Class
 *
 *   The Caffe Shop application's order is represented by this data class.
 *   It contains all of the essential details about a user, such as their full name,
 *   email address, phone number, username, password, and whether or not their account is active.
 *   It also shows if the user is logged in with administrator privileges.
 *
 */

data class Payment(
    var paymentId: Int = -1,
    var paymentDate: String = "",
    var paymentType: String = "",
    var paymentAmount: Double = 0.0,
) {
    override fun toString(): String {
        return "Payment(" +
                "paymentId='$paymentId', " +
                "paymentDate='$paymentDate', " +
                "paymentType='$paymentType', " +
                "paymentAmount='$paymentAmount'" +
                ")"
    }
}
