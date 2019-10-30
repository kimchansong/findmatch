package com.example.findmatch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.findmatch.*
import com.example.findmatch.Adapter.TeamJoinListAdapter
import com.example.findmatch.Adapter.TeamListAdapter
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.DTO.TeamJoinDto
import com.example.findmatch.DTO.TeamMemberDto
import com.example.findmatch.Service.TeamService
import kotlinx.android.synthetic.main.activity_team_manage.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamManageActivity : AppCompatActivity() {
    var team : TeamDto? = null
    val teamMemberList = mutableListOf<TeamMemberDto>()
    val teamJoinList = mutableListOf<TeamJoinDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_manage)
        val myTeamName = intent.getStringExtra("teamName").toString()

        getTeam(myTeamName)
        getTeamMember(this, myTeamName)
        getTeamJoinRequest(this, myTeamName)

        teamDeleteBtn.setOnClickListener{
            deleteTeam()
            deleteTeamMember(myTeamName)
        }
    }

    // 팀 정보 불러오기
    private fun getTeam(myTeamName:String) {
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)

        val call: Call<TeamDto> = service.requestTeam(myTeamName)

        call.enqueue(object : Callback<TeamDto> {
            override fun onFailure(call: Call<TeamDto>, t: Throwable) {
                Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<TeamDto>,
                response: Response<TeamDto>
            ) {
                if (response.body() != null) {
                    team = response.body()!!

                    teamName.text = team!!.teamName
                    teamInfo.text = team!!.teamInfo
                }
            }
        })
    }

    // 멤버 불러오기
    private fun getTeamMember(teamManageActivity: TeamManageActivity, myTeamName:String){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)

        val call: Call<Array<TeamMemberDto>> = service.requestTeamMember(myTeamName)

        call.enqueue(object : Callback<Array<TeamMemberDto>> {
            override fun onFailure(call: Call<Array<TeamMemberDto>>, t: Throwable) {
                Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Array<TeamMemberDto>>, response: Response<Array<TeamMemberDto>>) {
                if(response.body()!=null){
                    val num = response.body()!!.size - 1

                    for (i in 0.. num){
                        teamMemberList.add(response.body()!!.get(i))
                    }

                    val teamMemberAdapter =
                        TeamListAdapter(
                            teamManageActivity,
                            teamMemberList
                        )
                    listTeamMember.adapter = teamMemberAdapter
                }
            }
        })
    }

    // 팀 가입 요청 불러오기
    private fun getTeamJoinRequest(teamManageActivity: TeamManageActivity, myTeamName: String){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)

        val call: Call<Array<TeamJoinDto>> = service.requestTeamJoin(myTeamName)

        call.enqueue(object : Callback<Array<TeamJoinDto>> {
            override fun onFailure(call: Call<Array<TeamJoinDto>>, t: Throwable) {
                Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Array<TeamJoinDto>>, response: Response<Array<TeamJoinDto>>) {
                if(response.body()!=null){
                    val num = response.body()!!.size - 1

                    for (i in 0.. num){
                        teamJoinList.add(response.body()!!.get(i))
                    }

                    val teamJoinAdapter =
                        TeamJoinListAdapter(
                            teamManageActivity,
                            teamJoinList
                        )
                    listTeamJoinRequest.adapter = teamJoinAdapter
                }
            }
        })
    }

    // 팀 삭제
    private fun deleteTeam(){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)
        val call: Call<Int> = service.requestTeamDelete(team!!)

        call.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.body() == 1){
                    Toast.makeText(applicationContext, "팀 삭제 성공", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "팀 삭제 실패", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // 팀원 삭제
    private fun deleteTeamMember(myTeamName: String){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)
        val call: Call<Int> = service.requestTeamMemberDelete(myTeamName)

        call.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.body() == 1){
                    // 성공
                }else{
                    // 실패
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