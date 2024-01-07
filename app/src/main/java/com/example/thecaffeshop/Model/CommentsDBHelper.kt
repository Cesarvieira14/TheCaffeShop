package com.example.thecaffeshop.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val ver: Int = 1

// SQL:
//

class CommentsDBHelper(context: Context) : SQLiteOpenHelper(context, Constants.DB_NAME, null, ver) {
    companion object {
        /* Products Table */
        val TableName = "Comments"
        val Column_ComId = "ComId"
        val Column_ComText = "ComText"
        val Column_ComDate = "ComDate"
        val Column_CusId = "CusId"
        val Column_ProdId = "ProdId"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateStatement: String = "CREATE TABLE " + TableName + " ( " + Column_ComId +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_ComText + " TEXT, " +
                Column_ComDate + " TEXT, " +
                Column_CusId + " INTEGER, " +
                Column_ProdId + " INTEGER )"

        db?.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    private val customerDBHelper: CustomerDBHelper = CustomerDBHelper(context)

    fun getAllCommentsForProduct(prodId: Int): ArrayList<Comment> {
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $TableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, arrayOf())

        val comments: ArrayList<Comment> = ArrayList();
        try {
            while (cursor.moveToNext()) {
                comments.add(parseComment(cursor))
            }
        } finally {
            cursor.close()
            db.close()
        }
        return comments
    }

    fun addComment(comment: Comment): Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_ComText, comment.comText)
        cv.put(Column_ComDate, comment.comDate)
        cv.put(Column_CusId, comment.customer.id)
        cv.put(Column_ProdId, comment.prodId)

        val success = db.insert(TableName, null, cv) != -1L
        db.close()
        return success
    }

    fun deleteComment(comId: Int): Boolean {
        // delete product if it exists in the database
        // writableDatabase for delete actions
        val db: SQLiteDatabase = this.writableDatabase

        val result = db.delete(TableName, "$Column_ComId = $comId", null) == 1

        db.close()
        return result
    }

    @SuppressLint("Range")
    fun parseComment(cursor: Cursor): Comment {
        val comId = cursor.getInt(cursor.getColumnIndex(Column_ComId))
        val comText = cursor.getString(cursor.getColumnIndex(Column_ComText))
        val comDate = cursor.getString(cursor.getColumnIndex(Column_ComDate))
        val cusId = cursor.getInt(cursor.getColumnIndex(Column_CusId))
        val prodId = cursor.getInt(cursor.getColumnIndex(Column_ProdId))

        return Comment(
            comId,
            comText,
            comDate,
            customerDBHelper.getUserByUserId(cusId)!!,
            prodId
        )
    }
}