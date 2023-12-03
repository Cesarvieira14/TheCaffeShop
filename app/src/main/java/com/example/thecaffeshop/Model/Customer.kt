package com.example.thecaffeshop.Model

data class Customer(val id: Int,
                    var fullName: String,
                    var email: String,
                    var phoneNo: String,
                    var cusUserName: String,
                    var cusPassword: String,
                    var isActive: Boolean
) {
    override fun toString(): String {
        return "Customer(fullName='$fullName', " +
                "cusEmail='$email', " +
                "phoneNo='$phoneNo', " +
                "cusUserName='$cusUserName', " +
                "cusPassword='$cusPassword', " +
                "isActive=$isActive)"
    }
}
