package com.example.thecaffeshop.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/* Database Config*/
private const val ver: Int = 1

// SQL: CREATE TABLE "Orders" (
//	"OrderId"	INTEGER,
//	"OrderDate"	TEXT,
//	"OrderTime"	TEXT,
//	"OrderStatus"	TEXT,
//	"CusId"	INTEGER,
//	FOREIGN KEY("CusId") REFERENCES "Customers"("Id"),
//	PRIMARY KEY("OrderId" AUTOINCREMENT)
//)

class OrdersDBHelper(context: Context) : SQLiteOpenHelper(context, Constants.DB_NAME, null, ver) {
    /* Customer Table */
    val TableName = "Orders"
    val Column_OrderId = "OrderId"
    val Column_OrderDate = "OrderDate"
    val Column_OrderTime = "OrderTime"
    val Column_OrderStatus = "OrderStatus"
    val Column_CusId = "CusId"

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_OrderId +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_OrderDate + " TEXT, " +
                Column_OrderTime + " TEXT, " +
                Column_OrderStatus + " TEXT, " +
                Column_CusId + " INTEGER, FOREIGN KEY($Column_CusId) REFERENCES Customers(Id)) "

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun createOrder(order: Order): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_OrderDate, order.orderDate)
        cv.put(Column_OrderTime, order.orderTime)
        cv.put(Column_OrderStatus, order.orderStatus.toString())
        cv.put(Column_CusId, order.user.id)

        // Create new row
        val id = db.insert(TableName, null, cv)

        // Fetch created id
        val cursor: Cursor =
            db.rawQuery("SELECT * FROM $TableName WHERE rowid = ?", arrayOf(id.toString()))

        if (cursor != null && cursor.moveToFirst()) {
            db.close()
            return cursor.getInt(cursor.getColumnIndex(Column_OrderId))
        }

        db.close()
        return 0
    }
    fun getOrdersByUserId(userId: Int): ArrayList<Order> {
        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TableName WHERE $Column_CusId = ? ",
            arrayOf(userId.toString())
        )

        val orders: ArrayList<Order> = ArrayList();
        try {
            while (cursor.moveToNext()) {
                orders.add(parseOrder(cursor))
            }
        } finally {
            cursor.close()
            db.close()
        }
        return orders
    }

    fun getAllOrders(): ArrayList<Order> {
        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM $TableName", null)

        val orders: ArrayList<Order> = ArrayList()
        try {
            while (cursor.moveToNext()) {
                orders.add(parseOrder(cursor))
            }
        } finally {
            cursor.close()
            db.close()
        }
        return orders
    }

    fun updateOrderStatus(orderId: Int, orderStatus: OrderStatus): Boolean {
        val db: SQLiteDatabase = this.readableDatabase
        val cv = ContentValues()

        cv.put(Column_OrderStatus, orderStatus.toString())

        db.use { db ->
            val rowsAffected =
                db.update(TableName, cv, "$Column_OrderId = ?", arrayOf(orderId.toString()))

            return rowsAffected > 0
        }
        return false
    }

    @SuppressLint("Range")
    fun parseOrder(cursor: Cursor): Order {
        val orderId = cursor.getInt(cursor.getColumnIndex(Column_OrderId))
        val orderDate = cursor.getString(cursor.getColumnIndex(Column_OrderDate))
        val orderTime = cursor.getString(cursor.getColumnIndex(Column_OrderTime))
        val orderStatus = cursor.getString(cursor.getColumnIndex(Column_OrderStatus))
        val enumOrderStatus = OrderStatus.values().find { it.name == orderStatus } as OrderStatus

        return Order(orderId, orderDate, orderTime, enumOrderStatus)
    }
}