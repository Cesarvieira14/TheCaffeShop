package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            handleStartButtonClick()
        }
    }

    private fun handleStartButtonClick() {
        val start = Intent (this, LoginPage::class.java)
        startActivity(start)
    }
}