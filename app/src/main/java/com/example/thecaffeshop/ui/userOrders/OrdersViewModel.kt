package com.example.thecaffeshop.ui.userOrders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderDetailsDBHelper
import com.example.thecaffeshop.model.OrdersDBHelper
import com.example.thecaffeshop.model.PaymentsDBHelper
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.userId

class OrdersViewModel(application: Application) : AndroidViewModel(application) {
    private val userId = Session.userPreference(application.applicationContext).userId
    private val customerDBHelper: CustomerDBHelper =
        CustomerDBHelper(application.applicationContext)
    private val ordersDBHelper: OrdersDBHelper = OrdersDBHelper(application.applicationContext)
    private val paymentsDBHelper: PaymentsDBHelper =
        PaymentsDBHelper(application.applicationContext)
    private val orderDetailsDBHelper: OrderDetailsDBHelper =
        OrderDetailsDBHelper(application.applicationContext)

    private val _orders = MutableLiveData<ArrayList<Order>>()
    val orders: LiveData<ArrayList<Order>> = _orders

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    init {
        var ordersList = arrayListOf<Order>()

        val user = customerDBHelper.getUserByUserId(userId)

        val userOrders = ordersDBHelper.getOrdersByUserId(userId)

        userOrders.forEach { order ->
            order.products = orderDetailsDBHelper.getProductsByOrderId(order.orderId)
            order.payment = paymentsDBHelper.getPaymentByOrderId(order.orderId)
            if (user != null) {
                order.user = user
            }

            ordersList.add(order)
        }

        _orders.value = ordersList
    }

    fun selectOrder(order: Order) {
        _order.value = order
    }
}