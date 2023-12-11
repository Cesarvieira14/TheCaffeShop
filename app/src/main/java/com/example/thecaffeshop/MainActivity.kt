package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.thecaffeshop.ui.home.HomeActivity
import com.example.thecaffeshop.ui.user.LoginActivity
import com.example.thecaffeshop.utils.Session
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
            val menu = Intent(this, HomeActivity::class.java)
            startActivity(menu)
        }
    }

    private fun handleStartButtonClick() {
        val start = Intent (this, LoginActivity::class.java)
        startActivity(start)
    }
}