package com.example.thecaffeshop.ui.adminProducts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.ProductDBHelper

class AdminProductsViewModel(application: Application) : AndroidViewModel(application) {

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

}