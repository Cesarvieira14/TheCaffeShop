package com.example.thecaffeshop.model

import Constants.Companion.DB_NAME
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val ver: Int = 1

class ProductDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, ver) {

    /* Products Table */
    val TableName = "Products"
    val Column_ID = "ProdId"
    val Column_ProdName = "ProdName"
    val Column_ProdDescription = "ProdDescription"
    val Column_ProdPrice = "ProdPrice"
    val Column_ProdImage = "ProdImage"
    val Column_ProdAvailable = "ProdAvailable"


    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_ID +
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

        val sqlStatement = "SELECT * FROM $TableName WHERE $Column_ProdImage = ?"

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

    @SuppressLint("Range")
    fun parseProduct(cursor: Cursor): Product {
        val prodId = cursor.getInt(cursor.getColumnIndex(Column_ID))
        val prodName = cursor.getString(cursor.getColumnIndex(Column_ProdName))
        val prodDescription = cursor.getString(cursor.getColumnIndex(Column_ProdDescription))
        val prodPrice = cursor.getFloat(cursor.getColumnIndex(Column_ProdPrice))
        val prodImage = cursor.getString(cursor.getColumnIndex(Column_ProdImage))
        val prodAvailable = cursor.getInt(cursor.getColumnIndex(Column_ProdAvailable)) != 0

        return Product(prodId, prodName, prodDescription, prodPrice, prodImage, prodAvailable)
    }
}