package com.example.thecaffeshop.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.thecaffeshop.ui.userHome.HomeActivity
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.utils.Encryption
import com.example.thecaffeshop.utils.Session.admin
import com.example.thecaffeshop.utils.Session.userId
import com.example.thecaffeshop.utils.Session.userPreference
import com.example.thecaffeshop.utils.Session.username

class LoginActivity : AppCompatActivity() {

    private val customerDBHelper: CustomerDBHelper = CustomerDBHelper(this)
//    private val adminDBHelper: AdminDBHelper = AdminDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        // TODO: handle back button to not go to user page when logged out

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            handleLoginButtonClick()
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            handleRegisterButtonClick()
        }

        val userPrefs = userPreference(this.applicationContext)
        if (userPrefs?.userId ?: 0 > 0) {
            val menu = Intent(this, HomeActivity::class.java)
            startActivity(menu)
        }
    }

    private fun handleRegisterButtonClick() {
        val register = Intent(this, RegisterActivity::class.java)
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
            val menu = Intent(this, HomeActivity::class.java)
            startActivity(menu)
            Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        val customerUser = customerDBHelper.getUserByUsername(username)
        // TODO: handle admin login
        // val adminUser = adminDBHelper.getUserByUsername(username)

        if (customerUser == null) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!customerUser!!.isActive) {
            Toast.makeText(this, "Your account is not active", Toast.LENGTH_SHORT).show()
            return false
        }

        // Encrypt submitted password to check with the stored password
        val encryptedPassword = Encryption.hashString(password)
        if (customerUser!!.cusPassword != encryptedPassword) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            return false
        }

        val userPrefs = userPreference(this.applicationContext)
        userPrefs.userId = customerUser!!.id
        userPrefs.username = customerUser!!.cusUserName
        userPrefs.admin = false

        return true
    }
}

