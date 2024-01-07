package com.example.thecaffeshop

import com.example.thecaffeshop.model.Product
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ObjectsUnitTests {
    @Test
    fun isProductModelCorrect() {
        assertEquals(
            Product(
                1,
                "Coffee",
                "Delicious coffee",
                2.5,
                "imageUrl",
                true,
                3,
                arrayListOf()
            ).toString(),
            "Product(prodId='1', prodName='Coffee', prodDescription='Delicious coffee', prodPrice='2.5', prodImage='imageUrl', prodAvailable='true', prodRating='3', comments='[]')"
        )
    }
}