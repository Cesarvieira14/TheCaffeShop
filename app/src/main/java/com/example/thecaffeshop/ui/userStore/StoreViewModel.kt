package com.example.thecaffeshop.ui.userStore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.ProductDBHelper

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val productDBHelper: ProductDBHelper = ProductDBHelper(application.applicationContext)

    val products: MutableLiveData<ArrayList<Product>> by lazy {
        MutableLiveData<ArrayList<Product>>()
    }

    init {
        products.value = productDBHelper.getAllProducts()
    }
}