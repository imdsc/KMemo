package com.imdsc.kmemo

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.imdsc.kmemo.dao.MemoDAO
import com.imdsc.kmemo.pojo.Memo
import kotlinx.android.synthetic.main.activity_create_memo.*
import java.text.SimpleDateFormat
import java.util.*

class CreateMemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memo)
    }

    //弹出对话框，提示是否保存。这里不行
    override fun onDestroy() {
        super.onDestroy()
        println("新增备忘录这个Activity正在被销毁")
    }

    override fun onBackPressed() {
//        super.onBackPressed()//取消这行，则返回键的逻辑全由自己控制
        //如果标题和内容均为空，则按了返回键直接返回到主页面否则弹出提示框
        if(updateTextTitle.text.toString() == "" && updateContentET.text.toString() == ""){
            super.onBackPressed()
        }else {
            println("正要退出新增备忘录这个Activity")

            val confirmExitDialogBuilder = AlertDialog.Builder(this)
            confirmExitDialogBuilder.setTitle("是否保存？").setMessage("点击否则不保存并退出，点击是则保存并退出，点击空白则继续编辑。")
            confirmExitDialogBuilder.setPositiveButton(R.string.str_yes, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    insertMemo()

                    //调用super的返回
                    nativeBack()
                    println("退出并保存备忘录")
                }
            })
            confirmExitDialogBuilder.setNegativeButton(R.string.str_no, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    nativeBack()
                }
            })
            val confirmDialog = confirmExitDialogBuilder.create()
            confirmDialog.show()
        }
    }

    fun btnSaveOnClick(view: View){

        insertMemo()

        //不要用以下这种方式去返回
        //val intent = Intent(this,MainActivity::class.java)
        //startActivity(intent)

        //要用这种
        super.onBackPressed()

    }

    fun insertMemo(){
        println("保存了一个便签")

//        //保存文件
//        val fileName = editTextTitle.text
//        val fileContent = editTextContent.text
//
//        println(fileName.toString()+fileContent.toString())
//
//        val fos = openFileOutput(fileName.toString(), Context.MODE_PRIVATE)
//        fos.write(fileContent.toString().toByteArray(Charsets.UTF_8))
//        fos.close()

        val title = updateTextTitle.text
        val content = updateContentET.text

        //这里需要进行验空
        val memo = Memo(0,title.toString(),content.toString(),SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))

        val memoDAO = MemoDAO(this)
        memoDAO.addMemo(memo)
        memoDAO.closeDB()
        println("添加成功")
    }

    fun nativeBack(){
        super.onBackPressed()
    }
}
