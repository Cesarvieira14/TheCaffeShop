package com.example.thecaffeshop.ui.store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.Product

class ProductActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_TAG: String = "product"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = Product(0, "", "", 0.0, "", false)

        findViewById<TextView>(R.id.product_title).text = product.prodName
        findViewById<TextView>(R.id.product_description).text = product.prodDescription
    }
}