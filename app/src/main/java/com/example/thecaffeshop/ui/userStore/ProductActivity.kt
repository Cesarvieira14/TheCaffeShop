package com.example.thecaffeshop.ui.userStore

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thecaffeshop.databinding.ActivityProductBinding
import com.example.thecaffeshop.model.Product
import java.util.concurrent.Executors

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding

    companion object {
        const val PRODUCT_TAG: String = "product"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra(PRODUCT_TAG) as Product

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Store - ${product.prodName}"

        binding.productTitle.text = product.prodName
        binding.productDescription.text = product.prodDescription

        // Set product image
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            try {
                val `in` = java.net.URL(product.prodImage).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    binding.productImage.setImageBitmap(image)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.productAddCart.setOnClickListener {
            handleAddToCartBtnClick(product)
        }
    }

    fun handleAddToCartBtnClick(product: Product) {
        if (!product.prodAvailable) {
            Toast.makeText(this, "Product is not available", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: add to cart
        Toast.makeText(this, "Added product to your cart", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}