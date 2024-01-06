package com.example.thecaffeshop.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.AdminDBHelper
import com.example.thecaffeshop.model.User
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.utils.Encryption

class RegisterActivity : AppCompatActivity() {

    private val customerDBHelper: CustomerDBHelper = CustomerDBHelper(this)
    private val adminDBHelper: AdminDBHelper = AdminDBHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Create your account"

        findViewById<Button>(R.id.btnRegisterUser).setOnClickListener {
            handleRegisterButtonClick()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle the Home button click
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleRegisterButtonClick() {
        val fullName = findViewById<EditText>(R.id.editTextFullname).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmailAddress).text.toString()
        val phoneNo = findViewById<EditText>(R.id.editTextPhone).text.toString()
        val userName = findViewById<EditText>(R.id.editTextUsername1).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword2).text.toString()

        // -----------------------Validation for the register

        //check for empty fields
        if (fullName.isBlank() || email.isBlank() || phoneNo.isBlank() || userName.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // check if email format is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return
        }

        // Add validation for phone number
        if (!phoneNo.matches(Regex("^\\d{6,15}$"))) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
            return
        }

        val user = customerDBHelper.getUserByUsername(userName)

        if (user !== null) {
            Toast.makeText(
                this,
                "Username already exists. Please choose a different one.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val encryptedPassword = Encryption.hashString(password)

        // Create a Customer object with the user details
        val customer = User(
            0, fullName, email, phoneNo, userName, encryptedPassword, true, false
        )

        val isRegistered = customerDBHelper.registerCustomer(customer);

        // Clear the fields
        if (isRegistered) {
            Toast.makeText(this, "You registered successfully", Toast.LENGTH_SHORT).show()

            findViewById<EditText>(R.id.editTextFullname).text.clear()
            findViewById<EditText>(R.id.editTextEmailAddress).text.clear()
            findViewById<EditText>(R.id.editTextPhone).text.clear()
            findViewById<EditText>(R.id.editTextUsername1).text.clear()
            findViewById<EditText>(R.id.editTextPassword2).text.clear()
        } else {
            Toast.makeText(this, "Error: account not created", Toast.LENGTH_SHORT).show()
        }
    }


}