package com.example.findmatch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService{
    // 유저 가입 확인
    @GET("user/{userId}")
    fun requestUserOk(@Path("userId") userId:String): Call<UserDto>


}