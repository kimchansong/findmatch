package com.example.findmatch.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.findmatch.Adapter.TeamAdapter
import com.example.findmatch.DTO.TeamMemberDto
import com.example.findmatch.R
import com.example.findmatch.Service.TeamService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_team_list.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamListActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    val teamList = mutableListOf<TeamMemberDto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_list)


        getMyTeam(this)
    }

    inner class ListListener : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            startActivity<TeamManageActivity>(
                "teamName" to teamList.get(position).teamName
            )
        }
    }

    // 내가 속한 팀 불러오기
    private fun getMyTeam(teamListActivity: TeamListActivity){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)

        auth = FirebaseAuth.getInstance()
        val email :String ?= auth.currentUser!!.email
        val call: Call<Array<TeamMemberDto>> = service.requestMyTeam(email!!)

        call.enqueue(object : Callback<Array<TeamMemberDto>> {
            override fun onFailure(call: Call<Array<TeamMemberDto>>, t: Throwable) {
                Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Array<TeamMemberDto>>, response: Response<Array<TeamMemberDto>>) {
                if(response.body()!=null){
                    val num = response.body()!!.size - 1

                    for (i in 0.. num){
                        teamList.add(TeamMemberDto(response.body()!!.get(i).teamName, response.body()!!.get(i).userId, response.body()!!.get(i).auth))
                    }

                    Log.d("TAG", teamList.toString())
                    val teamAdapter =
                        TeamAdapter(
                            teamListActivity,
                            teamList
                        )
                    teamListView.adapter = teamAdapter
                    var listener = ListListener()
                    teamListView.setOnItemClickListener(listener)
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