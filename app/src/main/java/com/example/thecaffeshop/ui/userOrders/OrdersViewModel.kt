package com.example.thecaffeshop.ui.userOrders

import android.app.Application
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderDetailsDBHelper
import com.example.thecaffeshop.model.OrderStatus
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

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _filteredOrders = MutableLiveData<List<Order>>()
    val filteredOrders: LiveData<List<Order>> = _filteredOrders

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    init {
        fetchOrdersList()
    }

    fun fetchOrdersList() {
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

        val sortedOrders = ordersList.sortedWith(compareBy { it.orderStatus })

        _orders.value = sortedOrders
        _filteredOrders.value = sortedOrders
    }

    fun filterOrders(orderStatus: String) {
        if (orderStatus.lowercase() == "all") {
            _filteredOrders.value = orders.value
        } else {
            val enumOrderStatus = OrderStatus.values().find { it.name == orderStatus } as OrderStatus
            when (enumOrderStatus) {
                OrderStatus.Pending ->
                    _filteredOrders.value = orders.value?.filter { order -> order.orderStatus == OrderStatus.Pending}
                OrderStatus.Processing ->
                    _filteredOrders.value = orders.value?.filter { order -> order.orderStatus == OrderStatus.Processing}
                OrderStatus.Preparing ->
                    _filteredOrders.value = orders.value?.filter { order -> order.orderStatus == OrderStatus.Preparing}
                OrderStatus.Collect ->
                    _filteredOrders.value = orders.value?.filter { order -> order.orderStatus == OrderStatus.Collect}
                OrderStatus.Cancelled ->
                    _filteredOrders.value = orders.value?.filter { order -> order.orderStatus == OrderStatus.Cancelled}
            }
        }
    }

    fun selectOrder(order: Order) {
        _order.value = order
    }

    fun cancelOrder(): Boolean {
        order.value?.orderId?.let {
            val cancelOrder = ordersDBHelper.updateOrderStatus(it, OrderStatus.Cancelled)
            // If yes, update orders list to reflect the update
            if (cancelOrder) {
                fetchOrdersList()
                return true;
            }
        }
        return false;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makePayment(type: String): Boolean {
        order.value?.let {
            val paymentUpdate = paymentsDBHelper.makePayment(it.payment.paymentId, type)
            if (paymentUpdate) {
                val orderStatusUpdate = ordersDBHelper.updateOrderStatus(it.orderId, OrderStatus.Processing)
                // If yes, update orders list to reflect the update
                if (orderStatusUpdate) {
                    fetchOrdersList()
                    // Update current view order
                    _orders.value?.find { order -> order.orderId == it.orderId }?.let { it1 -> selectOrder(it1) }
                    return true;
                }
            }
        }
        return false;
    }
}