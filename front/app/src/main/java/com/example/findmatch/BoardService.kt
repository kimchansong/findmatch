package com.example.findmatch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BoardService{
    // 전체 글 가져오
    @GET("board/all")
    fun requestBoard():Call <Array <BoardDto>>


}