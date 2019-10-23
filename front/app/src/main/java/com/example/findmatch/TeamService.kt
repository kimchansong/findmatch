package com.example.findmatch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamService{
    @GET("team/sk")
    fun requestTeam():Call<TeamDto>

    // 팀원 불러오기
    @GET("teamMember/sk")
    fun requestTeamMember():Call<Array<TeamMemberDto>>

    // 팀 가입 요청 리스트 불러오기
    @GET("teamJoinRequest/sk")
    fun requestTeamJoin():Call<Array<TeamJoinDto>>

    @GET("duplicationCheck/{teamName}")
    fun duplicationTeamName(@Path("teamName") teamName:String):Call<TeamDto>
}