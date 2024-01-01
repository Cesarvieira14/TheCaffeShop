package com.example.thecaffeshop.model

import Constants.Companion.DB_NAME
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val ver: Int = 1

// SQL: CREATE TABLE Admins ( AdminId INTEGER PRIMARY KEY AUTOINCREMENT, AdminFullName TEXT, AdminEmail TEXT, AdminPhoneNo TEXT, AdminUserName TEXT, AdminPassword TEXT, AdminIsActive BOOLEAN )
class AdminDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, ver) {

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
    fun updateAdmin(updatedUser: User): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val cv = ContentValues()

        cv.put(Column_AdminFullName, updatedUser.fullName)
        cv.put(Column_AdminEmail, updatedUser.email)
        cv.put(Column_AdminPhoneNo, updatedUser.phoneNo)
        cv.put(Column_AdminUserName, updatedUser.userName)
        cv.put(Column_AdminPassword, updatedUser.password)
        cv.put(Column_AdminIsActive, updatedUser.isActive)

        val result = db.update(TableName, cv, "$Column_AdminId = ?", arrayOf(updatedUser.id.toString()))
        db.close()

        return result != -1
    }
    fun registerAdmin(admin: User): Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_AdminFullName, admin.fullName)
        cv.put(Column_AdminEmail, admin.email)
        cv.put(Column_AdminPhoneNo, admin.phoneNo)
        cv.put(Column_AdminUserName, admin.userName)
        cv.put(Column_AdminPassword, admin.password)
        cv.put(Column_AdminIsActive, admin.isActive)

        val success = db.insert(TableName, null, cv)
        db.close()
        return success != -1L
    }

    fun getAllAdmins(): List<User> {
        val db : SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        val admins = mutableListOf<User>()

        while (cursor.moveToNext()) {
            admins.add(parseUser(cursor))
        }

        cursor.close()
        db.close()

        return admins
    }
    fun getAdminByAdminId(adminId: Int): User? {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName WHERE $Column_AdminId = ?"
        val selectionArgs: Array<String> = arrayOf(adminId.toString())

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

    fun getAdminByUsername(username: String): User? {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName WHERE $Column_AdminUserName = ?"
        val selectionArgs: Array<String> = arrayOf(username)

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
        val id = cursor.getInt(cursor.getColumnIndex(Column_AdminId))
        val fullName = cursor.getString(cursor.getColumnIndex(Column_AdminFullName))
        val email = cursor.getString(cursor.getColumnIndex(Column_AdminEmail))
        val phoneNo = cursor.getString(cursor.getColumnIndex(Column_AdminPhoneNo))
        val userName = cursor.getString(cursor.getColumnIndex(Column_AdminUserName))
        val password = cursor.getString(cursor.getColumnIndex(Column_AdminPassword))
        val isActive = cursor.getInt(cursor.getColumnIndex(Column_AdminIsActive)) != 0

        return User(id, fullName, email, phoneNo, userName, password, isActive, true)
    }

    fun addAdmin(updatedUser: User) {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_AdminFullName, updatedUser.fullName)
        cv.put(Column_AdminEmail, updatedUser.email)
        cv.put(Column_AdminPhoneNo, updatedUser.phoneNo)
        cv.put(Column_AdminUserName, updatedUser.userName)
        cv.put(Column_AdminPassword, updatedUser.password)
        cv.put(Column_AdminIsActive, updatedUser.isActive)

        db.insert(TableName, null, cv)
        db.close()
    }
    fun removeAdmin(id: Int): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(TableName, "$Column_AdminId = $id", null) == 1
        db.close()
        return result
    }
}