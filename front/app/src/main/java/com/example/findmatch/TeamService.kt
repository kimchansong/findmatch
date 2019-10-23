package com.example.findmatch

import retrofit2.Call
import retrofit2.http.GET

interface TeamService{
    // 팀 정보 불러오기
    @GET("team/sk")
    fun requestTeam():Call<TeamDto>

    // 팀원 불러오기
    @GET("teamMember/sk")
    fun requestTeamMember():Call<Array<TeamMemberDto>>

    // 팀 가입 요청 리스트 불러오기
    @GET("teamJoinRequest/sk")
    fun requestTeamJoin():Call<Array<TeamJoinDto>>
}