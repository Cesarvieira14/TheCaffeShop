package com.example.thecaffeshop.Model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val DataBaseName = "TheCaffeShopDb.db"
private val ver: Int = 1

class AdminDBHelper(context: Context) : SQLiteOpenHelper(context, DataBaseName, null, ver) {

    /* Customer Table */
    val TableName = "Admins"
    val Column_AdminId = "AdminId"
    val Column_AdminFullName = "AdminFullName"
    val Column_AdminEmail = "AdminEmail"
    val Column_AdminPhoneNo = "AdminPhoneNo"
    val Column_AdminUserName = "AdminUserName"
    val Column_AdminPassword = "AdminPassword"
    val Column_AdminIsActive = "AdminIsActive"

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_AdminId +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_AdminFullName + " TEXT, " +
                Column_AdminEmail + " TEXT, " +
                Column_AdminPhoneNo + " TEXT, " +
                Column_AdminUserName + " TEXT, " +
                Column_AdminPassword + " TEXT, " +
                Column_AdminIsActive + " BOOLEAN )"

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun registerAdmin(admin: User): Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_AdminFullName, admin.fullName)
        cv.put(Column_AdminEmail, admin.email)
        cv.put(Column_AdminPhoneNo, admin.phoneNo)
        cv.put(Column_AdminUserName, admin.cusUserName)
        cv.put(Column_AdminPassword, admin.cusPassword)
        cv.put(Column_AdminIsActive, admin.isActive)

        val success = db.insert(TableName, null, cv)
        db.close()
        return success != -1L
    }

    @SuppressLint("Range")
    fun getUserByUsername(username: String): User? {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName WHERE $Column_AdminUserName = ?"
        val selectionArgs: Array<String> = arrayOf(username)

        val cursor: Cursor = db.rawQuery(sqlStatement, selectionArgs)

        val hasUser = cursor.moveToFirst()

        val user: User
        if (hasUser) {
            val id = cursor.getInt(cursor.getColumnIndex(Column_AdminId))
            val fullName = cursor.getString(cursor.getColumnIndex(Column_AdminFullName))
            val email = cursor.getString(cursor.getColumnIndex(Column_AdminEmail))
            val phoneNo = cursor.getString(cursor.getColumnIndex(Column_AdminPhoneNo))
            val cusUserName = cursor.getString(cursor.getColumnIndex(Column_AdminUserName))
            val cusPassword = cursor.getString(cursor.getColumnIndex(Column_AdminPassword))
            val isActive = cursor.getInt(cursor.getColumnIndex(Column_AdminIsActive)) != 0

            user = User(id, fullName, email, phoneNo, cusUserName, cusPassword, isActive, true)

            return user
        } else {
            return null
        }

        // Close the cursor and database connection
        cursor.close()
        db.close()
    }

}