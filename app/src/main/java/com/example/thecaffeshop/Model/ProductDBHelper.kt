package com.example.thecaffeshop.model

import Constants.Companion.DB_NAME
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val ver: Int = 1

// SQL: CREATE TABLE Products (ProdId INTEGER PRIMARY KEY AUTOINCREMENT, ProdName TEXT, ProdDescription TEXT, ProdPrice REAL, ProdImage TEXT, ProdAvailable BOOLEAN)
class ProductDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, ver) {
    companion object {
        /* Products Table */
        val TableName = "Products"
        val Column_ProdId = "ProdId"
        val Column_ProdName = "ProdName"
        val Column_ProdDescription = "ProdDescription"
        val Column_ProdPrice = "ProdPrice"
        val Column_ProdImage = "ProdImage"
        val Column_ProdAvailable = "ProdAvailable"

        @SuppressLint("Range")
        fun parseProduct(cursor: Cursor): Product {
            val prodId = cursor.getInt(cursor.getColumnIndex(Column_ProdId))
            val prodName = cursor.getString(cursor.getColumnIndex(Column_ProdName))
            val prodDescription = cursor.getString(cursor.getColumnIndex(Column_ProdDescription))
            val prodPrice = cursor.getFloat(cursor.getColumnIndex(Column_ProdPrice))
            val prodImage = cursor.getString(cursor.getColumnIndex(Column_ProdImage))
            val prodAvailable = cursor.getInt(cursor.getColumnIndex(Column_ProdAvailable)) != 0

            return Product(
                prodId,
                prodName,
                prodDescription,
                prodPrice.toDouble(),
                prodImage,
                prodAvailable
            )
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_ProdId +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_ProdName + " TEXT, " +
                Column_ProdDescription + " TEXT, " +
                Column_ProdPrice + " REAL, " +
                Column_ProdImage + " TEXT, " +
                Column_ProdAvailable + " BOOLEAN )"

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getAllProducts(): ArrayList<Product> {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, arrayOf())

        val products: ArrayList<Product> = ArrayList();
        try {
            while (cursor.moveToNext()) {
                products.add(parseProduct(cursor))
            }
        } finally {
            cursor.close()
            db.close()
        }
        return products
    }

    fun editProduct(product: Product): Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_ProdName, product.prodName)
        cv.put(Column_ProdDescription, product.prodDescription)
        cv.put(Column_ProdPrice, product.prodPrice)

        val result = db.update(TableName, cv, "$Column_ProdId = ${product.prodId}", null) == 1
        db.close()
        return result
    }

}