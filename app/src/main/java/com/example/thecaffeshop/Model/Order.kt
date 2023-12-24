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

enum class OrderStatus {
    Pending, Processing, Done, Cancelled
}

data class Order(
    var orderId: Int = -1,
    var orderDate: String = "",
    var orderTime: String = "",
    var orderStatus: OrderStatus = OrderStatus.Pending,
    var payment: Payment = Payment(),
    var user: User = User(),
    var products: List<Product> = emptyList(),
) {
    override fun toString(): String {
        return "Order(" +
                "orderId='$orderId', " +
                "orderDate='$orderDate', " +
                "orderTime='$orderTime', " +
                "orderStatus='$orderStatus', " +
                "payment='$payment', " +
                "user='$user', " +
                "products='$products'" +
                ")"
    }
}
