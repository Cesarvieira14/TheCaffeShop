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
import android.widget.ImageView
import android.widget.TextView
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.Product
import java.util.concurrent.Executors

class ProductsListAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val productsList: List<Product>,
) : ArrayAdapter<Product>(context, R.layout.products_list_item, productsList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = layoutInflater.inflate(R.layout.products_list_item, null, true)

        val currentProduct = productsList.get(position)

        val titleText = rowView.findViewById(R.id.product_list_title) as TextView
        val imageView = rowView.findViewById(R.id.product_list_icon) as ImageView
        val priceView = rowView.findViewById(R.id.product_list_price) as TextView

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

        return rowView
    }
}