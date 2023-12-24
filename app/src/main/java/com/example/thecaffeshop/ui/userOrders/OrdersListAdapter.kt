package com.example.thecaffeshop.ui.userOrders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.Order

class OrdersListAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val ordersList: List<Order>,
) : ArrayAdapter<Order>(context, R.layout.orders_list_item, ordersList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = layoutInflater.inflate(R.layout.orders_list_item, null, true)

        val currentOrder = ordersList.get(position)

        val idText = rowView.findViewById(R.id.order_list_id) as TextView
        val datetimeText = rowView.findViewById(R.id.order_list_datetime) as TextView
        val statusText = rowView.findViewById(R.id.order_list_status) as TextView
        val amountText = rowView.findViewById(R.id.order_list_amount) as TextView

        idText.text = currentOrder.orderId.toString()
        datetimeText.text = currentOrder.orderDate
        statusText.text = currentOrder.orderStatus.toString()
        OrderFragment.formatOrderStatus(statusText)
        val formattedPrice = String.format("%.2f", currentOrder.payment.paymentAmount)
        amountText.text = "£$formattedPrice"


        return rowView
    }
}