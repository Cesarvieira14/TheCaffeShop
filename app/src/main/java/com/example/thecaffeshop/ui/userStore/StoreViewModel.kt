package com.example.thecaffeshop.ui.userStore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.ProductDBHelper

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val productDBHelper: ProductDBHelper = ProductDBHelper(application.applicationContext)

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
}