package com.example.thecaffeshop.model

/*
 *   User Data Class
 *
 *   The Caffe Shop application's user is represented by this data class.
 *   It contains all of the essential details about a user, such as their full name,
 *   email address, phone number, username, password, and whether or not their account is active.
 *   It also shows if the user is logged in with administrator privileges.
 *
 */

data class User(
    val id: Int,
    var fullName: String,
    var email: String,
    var phoneNo: String,
    var userName: String,
    var password: String,
    var isActive: Boolean,
    var isAdmin: Boolean
) {
    override fun toString(): String {
        return "Customer(" +
                "id='$id', " +
                "fullName='$fullName', " +
                "email='$email', " +
                "phoneNo='$phoneNo', " +
                "userName='$userName', " +
                "password='$password', " +
                "isActive=$isActive" +
                "isAdmin=$isAdmin" +
                ")"
    }
}
