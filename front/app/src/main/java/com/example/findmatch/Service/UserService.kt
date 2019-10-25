package com.example.findmatch.Service

import com.example.findmatch.DTO.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService{
    // 유저 가입 확인
    @GET("user/{userId}")
    fun requestUserOk(@Path("userId") userId:String): Call<UserDto>

    // 유저 생성
    @POST("user/insert/{userInfo}")
    fun insertUser(@Path("userInfo") userInfo:UserDto): Call<UserDto>
}