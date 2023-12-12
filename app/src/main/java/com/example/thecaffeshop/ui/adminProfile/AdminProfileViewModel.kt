package com.example.thecaffeshop.ui.adminProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.thecaffeshop.model.AdminDBHelper
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.userId

class AdminProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val adminDBHelper: AdminDBHelper = AdminDBHelper(application.applicationContext)
    private val adminId = Session.userPreference(application.applicationContext).userId

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
        val adminUser = adminDBHelper.getAdminByAdminId(adminId)

        if (adminUser != null) {
            userName.value = adminUser.userName
            userEmail.value = adminUser.email
            userFullName.value = adminUser.fullName
            userPhone.value = adminUser.phoneNo
        }
    }
}