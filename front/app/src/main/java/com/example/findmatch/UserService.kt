package com.example.findmatch

import retrofit2.Call
import retrofit2.http.GET

interface UserService{
    // 유저 가입 확인
    @GET("user/{userId}")
    fun requestUserOk(): Call<UserDto>


}