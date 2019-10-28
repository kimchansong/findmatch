package com.example.findmatch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.findmatch.DTO.BoardDto
import com.example.findmatch.R
import com.example.findmatch.Service.BoardService
import kotlinx.android.synthetic.main.activity_board_write_activiry.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*



class BoardWriteActiviry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.findmatch.R.layout.activity_board_write_activiry)
        val spn = findViewById(R.id.writeType) as Spinner
        val sAdapter = ArrayAdapter.createFromResource(this, com.example.findmatch.R.array.boardType, android.R.layout.simple_spinner_dropdown_item)
        writeType.setAdapter(sAdapter)
        var type:String = ""
        spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 type = spn.getItemAtPosition(position).toString()
            }

        }

        writeButton.setOnClickListener{
            val title = writeTitle.text.toString()
            val content = writeContent.text.toString()

            var item : BoardDto = BoardDto(0,"u_id",title,content,3,nowTime())
            /*
            println("시간 : " + nowTime())
            println("제목 : " + title)
            println("내용 : " + content)
            println("타입 : " + type)
            */
             writeBoard(item)

        }
    }


    private fun writeBoard(item : BoardDto){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(BoardService::class.java)
        val call: Call<BoardDto> =  service.writeBoard(item)

        call.enqueue(object : Callback<BoardDto> {
            override fun onFailure(call: Call<BoardDto>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<BoardDto>, response: Response<BoardDto>) {
                if(response.body()!=null){
                    startActivity<BoardActivity>()
                }
            }
        })
    }


    private fun nowTime(): String {
        val tz = TimeZone.getTimeZone("Asia/Seoul")
        val gc = GregorianCalendar(tz)

        var year = gc.get(GregorianCalendar.YEAR).toString()
        var month = (gc.get(GregorianCalendar.MONTH)+1).toString()
        var day = gc.get(GregorianCalendar.DATE).toString()

        var hour= gc.get(GregorianCalendar.HOUR).toString()
        var min = gc.get(GregorianCalendar.MINUTE).toString()
        var sec = gc.get(GregorianCalendar.SECOND).toString()

        return year + " " + month + " " + day + " " + hour + " " + min + " " + sec
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

}
