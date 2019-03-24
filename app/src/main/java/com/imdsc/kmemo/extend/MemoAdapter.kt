package com.imdsc.kmemo.extend

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.imdsc.kmemo.MainActivity
import com.imdsc.kmemo.R
import com.imdsc.kmemo.pojo.Memo

class MemoAdapter(val items : List<Memo>) : RecyclerView.Adapter<MemoAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        //修改布局
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_1,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        //获取数据
        val memo = items[position]

        //填充标题
        val v = holder.view as TextView
        v.text = memo.title

        //设置点击事件
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                MainActivity.enterEditWindow(memo)
            }
        })

        //设置长按事件，删除事件
        holder.itemView.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                MainActivity.ConfirmDeleteMemo(memo)
                return true
            }
        })
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view)

}