package com.example.thecaffeshop.model

data class Product(
    val prodId: Int,
    var prodName: String,
    var prodDescription: String,
    var prodPrice: Double,
    var prodImage: String,
    var prodAvailable: Boolean,
) {
    override fun toString(): String {
        return "Product(" +
                "prodId='$prodId', " +
                "prodName='$prodName', " +
                "prodDescription='$prodDescription', " +
                "prodPrice='$prodPrice', " +
                "prodImage='$prodImage', " +
                "prodAvailable='$prodAvailable', " +
                ")"
    }
}
