package com.example.findmatch.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.findmatch.DTO.UserDto
import com.example.findmatch.R
import com.example.findmatch.Service.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_mypage.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MypageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        selectUser()



        editButton.setOnClickListener {
            val userId = idText.text.toString()
            val userName = nameText.text.toString()
            val userAge = Integer.parseInt(ageText.text.toString())
            val userPhone = phoneText.text.toString()
            val userPoint = Integer.parseInt(pointText.text.toString())
            var user :UserDto = UserDto(userId,userName,userAge,userPhone,userPoint)

            updateUser(user)
        }
    }

    private fun selectUser(){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(UserService::class.java)

        auth = FirebaseAuth.getInstance()
        val email = auth.currentUser!!.email
        val call: Call<UserDto> = service.requestUserOk(email!!)


        call.enqueue(object : Callback<UserDto> {
            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                print("실패")
            }

            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if(response.body() != null){
                    idText.setText(response.body()!!.userId)
                    nameText.setText(response.body()!!.userName)
                    ageText.setText(""+response.body()!!.userAge)
                    phoneText.setText(response.body()!!.userPhone)
                    pointText.setText(""+response.body()!!.userPoint)

                }
            }
        })
    }

    private fun updateUser(item : UserDto){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(UserService::class.java)
        val call: Call<Int> =  service.updateUser(item)

        call.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"수정 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.body()==1){
                    Toast.makeText(applicationContext,"수정 성공",Toast.LENGTH_SHORT).show()
                    startActivity<MainActivity>()
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