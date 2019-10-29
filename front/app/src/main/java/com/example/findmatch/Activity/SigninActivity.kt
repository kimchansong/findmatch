package com.example.findmatch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.findmatch.DTO.UserDto
import com.example.findmatch.R
import com.example.findmatch.Service.UserService
import kotlinx.android.synthetic.main.activity_signin.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)



        val userId = intent.getStringExtra("userId").toString()
        val userName = intent.getStringExtra("userName").toString()
        idText.setText(userId)
        nameText.setText(userName)

        editButton.setOnClickListener{
            insertUser()
            startActivity<MainActivity>()
        }
    }

    private fun insertUser(){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(UserService::class.java)

        val userId = idText.text.toString()
        val userName = nameText.text.toString()
        val userAge = ageText.text.toString()
        val userPhone = phoneText.text.toString()
        val userPoint = 0

        val userInfo = UserDto(userId, userName, userAge, userPhone, userPoint)

        val call = service.insertUser(userInfo)

        call.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.body() == 0){
                    Toast.makeText(applicationContext,"회원가입완료",Toast.LENGTH_SHORT).show()
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
