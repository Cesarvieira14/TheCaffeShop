package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.thecaffeshop.Model.AdminDBHelper
import com.example.thecaffeshop.Model.CustomerDBHelper

class LoginPage : AppCompatActivity() {

    val customerDBHelper: CustomerDBHelper = CustomerDBHelper(this)
    val adminDBHelper: AdminDBHelper = AdminDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
    }

    fun registerButton(view: View) {
        val register = Intent(this, RegisterPage::class.java)
        startActivity(register)
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        val customerUser = customerDBHelper.getUserByUsername(username)
        val adminUser = adminDBHelper.getUserByUsername(username)

        if ((customerUser !== null && customerUser.cusPassword == password && customerUser.isActive) ||
            (adminUser !== null && adminUser.cusPassword == password && adminUser.isActive)) {
            return true
        }

        return false
    }


    fun loginButton(view: View) {
        val username = findViewById<EditText>(R.id.editTextUsername1).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword1).text.toString()

        if (authenticateUser(username, password)) {
            // User exists, proceed to next activity
            val login = Intent(this, MenuPage::class.java)
            startActivity(login)
            Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
        } else {
            // User doesn't exist, show error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }


    }


}

