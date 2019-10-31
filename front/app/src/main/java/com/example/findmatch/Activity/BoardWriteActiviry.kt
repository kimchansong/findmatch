package com.example.findmatch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.findmatch.DTO.BoardDto
import com.example.findmatch.Service.BoardService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_board_write_activiry.*
import kotlinx.android.synthetic.main.activity_footer.view.*
import kotlinx.android.synthetic.main.activity_make_team.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.text.SimpleDateFormat


class BoardWriteActiviry : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.findmatch.R.layout.activity_board_write_activiry)
        val spn = findViewById(com.example.findmatch.R.id.writeType) as Spinner
        val sAdapter = ArrayAdapter.createFromResource(this, com.example.findmatch.R.array.boardType, android.R.layout.simple_spinner_dropdown_item)
        writeType.setAdapter(sAdapter)
        var type:Int = 1
        spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val source = spn.getItemAtPosition(position).toString()
                val result = source.split(".")
                type = result[0].toInt()
            }

        }

        writeButton.setOnClickListener{

            auth = FirebaseAuth.getInstance()
            val title = writeTitle.text.toString()
            val content = writeContent.text.toString()
            val email = auth.currentUser!!.email.toString()
            var item : BoardDto = BoardDto(0,email,title,content,type,nowTime())

            writeBoard(item)
        }
        //푸터
        top_board_write.home.setOnClickListener(){
            startActivity<MainActivity>()
        }
        top_board_write.person.setOnClickListener(){
            startActivity<MypageActivity>()
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
                Toast.makeText(applicationContext,"글쓰기 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BoardDto>, response: Response<BoardDto>) {
                if(response.body()!=null){
                    Toast.makeText(applicationContext,"글쓰기 성공", Toast.LENGTH_SHORT).show()
                    startActivity<BoardActivity>()
                }
            }
        })
    }


    private fun nowTime(): String {
        val dt = Date()
        val full_sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return full_sdf.format(dt).toString()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

}
