package com.example.findmatch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.findmatch.DTO.BoardDto
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.R
import com.example.findmatch.Service.BoardService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_board_detail.*
import kotlinx.android.synthetic.main.activity_board_write_activiry.writeContent
import kotlinx.android.synthetic.main.activity_board_write_activiry.writeTitle
import kotlinx.android.synthetic.main.activity_board_write_activiry.writeType
import kotlinx.android.synthetic.main.activity_team_manage.*
import kotlinx.android.synthetic.main.activity_team_member_item.*
import kotlinx.android.synthetic.main.activity_team_member_item.teamName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class BoardDetailActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_detail)

        val num = intent.getIntExtra("num",0)
        writeTitle.setText(intent.getStringExtra("title"))
        writeContent.setText(intent.getStringExtra("content"))
        val writter = intent.getStringExtra("writter")


        val spn = findViewById(R.id.writeType) as Spinner
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

        //글 수정 권한 확인
        auth = FirebaseAuth.getInstance()
        val nowUser = auth.currentUser!!.email.toString()

        // 권한이 없다면 숨기
        if(nowUser != writter){
            updateButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
        }

        deleteButton.setOnClickListener{
            deleteBoard(num)
        }

        updateButton.setOnClickListener{
            auth = FirebaseAuth.getInstance()
            val title = writeTitle.text.toString()
            val content = writeContent.text.toString()
            val email = auth.currentUser!!.email.toString()
            var item : BoardDto = BoardDto(num,email,title,content,type,nowTime())
            updateBoard(item)
        }
    }

    private fun deleteBoard(num : Int){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(BoardService::class.java)
        val call: Call<Int> =  service.deleteBoard(num)

        call.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"삭제 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.body()!=null){
                    Toast.makeText(applicationContext,"삭제 성공",Toast.LENGTH_SHORT).show()
                    startActivity<BoardActivity>()
                }
            }
        })
    }


    private fun updateBoard(item : BoardDto){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(BoardService::class.java)
        val call: Call<Int> =  service.updateBoard(item)
        call.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"수정 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.body()!=null){
                    Toast.makeText(applicationContext,"수정 성공",Toast.LENGTH_SHORT).show()
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
