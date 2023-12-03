package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.thecaffeshop.Model.Customer
import com.example.thecaffeshop.Model.DataBaseHelper

class RegisterPage : AppCompatActivity() {

    val dbHelper: DataBaseHelper = DataBaseHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)


        // Get actionBar
        val actionBar: ActionBar? = supportActionBar

        // Enable the Up button
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle the Home button click
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun btRegisterCustomer(view: View) {

        val cusFullName = findViewById<EditText>(R.id.editTextFullname).text.toString()
        val cusEmail = findViewById<EditText>(R.id.editTextEmailAddress).text.toString()
        val cusPhoneNo = findViewById<EditText>(R.id.editTextPhone).text.toString()
        val cusUserName = findViewById<EditText>(R.id.editTextUsername1).text.toString()
        val cusPassword = findViewById<EditText>(R.id.editTextPassword2).text.toString()
        val cusIsActive: Boolean = true

        // -----------------------Validation for the register

        //check for empty fields

        if (cusFullName.isBlank() || cusEmail.isBlank() || cusPhoneNo.isBlank() || cusUserName.isBlank() || cusPassword.isBlank()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // check if email format is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(cusEmail).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return
        }

        // Add validation for phone number ----DONE
        if (!cusPhoneNo.matches(Regex("^\\d{6,15}$"))) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
            return
        }

        val user = dbHelper.getUserByUsername(cusUserName)

        if (user !== null) {
            Toast.makeText(this, "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show()
            return
        }



        //----------------------------------- end of validation fro register





        // Create a Customer object with the user details
        val customer = Customer(
            0, cusFullName, cusEmail, cusPhoneNo, cusUserName, cusPassword,
            cusIsActive
        )


        // Clear the fields
        if (dbHelper.registerCustomer(customer)) {
            Toast.makeText(this, "You registered successfully", Toast.LENGTH_SHORT).show()
            findViewById<EditText>(R.id.editTextFullname).text.clear()
            findViewById<EditText>(R.id.editTextEmailAddress).text.clear()
            findViewById<EditText>(R.id.editTextPhone).text.clear()
            findViewById<EditText>(R.id.editTextUsername1).text.clear()
            findViewById<EditText>(R.id.editTextPassword2).text.clear()

        } else Toast.makeText(this, "Error: The employee not added", Toast.LENGTH_SHORT).show()
    }


}