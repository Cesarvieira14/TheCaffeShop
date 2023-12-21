package com.example.thecaffeshop.ui.userOrders

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
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.Product
import java.util.ArrayList
import java.util.concurrent.Executors

class OrdersListAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val ordersList: ArrayList<Order>,
) : ArrayAdapter<Order>(context, R.layout.orders_list_item, ordersList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = layoutInflater.inflate(R.layout.orders_list_item, null, true)

        val currentOrder = ordersList.get(position)

        val idText = rowView.findViewById(R.id.order_list_id) as TextView
        val datetimeText = rowView.findViewById(R.id.order_list_datetime) as TextView
        val statusText = rowView.findViewById(R.id.order_list_status) as TextView
        val amountText = rowView.findViewById(R.id.order_list_amount) as TextView

        idText.text = currentOrder.orderId.toString()
        datetimeText.text = "${currentOrder.orderDate} ${currentOrder.orderTime}"
        statusText.text = currentOrder.orderStatus
        val formattedPrice = String.format("%.2f", currentOrder.payment.paymentAmount)
        amountText.text = "£$formattedPrice"

        return rowView
    }
}