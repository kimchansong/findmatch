package com.example.findmatch.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.findmatch.DTO.BoardDto
import com.example.findmatch.R

class BoardAdapter (val context : Context, val boardList: List<BoardDto>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("TeamListAdapter 확인", boardList.toString())
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_board_adapter, null)

        val number = view.findViewById<TextView>(R.id.number)
        //val userId = view.findViewById<TextView>(R.id.userId)
        val title = view.findViewById<TextView>(R.id.title)
       // val content = view.findViewById<TextView>(R.id.content)
        val type = view.findViewById<TextView>(R.id.type)
        val date = view.findViewById<TextView>(R.id.date)

        val boardItem = boardList[position]
        number.text = boardItem.b_num.toString()
        //userId.text = boardItem.user_u_id
        title.text = boardItem.b_title
        //content.text = boardItem.b_content
        if( boardItem.b_type == 1){
            type.text = "매칭게시판"
        }else if(boardItem.b_type == 2 ){
            type.text = "용병게시판"
        } else {
            type.text = "자유게시판"
        }
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