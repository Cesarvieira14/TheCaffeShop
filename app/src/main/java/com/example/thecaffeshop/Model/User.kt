package com.example.thecaffeshop.model

data class User(
    val id: Int,
    var fullName: String,
    var email: String,
    var phoneNo: String,
    var cusUserName: String,
    var cusPassword: String,
    var isActive: Boolean,
    var isAdmin: Boolean
) {
    override fun toString(): String {
        return "Customer(" +
                "id='$id', " +
                "fullName='$fullName', " +
                "email='$email', " +
                "phoneNo='$phoneNo', " +
                "userName='$cusUserName', " +
                "password='$cusPassword', " +
                "isActive=$isActive" +
                "isAdmin=$isAdmin" +
                ")"
    }
}
