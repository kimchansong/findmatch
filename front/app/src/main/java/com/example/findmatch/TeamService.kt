package com.example.findmatch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamService{
    @GET("team/sk")
    fun requestTeam():Call<TeamDto>
    @GET("duplicationCheck/{teamName}")
    fun duplicationTeamName(@Path("teamName") teamName:String):Call<TeamDto>
}