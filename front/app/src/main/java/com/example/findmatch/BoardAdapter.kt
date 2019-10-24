package com.example.findmatch

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class BoardAdapter (val context : Context, val boardList: List<BoardDto>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("TeamListAdapter 확인", boardList.toString())
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_board_adapter, null)

        val number = view.findViewById<TextView>(R.id.number)
        val userId = view.findViewById<TextView>(R.id.userId)
        val title = view.findViewById<TextView>(R.id.title)
        val content = view.findViewById<TextView>(R.id.content)
        val type = view.findViewById<TextView>(R.id.type)
        val date = view.findViewById<TextView>(R.id.date)

        val boardItem = boardList[position]
        number.text = boardItem.b_num.toString()
        userId.text = boardItem.User_u_id
        title.text = boardItem.b_title
        content.text = boardItem.b_content
        type.text = boardItem.b_type.toString()
        date.text = boardItem.b_date

        return view
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return boardList.size
    }
}