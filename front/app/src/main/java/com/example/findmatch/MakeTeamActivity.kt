package com.example.findmatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_make_team.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MakeTeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_team)

        DupBtn.setOnClickListener {
            checkDuplication()
        }
        addMemberBtn.setOnClickListener{
            addTeamMember()
        }
    }
    private fun checkDuplication(){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val teamName = TeamNameTxt.text.toString()

        var service = retrofit.create(TeamService::class.java)

        val call: Call<TeamDto> = service.duplicationTeamName(teamName)
        call.enqueue(object : Callback<TeamDto>{
            override fun onFailure(call: Call<TeamDto>, t: Throwable) {
                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TeamDto>, response: Response<TeamDto>) {
                if(response.body()!=null){

                    dupCheckText.setText("등록된 팀 이름이 있습니다. 다시 설정해주세요.")
                    dupCheckText.setTextColor(Color.RED)

                }else{
                    dupCheckText.setText("사용 가능한 팀 명입니다.")
                    dupCheckText.setTextColor(Color.BLUE)

                }
            }
        })
    }
    private fun addTeamMember(){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val MemberEmail = MemberEmailText.text.toString()
        var service = retrofit.create(UserService::class.java)
        val call : Call<UserDto> = service.requestUserOk(MemberEmail)
        call.enqueue(object : Callback<UserDto>{

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if(response.body()!=null){
                    EmailCheckText.setText("추가 성공!")
                    EmailCheckText.setTextColor(Color.BLUE)
                    addText(MemberEmail)
                }else{
                    EmailCheckText.setText("이메일이 없습니다.")
                    EmailCheckText.setTextColor(Color.RED)
                }
            }
        })
    }

    private fun addText(MemberEmail:String){
        val textView = TextView(this)
        textView.text = MemberEmail
        MemberLayout.addView(textView)
    }
    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }
}
