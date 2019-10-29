package com.example.findmatch.Service

import com.example.findmatch.DTO.BoardDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BoardService{
    // 전체 글 가져오
    @GET("board/all")
    fun requestBoard():Call <Array <BoardDto>>

    // 글 쓰기
    @POST("board/write")
    fun writeBoard(@Body item: BoardDto):Call<BoardDto>

    // 글 삭제
    @POST("board/delete/{num}")
    fun deleteBoard(@Path("num") num: Int):Call<Int>


}