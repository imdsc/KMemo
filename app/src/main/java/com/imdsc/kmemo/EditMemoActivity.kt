package com.imdsc.kmemo

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.imdsc.kmemo.dao.MemoDAO
import com.imdsc.kmemo.pojo.Memo
import java.text.SimpleDateFormat
import java.util.*

class EditMemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_memo)

        val aceptIntent = intent

        val strTitle = aceptIntent.getStringExtra("E_MEMO_TITLE")
        val strContent = aceptIntent.getStringExtra("E_MEMO_CONTENT")
        val strTime = aceptIntent.getStringExtra("E_MEMO_TIME")
        val intId = aceptIntent.getIntExtra("E_MEMO_ID",0)


        findViewById<EditText>(R.id.updateTextTitle).text = Editable.Factory.getInstance().newEditable(strTitle)
        findViewById<EditText>(R.id.updateContentET).text = Editable.Factory.getInstance().newEditable(strContent)
        findViewById<TextView>(R.id.timeTV).text = Editable.Factory.getInstance().newEditable("上次修改时间：$strTime")
    }


    override fun onBackPressed() {
        //自动执行更新
        updateMemo(Memo(intent.getIntExtra("E_MEMO_ID",0),findViewById<EditText>(R.id.updateTextTitle).text.toString(),findViewById<EditText>(R.id.updateContentET).text.toString(),
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())))
        super.onBackPressed()
    }

    fun onUpdateButtonClick(view:View){
        onBackPressed()
    }

    fun updateMemo(memo:Memo){
        val memoDAO = MemoDAO(this)

        memoDAO.updateMemo(memo)

        memoDAO.closeDB()
    }
}
