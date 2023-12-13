package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.thecaffeshop.ui.userHome.HomeActivity
import com.example.thecaffeshop.ui.account.LoginActivity
import com.example.thecaffeshop.ui.adminOrders.AdminHomeActivity
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.admin
import com.example.thecaffeshop.utils.Session.userId

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            handleStartButtonClick()
        }

        // Check if the user is already logged in
        val userPrefs = Session.userPreference(this.applicationContext)
        if (userPrefs?.userId ?: 0 > 0) {
            //check if the user is an admin --> open the AdminHomeActivity
            if (userPrefs?.admin == true) {
                val menu = Intent(this, AdminHomeActivity::class.java)
                startActivity(menu)
            } else {
                //check if the user is a customer --> open the HomeActivity
                val menu = Intent(this, HomeActivity::class.java)
                startActivity(menu)
            }
        }
    }

    private fun handleStartButtonClick() {
        val start = Intent (this, LoginActivity::class.java)
        startActivity(start)
    }
}