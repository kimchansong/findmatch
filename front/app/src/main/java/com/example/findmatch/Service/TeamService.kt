package com.example.findmatch.Service

import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.DTO.TeamJoinDto
import com.example.findmatch.DTO.TeamMemberDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamService{
    // 팀 정보 불러오기
    @GET("team/{teamName}")
    fun requestTeam(@Path("teamName") teamName:String):Call<TeamDto>

    // 내 팀 불러오기
    @GET("myTeam/{userId}")
    fun requestMyTeam(@Path("userId") userId:String):Call<Array<TeamMemberDto>>

    // 팀원 불러오기
    @GET("teamMember/{teamName}")
    fun requestTeamMember(@Path("teamName") teamName:String):Call<Array<TeamMemberDto>>

    // 팀 가입 요청 리스트 불러오기
    @GET("teamJoinRequest/{teamName}")
    fun requestTeamJoin(@Path("teamName") teamName:String):Call<Array<TeamJoinDto>>

    // 팀 삭제
    @POST("team/delete")
    fun requestTeamDelete(@Body teamName:TeamDto):Call<Int>

    // 팀원 삭제
    @POST("teamMember/delete/{teamName}")
    fun requestTeamMemberDelete(@Path("teamName") teamName: String):Call<Int>

    // 팀 중복 체크
    @GET("duplicationCheck/{teamName}")
    fun duplicationTeamName(@Path("teamName") teamName:String):Call<Int>

    // 팀 생성
    @POST("team/add")
    fun addTeam(@Body team: TeamDto):Call<Int>

    // 팀원 추가
    @POST("teamMember/add/{teamName}")
    fun addTeamMember(@Body teamMember:Array<Any>, @Path("teamName") teamName:String):Call<Int>
}