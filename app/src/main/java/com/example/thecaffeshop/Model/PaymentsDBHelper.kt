package com.example.thecaffeshop.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/* Database Config*/
private const val ver: Int = 1

// SQL: CREATE TABLE Payments (PaymentId INTEGER PRIMARY KEY AUTOINCREMENT, PaymentDate TEXT, PaymentType TEXT, PaymentAmount REAL, OrderId INTEGER, FOREIGN KEY(OrderId) REFERENCES Orders(OrderId))

class PaymentsDBHelper(context: Context) : SQLiteOpenHelper(context, Constants.DB_NAME, null, ver) {
    /* Customer Table */
    val TableName = "Payments"
    val Column_PaymentId = "PaymentId"
    val Column_PaymentDate = "PaymentDate"
    val Column_PaymentType = "PaymentType"
    val Column_PaymentAmount = "PaymentAmount"
    val Column_OrderId = "OrderId"

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_PaymentId +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_PaymentDate + " TEXT, " +
                Column_PaymentType + " TEXT, " +
                Column_PaymentAmount + " REAL, " +
                Column_OrderId + " INTEGER, FOREIGN KEY($Column_OrderId) REFERENCES Orders(OrderId)) "

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun createPayment(order: Order): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_PaymentDate, order.payment.paymentDate)
        cv.put(Column_PaymentType, order.payment.paymentType)
        cv.put(Column_PaymentAmount, order.payment.paymentAmount)
        cv.put(Column_OrderId, order.orderId)

        val success = db.insert(TableName, null, cv)
        db.close()
        return success != -1L
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makePayment(paymentId: Int, type: String): Boolean {
        val db: SQLiteDatabase = this.readableDatabase
        val cv = ContentValues()

        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        cv.put(Column_PaymentDate, LocalDateTime.now().format(dateFormatter))
        cv.put(Column_PaymentType, type)

        db.use { db ->
            val rowsAffected =
                db.update(TableName, cv, "$Column_PaymentId = ?", arrayOf(paymentId.toString()))

            return rowsAffected > 0
        }
        return false
    }

    @SuppressLint("Range")
    fun getPaymentByOrderId(orderId: Int): Payment {
        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TableName WHERE $Column_OrderId = ? ",
            arrayOf(orderId.toString())
        )

        val payment = Payment();
        try {
            while (cursor.moveToNext()) {
                payment.paymentId = cursor.getInt(cursor.getColumnIndex(Column_PaymentId))
                payment.paymentDate = cursor.getString(cursor.getColumnIndex(Column_PaymentDate))
                payment.paymentType = cursor.getString(cursor.getColumnIndex(Column_PaymentType))
                payment.paymentAmount = cursor.getDouble(cursor.getColumnIndex(Column_PaymentAmount))
            }
        } finally {
            cursor.close()
            db.close()
        }
        return payment
    }
}