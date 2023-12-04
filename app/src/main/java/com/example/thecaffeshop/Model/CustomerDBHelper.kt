package com.example.thecaffeshop.Model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/* Database Config*/
private val DataBaseName = "TheCaffeShopDb.db"
private val ver: Int = 1


class CustomerDBHelper(context: Context) : SQLiteOpenHelper(context, DataBaseName, null, ver) {

    /* Customer Table */
    public val TableName = "Customers"
    public val Column_ID = "Id"
    public val Column_CusFullName = "CusFullName"
    public val Column_CusEmail = "CusEmail"
    public val Column_CusPhoneNo = "CusPhoneNo"
    public val Column_CusUserName = "CusUserName"
    public val Column_CusPassword = "CusPassword"
    public val Column_CusIsActive = "CusIsActive"

    /*************************/


    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_CusFullName + " TEXT, " +
                Column_CusEmail + " TEXT, " +
                Column_CusPhoneNo + " TEXT, " +
                Column_CusUserName + " TEXT, " +
                Column_CusPassword + " TEXT, " +
                Column_CusIsActive + " BOOLEAN )"

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun registerCustomer(customer: User): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_CusFullName, customer.fullName)
        cv.put(Column_CusEmail, customer.email)
        cv.put(Column_CusPhoneNo, customer.phoneNo)
        cv.put(Column_CusUserName, customer.cusUserName)
        cv.put(Column_CusPassword, customer.cusPassword)
        cv.put(Column_CusIsActive, customer.isActive)

        val success = db.insert(TableName, null, cv)
        db.close()
        return success != -1L
    }

    @SuppressLint("Range")
    fun getUserByUsername(username: String): User? {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement: String = "SELECT * FROM $TableName WHERE $Column_CusUserName = ?"
        val selectionArgs: Array<String> = arrayOf(username)

        val cursor: Cursor = db.rawQuery(sqlStatement, selectionArgs)

        val hasUser = cursor.moveToFirst()

        val user: User
        if (hasUser) {
            val id = cursor.getInt(cursor.getColumnIndex(Column_ID))
            val fullName = cursor.getString(cursor.getColumnIndex(Column_CusFullName))
            val email = cursor.getString(cursor.getColumnIndex(Column_CusEmail))
            val phoneNo = cursor.getString(cursor.getColumnIndex(Column_CusPhoneNo))
            val cusUserName = cursor.getString(cursor.getColumnIndex(Column_CusUserName))
            val cusPassword = cursor.getString(cursor.getColumnIndex(Column_CusPassword))
            val isActive = cursor.getInt(cursor.getColumnIndex(Column_CusIsActive)) != 0

            user = User(id, fullName, email, phoneNo, cusUserName, cusPassword, isActive, false)

            return user
        } else {
            return null
        }

        // Close the cursor and database connection
        cursor.close()
        db.close()
    }

}


