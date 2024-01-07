package com.example.thecaffeshop.ui.adminProducts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecaffeshop.model.Comment
import com.example.thecaffeshop.model.CommentsDBHelper
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.ProductDBHelper

class AdminProductsViewModel(application: Application) : AndroidViewModel(application) {
    private val productDBHelper: ProductDBHelper = ProductDBHelper(application.applicationContext)
    private val commentDBHelper: CommentsDBHelper = CommentsDBHelper(application.applicationContext)

    private val _products = MutableLiveData<ArrayList<Product>>()
    val products: LiveData<ArrayList<Product>> = _products

    private val commentsDBHelper: CommentsDBHelper =
        CommentsDBHelper(application.applicationContext)

    init {
        _products.value = productDBHelper.getAllProducts()
    }

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _productComments = MutableLiveData<List<Comment>>()
    val productComments: LiveData<List<Comment>> = _productComments

    fun selectProduct(product: Product) {
        val comments = commentsDBHelper.getAllCommentsForProduct(product.prodId)
        product.comments = comments

        _product.value = product
        _productComments.value = comments
    }

    fun removeComment(comment: Comment) {
        commentsDBHelper.deleteComment(comment.comId)
        // Fetch product comments async
        selectProduct(product.value!!)
    }

    fun updateProductsList() {
        val updatedList = productDBHelper.getAllProducts()
        _products.postValue(updatedList)
    }

}