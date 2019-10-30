package com.example.findmatch.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.findmatch.Adapter.BoardAdapter
import com.example.findmatch.DTO.BoardDto
import com.example.findmatch.Service.BoardService
import com.example.findmatch.R
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_board_write_activiry.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BoardActivity : AppCompatActivity() {

    val boardList = mutableListOf<BoardDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        // 글쓰기 페이지 이동
        insertButton.setOnClickListener {
            startActivity<BoardWriteActiviry>()
        }

        setRetrofit(this)
    }



    // 클릭했을
    inner class ListListener : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            startActivity<BoardDetailActivity>(
                "title" to boardList.get(position).b_title,
               "content" to  boardList.get(position).b_content,
                "date" to boardList.get(position).b_date,
                "num" to boardList.get(position).b_num,
                "writter" to boardList.get(position).user_u_id
            )
        }
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
                Toast.makeText(applicationContext,"불러오기 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Array<BoardDto>>, response: Response<Array<BoardDto>>) {
                if(response.body()!=null){
                    val num = response.body()!!.size - 1

                    for (i in 0.. num){
                        boardList.add(response.body()!!.get(i))
                    }

                    val boardAdapter =
                        BoardAdapter(boardActivity, boardList)
                    listBoard.adapter = boardAdapter
                    var listener = ListListener()
                    listBoard.setOnItemClickListener(listener)
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
