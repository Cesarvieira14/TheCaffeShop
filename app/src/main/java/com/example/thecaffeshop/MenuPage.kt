package com.example.thecaffeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

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


    }
}