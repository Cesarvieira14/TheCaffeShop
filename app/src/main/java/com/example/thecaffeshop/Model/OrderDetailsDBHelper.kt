package com.example.thecaffeshop.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.thecaffeshop.model.ProductDBHelper.Companion.parseProduct

/* Database Config*/
private const val ver: Int = 1

// SQL: CREATE TABLE OrderDetails (OrderDetailsId INTEGER PRIMARY KEY AUTOINCREMENT, OrderId INTEGER, ProdId INTEGER, FOREIGN KEY(OrderId) REFERENCES Orders(OrderId), FOREIGN KEY(ProdId) REFERENCES Products(ProdId))

class OrderDetailsDBHelper(context: Context) : SQLiteOpenHelper(context, Constants.DB_NAME, null, ver) {
    /* Customer Table */
    val TableName = "OrderDetails"
    val Column_ID = "OrderDetailsId"
    val Column_OrderId = "OrderId"
    val Column_ProdId = "ProdId"

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_OrderId + " INTEGER, FOREIGN KEY($Column_OrderId) REFERENCES Orders(OrderId), " +
                Column_ProdId + " INTEGER, FOREIGN KEY($Column_ProdId) REFERENCES Products(ProdId)) "

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun createOrderDetails(orderId: Int, prodId: Int): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_OrderId, orderId)
        cv.put(Column_ProdId, prodId)

        val success = db.insert(TableName, null, cv)
        db.close()
        return success != -1L
    }

    fun getProductsByOrderId(orderId: Int): ArrayList<Product> {
        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery(
            "SELECT * " +
                "FROM OrderDetails " +
                "LEFT JOIN Products " +
                "ON OrderDetails.ProdId = Products.ProdId " +
                "WHERE OrderDetails.OrderId = ?",
            arrayOf(orderId.toString())
        )

        val productsForOrder: ArrayList<Product> = ArrayList();
        try {
            while (cursor.moveToNext()) {
                productsForOrder.add(parseProduct(cursor))
            }
        } finally {
            cursor.close()
            db.close()
        }
        return productsForOrder
    }
}