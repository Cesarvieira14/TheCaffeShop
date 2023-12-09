package com.example.thecaffeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.userId
import com.example.thecaffeshop.utils.Session.username
import kotlin.math.log

class MenuPage : AppCompatActivity() {
    var simpleList: ListView? = null
    var productList= arrayOf("Coffe 1", "Coffe 2","Coffe 3","Coffe 4",
        "Coffe 5", "Coffe 6", "Coffe 7")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_page)

        simpleList = findViewById(R.id.menuListView)
        val arrayAdapter =
            ArrayAdapter(this,R.layout.menu_item_adapter, R.id.textView, productList)
        simpleList?.adapter = arrayAdapter

        val userPrefs = Session.userPreference(this)
        if (userPrefs?.userId ?: 0 === 0) {
            val login = Intent(this, LoginPage::class.java)
            startActivity(login)
        }
    }
}