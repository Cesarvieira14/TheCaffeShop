package com.example.thecaffeshop.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

object Session {

    private const val USER_ID = "USER_ID"
    private const val USER_USERNAME = "USERNAME"
    private const val USER_ADMIN = "ADMIN"

    fun userPreference(context: Context): SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.userId
        get() = getInt(USER_ID, 0)
        set(value) {
            editMe {
                it.putInt(USER_ID, value)
            }
        }

    var SharedPreferences.username
        get() = getString(USER_USERNAME, "")
        set(value) {
            editMe {
                it.putString(USER_USERNAME, value)
            }
        }

    var SharedPreferences.admin
        get() = getBoolean(USER_ADMIN, false)
        set(value) {
            editMe {
                it.putBoolean(USER_ADMIN, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }
}
