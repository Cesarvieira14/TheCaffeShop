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

data class Order(
    val orderId: Int,
    var orderDate: String,
    var orderTime: String,
    var orderStatus: String,
    val paymentId: Int,
    val paymentDate: String,
    val paymentType: String,
    val paymentAmount: Double,
    val user: User,
    val products: List<Product>,
) {
    override fun toString(): String {
        return "Customer(" +
                "orderId='$orderId', " +
                "orderDate='$orderDate', " +
                "orderTime='$orderTime', " +
                "orderStatus='$orderStatus', " +
                "paymentId='$paymentId', " +
                "paymentDate='$paymentDate', " +
                "paymentType='$paymentType', " +
                "paymentAmount='$paymentAmount', " +
                "user='$user', " +
                "products='$products'" +
                ")"
    }
}
