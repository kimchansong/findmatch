package com.example.findmatch.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.R
import com.example.findmatch.DTO.TeamMemberDto

class TeamAdapter (val context : Context, val teamList: List<TeamMemberDto>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_team_item, null)
        val teamName = view.findViewById<TextView>(R.id.teamName)
        val teamMember = teamList[position]

        teamName.text = teamMember.teamName

        return view
    }

    override fun getItem(position: Int): Any {
        return teamList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return teamList.size
    }
}