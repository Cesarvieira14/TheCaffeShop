package com.example.thecaffeshop.ui.userStore

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.Product
import java.util.ArrayList
import java.util.concurrent.Executors

class CartListAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val productsList: ArrayList<Product>,
) : ArrayAdapter<Product>(context, R.layout.cart_list_item, productsList) {
    private lateinit var _handleProductRemove : (product: Product) -> Unit

    fun setHandleProductRemove(callback: (product: Product) -> Unit){
        _handleProductRemove = callback
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = layoutInflater.inflate(R.layout.cart_list_item, null, true)

        val currentProduct = productsList.get(position)

        val titleText = rowView.findViewById(R.id.cart_list_title) as TextView
        val imageView = rowView.findViewById(R.id.cart_list_icon) as ImageView
        val priceView = rowView.findViewById(R.id.cart_list_price) as TextView

        titleText.text = currentProduct.prodName
        val formattedPrice = String.format("%.2f", currentProduct.prodPrice)
        priceView.text = "£$formattedPrice"

        // Set product image
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            try {
                val `in` = java.net.URL(currentProduct.prodImage).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    imageView.setImageBitmap(image)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val deleteBtn = rowView.findViewById(R.id.cart_btn_delete) as Button
        deleteBtn.setOnClickListener {
            _handleProductRemove(currentProduct)
        }

        return rowView
    }
}