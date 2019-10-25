package com.example.findmatch.Service

import com.example.findmatch.DTO.BoardDto
import retrofit2.Call
import retrofit2.http.GET

interface BoardService{
    // 전체 글 가져오
    @GET("board/all")
    fun requestBoard():Call <Array <BoardDto>>


}