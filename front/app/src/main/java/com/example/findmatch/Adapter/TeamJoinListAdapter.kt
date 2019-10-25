package com.example.findmatch.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.findmatch.R
import com.example.findmatch.DTO.TeamJoinDto

class TeamJoinListAdapter (val context : Context, val teamJoinList: List<TeamJoinDto>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("TeamJoinList 확인", teamJoinList.toString())
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_team_joinrequest_item, null)

        val userId = view.findViewById<TextView>(R.id.userId)
        val status = view.findViewById<TextView>(R.id.status)

        val teamJoin = teamJoinList[position]
        userId.text = teamJoin.userId
        status.text = teamJoin.status

        return view
    }

    override fun getItem(position: Int): Any {
        return teamJoinList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return teamJoinList.size
    }
}