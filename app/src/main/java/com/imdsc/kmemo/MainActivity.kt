package com.imdsc.kmemo

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.imdsc.kmemo.dao.MemoDAO
import com.imdsc.kmemo.extend.MemoAdapter
import com.imdsc.kmemo.pojo.Memo

class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var me:Context
        private lateinit var rv:RecyclerView
        fun ConfirmDeleteMemo(memo: Memo){
            val confirmExitDialogBuilder = AlertDialog.Builder(me)
            confirmExitDialogBuilder.setTitle("确认删除此条备忘录？").setMessage("点击取消则不删除，点击确认则删除此条便签。")
            confirmExitDialogBuilder.setPositiveButton(R.string.str_confirm, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val memoDAO = MemoDAO(me)
                    memoDAO.deleteMemo(memo.id)

                    val data:List<Memo> = memoDAO.selectAllMemos()
                    memoDAO.closeDB()

                    //设置RV布局
                    rv.layoutManager = LinearLayoutManager(me)

                    //设置adapter
                    rv.adapter = MemoAdapter(data)
                }
            })
            confirmExitDialogBuilder.setNegativeButton(R.string.str_cancel, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    println("取消删除${memo.title}备忘录")
                }
            })
            val confirmDialog = confirmExitDialogBuilder.create()
            confirmDialog.show()
        }

        fun enterEditWindow(memo: Memo){
            val intent = Intent(me,EditMemoActivity::class.java)
            intent.putExtra("E_MEMO_TITLE",memo.title)
            intent.putExtra("E_MEMO_CONTENT",memo.content)
            intent.putExtra("E_MEMO_TIME",memo.time)
            intent.putExtra("E_MEMO_ID", memo.id)
            me.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        me = this
        rv = findViewById(R.id.EditMemoRV)
    }

    override fun onStart() {
        super.onStart()

        //此处要重新渲染RV
        fillEditMemoRecyclerView()

    }

    //启用新增便签Activity
    fun onCreateMemoButtonClick(view: View){
        println("我要新增便签")
        val intent = Intent(this,CreateMemoActivity::class.java)
        startActivity(intent)
    }

    fun fillEditMemoRecyclerView(){

        val memoDAO = MemoDAO(this)

        val data:List<Memo> = memoDAO.selectAllMemos()

        memoDAO.closeDB()

        val editMemoRV:RecyclerView = findViewById(R.id.EditMemoRV)

        //设置RV布局
        editMemoRV.layoutManager = LinearLayoutManager(this)

        //设置adapter
        editMemoRV.adapter = MemoAdapter(data)

    }
}
