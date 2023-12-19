package com.example.thecaffeshop.ui.userStore

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderDetailsDBHelper
import com.example.thecaffeshop.model.OrdersDBHelper
import com.example.thecaffeshop.model.PaymentsDBHelper
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.ProductDBHelper
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.userId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val userId = Session.userPreference(application.applicationContext).userId
    private val customerDBHelper: CustomerDBHelper = CustomerDBHelper(application.applicationContext)
    private val productDBHelper: ProductDBHelper = ProductDBHelper(application.applicationContext)
    private val ordersDBHelper: OrdersDBHelper = OrdersDBHelper(application.applicationContext)
    private val paymentsDBHelper: PaymentsDBHelper = PaymentsDBHelper(application.applicationContext)
    private val orderDetailsDBHelper: OrderDetailsDBHelper = OrderDetailsDBHelper(application.applicationContext)

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    @RequiresApi(Build.VERSION_CODES.O)
    private  val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    private val _products = MutableLiveData<ArrayList<Product>>()
    val products: LiveData<ArrayList<Product>> = _products

    init {
        _products.value = productDBHelper.getAllProducts()
    }

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun selectProduct (product: Product) {
        _product.value = product
    }

    private val _cart = MutableLiveData<ArrayList<Product>>(arrayListOf())
    val cart: LiveData<ArrayList<Product>> = _cart

    fun addToCart (product: Product) {
        _cart.value?.add(product)
    }

    fun removeFromCart (product: Product) {
        val currentList = _cart.value ?: arrayListOf()
        currentList.remove(product)
        _cart.value=currentList
    }

    fun clearCart () {
        _cart.value?.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makeAnOrder (): Boolean {
        val currentUser = customerDBHelper.getUserByUserId(userId)

        val newOrder = Order()

        // Prepare initial order information
        newOrder.orderDate = LocalDateTime.now().format(dateFormatter)
        newOrder.orderTime = LocalDateTime.now().format(timeFormatter)
        newOrder.orderStatus = "Pending"
        if (currentUser != null) {
            newOrder.user = currentUser
        }

        try {
            // 1. Create new order in DB
            val orderId = ordersDBHelper.createOrder(newOrder)
            newOrder.orderId = orderId

            // 2. Associate all products in cart to the order
            if (orderId > 0) {
                var totalAmount: Double = 0.0

                _products.value?.forEach {product ->
                    orderDetailsDBHelper.createOrderDetails(newOrder.orderId, product.prodId)
                    totalAmount += product.prodPrice
                }

                // 3. Create new payment in DB
                newOrder.paymentAmount = totalAmount
                paymentsDBHelper.createPayment(newOrder)

                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return false
    }
}