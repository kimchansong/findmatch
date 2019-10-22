package com.example.findmatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_team_manage.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamManageActivity : AppCompatActivity() {
    val teamMemberList = mutableListOf<TeamMemberDto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_manage)

        setRetrofit(this)
    }

    // HTTP 통신
    private fun setRetrofit(teamManageActivity: TeamManageActivity){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)

        val call: Call<Array<TeamMemberDto>> = service.requestTeamMember()

        call.enqueue(object : Callback<Array<TeamMemberDto>> {
            override fun onFailure(call: Call<Array<TeamMemberDto>>, t: Throwable) {
                Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Array<TeamMemberDto>>, response: Response<Array<TeamMemberDto>>) {
                if(response.body()!=null){
                    val num = response.body()!!.size - 1

                    for (i in 0.. num){
                        Log.d("?", response.body()!!.get(i).userId)
                        teamMemberList.add(response.body()!!.get(i))
                    }

                    Log.d("멤버 확인", teamMemberList.toString())
                    val teamMemberAdapter = TeamListAdapter(teamManageActivity, teamMemberList)
                    listTeamMember.adapter = teamMemberAdapter
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