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
    val Column_ID = "OrderId"
    val Column_OrderDate = "OrderDate"
    val Column_OrderTime = "OrderTime"
    val Column_OrderStatus = "OrderStatus"
    val Column_CusId = "CusId"

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_OrderDate + " TEXT, " +
                Column_OrderTime + " TEXT, " +
                Column_OrderStatus + " TEXT, " +
                Column_CusId + " INTEGER, FOREIGN KEY(CusId) REFERENCES Customers(Id)) "

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun createOrder(order: Order): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_OrderDate, order.orderDate)
        cv.put(Column_OrderTime, order.orderTime)
        cv.put(Column_OrderStatus, order.orderStatus)
        cv.put(Column_CusId, order.user.id)

        // Create new row
        val id = db.insert(TableName, null, cv)

        // Fetch created id
        val sqlStatement = "SELECT * FROM $TableName WHERE rowid = ?"
        val selectionArgs: Array<String> = arrayOf(id.toString())
        val cursor: Cursor = db.rawQuery(sqlStatement, selectionArgs)

        if (cursor != null && cursor.moveToFirst()) {
            db.close()
            return cursor.getInt(cursor.getColumnIndex(Column_ID))
        }

        db.close()
        return 0
    }
}