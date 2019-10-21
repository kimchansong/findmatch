package com.example.findmatch

import retrofit2.Call
import retrofit2.http.GET

interface TeamService{
    @GET("team/sk")
    fun requestTeam():Call<TeamDto>
}