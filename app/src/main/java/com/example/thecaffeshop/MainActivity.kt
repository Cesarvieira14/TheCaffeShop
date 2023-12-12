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

        val userPrefs = Session.userPreference(this.applicationContext)
        if (userPrefs?.userId ?: 0 > 0) {
            if (userPrefs?.admin == true) {
                val menu = Intent(this, AdminHomeActivity::class.java)
                startActivity(menu)
            } else {
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