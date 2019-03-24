package com.imdsc.kmemo.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MemoDBHelper(context: Context) : SQLiteOpenHelper(context,"KMemo",null,2){

    override fun onCreate(db: SQLiteDatabase?) {
        val createSqlStr = "CREATE TABLE IF NOT EXISTS tb_memo (id INTEGER PRIMARY KEY AUTOINCREMENT,title varchar(245),content text,time varchar(155))"
        db?.execSQL(createSqlStr)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}