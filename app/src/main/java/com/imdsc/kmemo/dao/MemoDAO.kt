package com.imdsc.kmemo.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.imdsc.kmemo.pojo.Memo
import java.lang.Exception

class MemoDAO(context: Context) {

    private val memoDB = MemoDBHelper(context)

    private val db: SQLiteDatabase

    init {
        db = memoDB.writableDatabase
    }

    fun addMemo(memo: Memo): Boolean {
        var result :Boolean

        val sql = "INSERT INTO tb_memo(id,title,content,time) values(NULL,'${memo.title}','${memo.content}','${memo.time}')"

        try {
            db.execSQL(sql)
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        }
        return result
    }

    fun selectAllMemos():List<Memo>{

        val sql = "SELECT * FROM tb_memo"

        val result = db.rawQuery(sql,null)

        val data = ArrayList<Memo>()

        while (result.moveToNext()){
            data.add(Memo(result.getInt(0),result.getString(1),result.getString(2),result.getString(3)))
        }
        println("一共有${result.count}条便签")


        return data
    }

    fun deleteMemo(id:Int){

        db.delete("tb_memo","id=?", arrayOf(id.toString()))

    }

    fun updateMemo(memo: Memo){
        val cv = ContentValues()
        cv.put("title",memo.title)
        cv.put("content",memo.content)
        cv.put("time",memo.time)
        db.update("tb_memo",cv,"id=?", arrayOf(memo.id.toString()))
    }

    fun closeDB(){
        db.close()
    }
}