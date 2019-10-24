package com.example.findmatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_team_manage.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BoardActivity : AppCompatActivity() {

    val boardList = mutableListOf<BoardDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setRetrofit(this)
    }



    private fun setRetrofit(boardActivity: BoardActivity){

        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(BoardService::class.java)

        val call: Call<Array<BoardDto>> =  service.requestBoard()

        call.enqueue(object : Callback<Array<BoardDto>> {
            override fun onFailure(call: Call<Array<BoardDto>>, t: Throwable) {
                Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Array<BoardDto>>, response: Response<Array<BoardDto>>) {
                if(response.body()!=null){
                    val num = response.body()!!.size - 1

                    for (i in 0.. num){
                        boardList.add(response.body()!!.get(i))
                    }

                    val boardAdapter = BoardAdapter(boardActivity, boardList)
                    listTeamMember.adapter = boardAdapter
                }
            }
        })


    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

}
