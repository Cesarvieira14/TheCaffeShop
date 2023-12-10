package com.example.thecaffeshop.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoreViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is store fragment"
    }
    val text: LiveData<String> = _text
}