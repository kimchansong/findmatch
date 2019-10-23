package com.example.findmatch

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TeamListAdapter (val context : Context, val teamMemberList: List<TeamMemberDto>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("TeamListAdapter 확인", teamMemberList.toString())
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_team_member_item, null)

        val teamName = view.findViewById<TextView>(R.id.teamName)
        val userId = view.findViewById<TextView>(R.id.userId)
        val auth = view.findViewById<TextView>(R.id.auth)

        val teamMember = teamMemberList[position]
        teamName.text = teamMember.teamName
        userId.text = teamMember.userId
        auth.text = teamMember.auth

        return view
    }

    override fun getItem(position: Int): Any {
        return teamMemberList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return teamMemberList.size
    }
}