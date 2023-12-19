package com.example.thecaffeshop.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/* Database Config*/
private const val ver: Int = 1

// SQL: CREATE TABLE Payments (PaymentId INTEGER PRIMARY KEY AUTOINCREMENT, PaymentDate TEXT, PaymentType TEXT, PaymentAmount REAL, OrderId INTEGER, FOREIGN KEY(OrderId) REFERENCES Orders(OrderId))

class PaymentsDBHelper(context: Context) : SQLiteOpenHelper(context, Constants.DB_NAME, null, ver) {
    /* Customer Table */
    val TableName = "Payments"
    val Column_ID = "PaymentId"
    val Column_PaymentDate = "PaymentDate"
    val Column_PaymentType = "PaymentType"
    val Column_PaymentAmount = "PaymentAmount"
    val Column_OrderId = "OrderId"

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_PaymentDate + " TEXT, " +
                Column_PaymentType + " TEXT, " +
                Column_PaymentAmount + " REAL, " +
                Column_OrderId + " INTEGER, FOREIGN KEY(OrderId) REFERENCES Orders(OrderId)) "

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun createPayment(order: Order): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_PaymentDate, order.paymentDate)
        cv.put(Column_PaymentType, order.paymentType)
        cv.put(Column_PaymentAmount, order.paymentAmount)
        cv.put(Column_OrderId, order.orderId)

        val success = db.insert(TableName, null, cv)
        db.close()
        return success != -1L
    }
}