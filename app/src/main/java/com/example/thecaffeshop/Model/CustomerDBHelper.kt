package com.example.thecaffeshop.model

import Constants.Companion.DB_NAME
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/* Database Config*/
private const val ver: Int = 1

// CREATE TABLE Customers ( Id INTEGER PRIMARY KEY AUTOINCREMENT, CusFullName TEXT, CusEmail TEXT, CusPhoneNo TEXT, CusUserName TEXT, CusPassword TEXT, CusIsActive BOOLEAN )
class CustomerDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, ver) {

    /* Customer Table */
    val TableName = "Customers"
    val Column_ID = "Id"
    val Column_CusFullName = "CusFullName"
    val Column_CusEmail = "CusEmail"
    val Column_CusPhoneNo = "CusPhoneNo"
    val Column_CusUserName = "CusUserName"
    val Column_CusPassword = "CusPassword"
    val Column_CusIsActive = "CusIsActive"

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
    fun updateCustomer(updatedUser: User): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val cv = ContentValues()

        cv.put(Column_CusFullName, updatedUser.fullName)
        cv.put(Column_CusEmail, updatedUser.email)
        cv.put(Column_CusPhoneNo, updatedUser.phoneNo)
        cv.put(Column_CusUserName, updatedUser.userName)
        cv.put(Column_CusPassword, updatedUser.password)
        cv.put(Column_CusIsActive, updatedUser.isActive)

        val result = db.update(TableName, cv, "$Column_ID = ?", arrayOf(updatedUser.id.toString()))
        db.close()

        return result != -1
    }

    fun registerCustomer(customer: User): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_CusFullName , customer.fullName)
        cv.put(Column_CusEmail    , customer.email)
        cv.put(Column_CusPhoneNo  , customer.phoneNo)
        cv.put(Column_CusUserName , customer.userName)
        cv.put(Column_CusPassword , customer.password)
        cv.put(Column_CusIsActive , customer.isActive)

        val success = db.insert(TableName, null, cv)
        db.close()
        return success != -1L
    }

    fun getUserByUsername(username: String): User? {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName WHERE $Column_CusUserName = ?"
        val selectionArgs: Array<String> = arrayOf(username)

        val cursor: Cursor = db.rawQuery(sqlStatement, selectionArgs)

        val hasUser = cursor.moveToFirst()
        if (hasUser) {
            return parseUser(cursor)
        } else {
            return null
        }

        // Close the cursor and database connection
        cursor.close()
        db.close()
    }

    fun getAllUsers(): List<User> {
        val db : SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        val users = mutableListOf<User>()

        while (cursor.moveToNext()) {
            users.add(parseUser(cursor))
        }

        cursor.close()
        db.close()

        return users
    }
    fun getUserByUserId(userId: Int): User? {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName WHERE $Column_ID = ?"
        val selectionArgs: Array<String> = arrayOf(userId.toString())
        val cursor: Cursor = db.rawQuery(sqlStatement, selectionArgs)

        val hasUser = cursor.moveToFirst()
        if (hasUser) {
            return parseUser(cursor)
        } else {
            return null
        }

        cursor.close()
        db.close()
    }

    @SuppressLint("Range")
    fun parseUser(cursor: Cursor): User {
        val id = cursor.getInt(cursor.getColumnIndex(Column_ID))
        val fullName = cursor.getString(cursor.getColumnIndex(Column_CusFullName))
        val email = cursor.getString(cursor.getColumnIndex(Column_CusEmail))
        val phoneNo = cursor.getString(cursor.getColumnIndex(Column_CusPhoneNo))
        val userName = cursor.getString(cursor.getColumnIndex(Column_CusUserName))
        val password = cursor.getString(cursor.getColumnIndex(Column_CusPassword))
        val isActive = cursor.getInt(cursor.getColumnIndex(Column_CusIsActive)) != 0

        return User(id, fullName, email, phoneNo, userName, password, isActive, false)
    }


    fun addCustomer(updatedUser: User) {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_CusFullName, updatedUser.fullName)
        cv.put(Column_CusEmail   , updatedUser.email)
        cv.put(Column_CusPhoneNo , updatedUser.phoneNo)
        cv.put(Column_CusUserName, updatedUser.userName)
        cv.put(Column_CusPassword, updatedUser.password)
        cv.put(Column_CusIsActive, updatedUser.isActive)

        db.insert(TableName, null, cv)
        db.close()
    }
    fun removeCustomer(id: Int): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(TableName, "$Column_ID = $id", null) == 1
        db.close()
        return result
    }
}


