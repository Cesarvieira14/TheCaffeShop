package com.example.thecaffeshop.ui.adminOrders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderDetailsDBHelper
import com.example.thecaffeshop.model.OrderStatus
import com.example.thecaffeshop.model.OrdersDBHelper
import com.example.thecaffeshop.model.PaymentsDBHelper

class AdminOrdersViewModel(application: Application) : AndroidViewModel(application) {

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

        val allOrders = ordersDBHelper.getAllOrders()

        allOrders.forEach { order ->
            order.products = orderDetailsDBHelper.getProductsByOrderId(order.orderId)
            order.payment = paymentsDBHelper.getPaymentByOrderId(order.orderId)
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
            val enumOrderStatus =
                OrderStatus.values().find { it.name == orderStatus } as OrderStatus
            when (enumOrderStatus) {
                OrderStatus.Pending ->
                    _filteredOrders.value =
                        orders.value?.filter { order -> order.orderStatus == OrderStatus.Pending }
                OrderStatus.Processing ->
                    _filteredOrders.value =
                        orders.value?.filter { order -> order.orderStatus == OrderStatus.Processing }
                OrderStatus.Collect ->
                    _filteredOrders.value =
                        orders.value?.filter { order -> order.orderStatus == OrderStatus.Collect }

                OrderStatus.Done ->
                    _filteredOrders.value =
                        orders.value?.filter { order -> order.orderStatus == OrderStatus.Done }
                OrderStatus.Cancelled ->
                    _filteredOrders.value =
                        orders.value?.filter { order -> order.orderStatus == OrderStatus.Cancelled }
            }
        }
    }

    fun selectOrder(order: Order) {
        _order.value = order
    }


    fun updateOrderStatus(orderId: Int, newStatus: OrderStatus): Boolean {
        val orderStatusUpdate = ordersDBHelper.updateOrderStatus(orderId, newStatus)
        if (orderStatusUpdate) {
            fetchOrdersList()
            // Update current view order
            _orders.value?.find { order -> order.orderId == orderId }?.let { selectOrder(it) }
            return true
        }else {
            return false
        }
    }
}