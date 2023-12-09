package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.thecaffeshop.Model.AdminDBHelper
import com.example.thecaffeshop.Model.CustomerDBHelper

class LoginPage : AppCompatActivity() {

    private val customerDBHelper: CustomerDBHelper = CustomerDBHelper(this)
    private val adminDBHelper: AdminDBHelper = AdminDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            handleLoginButtonClick()
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            handleRegisterButtonClick()
        }
    }

    private fun handleRegisterButtonClick() {
        val register = Intent(this, RegisterPage::class.java)
        startActivity(register)
    }

    private fun handleLoginButtonClick() {
        val username = findViewById<EditText>(R.id.editTextUsername1).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword1).text.toString()

        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

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

    private fun authenticateUser(username: String, password: String): Boolean {
        val customerUser = customerDBHelper.getUserByUsername(username)
        val adminUser = adminDBHelper.getUserByUsername(username)

        val isCustomer = customerUser !== null && customerUser.cusPassword == password && customerUser.isActive
        val isAdmin = adminUser !== null && adminUser.cusPassword == password && adminUser.isActive

        // TODO: set user app session

        return isCustomer || isAdmin
    }
}

