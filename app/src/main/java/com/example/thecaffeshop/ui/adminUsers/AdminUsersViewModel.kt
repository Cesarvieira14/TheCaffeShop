package com.example.thecaffeshop.ui.adminUsers


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecaffeshop.model.AdminDBHelper
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderStatus
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.User
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.userId
import com.example.thecaffeshop.utils.Session.username

class AdminUsersViewModel(application: Application) : AndroidViewModel(application) {
    private val userName = Session.userPreference(application.applicationContext).username
    private val customerDBHelper: CustomerDBHelper =
        CustomerDBHelper(application.applicationContext)
    private val adminDBHelper: AdminDBHelper =
        AdminDBHelper(application.applicationContext)

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _filteredUsers = MutableLiveData<List<User>>()
    val filteredUsers: LiveData<List<User>> = _filteredUsers

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        fetchUsersList()
    }
    fun fetchUsersList() {
        val usersList = customerDBHelper.getAllUsers() + adminDBHelper.getAllAdmins()
        Log.d("AdminUsersViewModel", "Users List: $usersList")
        _users.value = usersList
    }

    fun filterUsers(userType: String) {
        val allUsers = _users.value ?: emptyList()
        val filterUsers = when (userType.lowercase()) {
            "all" -> allUsers
            // Add other filter cases as needed
            else -> allUsers.filter { it.isAdmin /* or any other condition */ }
        }
        _filteredUsers.value = filterUsers
    }

    fun selectUser(user: User) {
        _user.value = user

    }
}






