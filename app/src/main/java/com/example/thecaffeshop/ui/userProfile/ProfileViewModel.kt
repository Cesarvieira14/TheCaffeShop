package com.example.thecaffeshop.ui.userProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.userId

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val customerDBHelper: CustomerDBHelper = CustomerDBHelper(application.applicationContext)
    private val userId = Session.userPreference(application.applicationContext).userId

    val userName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userFullName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userPhone: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun fetchUserData () {
        val customerUser = customerDBHelper.getUserByUserId(userId)

        if (customerUser != null) {
            userName.value = customerUser.userName
            userEmail.value = customerUser.email
            userFullName.value = customerUser.fullName
            userPhone.value = customerUser.phoneNo
        }
    }
}