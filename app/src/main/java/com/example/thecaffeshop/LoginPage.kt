package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
    }
    fun registerButton(view: View){
        val register = Intent (this, RegisterPage::class.java)
        startActivity(register)
    }
    fun loginButton(view: View){
        val login = Intent (this, MenuPage::class.java)
        startActivity(login)
    }
}

