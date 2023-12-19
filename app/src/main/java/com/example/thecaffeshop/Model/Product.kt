package com.example.thecaffeshop.model

import java.io.Serializable

data class Product(
    val prodId: Int,
    var prodName: String,
    var prodDescription: String,
    var prodPrice: Double,
    var prodImage: String,
    var prodAvailable: Boolean,
): Serializable {
    override fun toString(): String {
        return "Product(" +
                "prodId='$prodId', " +
                "prodName='$prodName', " +
                "prodDescription='$prodDescription', " +
                "prodPrice='$prodPrice', " +
                "prodImage='$prodImage', " +
                "prodAvailable='$prodAvailable'" +
                ")"
    }
}
