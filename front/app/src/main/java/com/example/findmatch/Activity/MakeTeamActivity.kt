package com.example.findmatch.Activity

import com.example.findmatch.Service.UserService
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.R
import com.example.findmatch.Service.TeamService
import kotlinx.android.synthetic.main.activity_make_team.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MakeTeamActivity : AppCompatActivity() {
    var checkDup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_team)

        var teamMember = HashSet<String>()

        teamNameTxt.addTextChangedListener(){
            checkDup = false
            dupCheckText.setText("팀 이름이 변경되었습니다")
            dupCheckText.setTextColor(Color.BLACK)
        }

        // 중복체크
        dupBtn.setOnClickListener {
            checkDuplication()
            Log.d("TAG", checkDup.toString())
        }

        // 멤버추가
        addMemberBtn.setOnClickListener{
            checkDuplicationTeamMember(teamMember)
        }

        // 팀생성
        teamMakeCommit.setOnClickListener {
            addTeam()
            addTeamMember(teamMember)
        }
    }

    private fun checkDuplication(){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val teamName = teamNameTxt.text.toString()
        var service = retrofit.create(TeamService::class.java)

        val call: Call<Int> = service.duplicationTeamName(teamName)
        call.enqueue(object : Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.body() == 1){
                    dupCheckText.setText("등록된 팀 이름이 있습니다. 다시 설정해주세요.")
                    dupCheckText.setTextColor(Color.RED)
                    checkDup = false
                }else{
                    dupCheckText.setText("사용 가능한 팀 명입니다.")
                    dupCheckText.setTextColor(Color.BLUE)
                    checkDup = true
                }
            }
        })
    }

    private fun checkDuplicationTeamMember(teamMember : HashSet<String>){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val memberEmail = memberEmailText.text.toString()
        var service = retrofit.create(UserService::class.java)
        val call : Call<Int> = service.requestUserOk(memberEmail)
        call.enqueue(object : Callback<Int>{

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                // 유저가 있을 경우
                if(response.body() == 1){
                    if(teamMember.contains(memberEmail)){
                        emailCheckText.setText("이미 등록된 맴버입니다.")
                        emailCheckText.setTextColor(Color.RED)
                    }else{
                        emailCheckText.setText("추가 성공!")
                        emailCheckText.setTextColor(Color.BLUE)
                        teamMember.add(memberEmail)
                        updateText(teamMember)
                    }
                }
                // 없을 경우
                else{
                    emailCheckText.setText("아이디가 없습니다.")
                    emailCheckText.setTextColor(Color.RED)
                }
            }
        })
    }

    private fun updateText(teamMember: HashSet<String>){
        var members = teamMember.toArray()
        var text = ""
        for(member in members){
            text += member
            text += " "
        }
        memberList.setText(text)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun addTeam(){
        if(checkDup){
            val teamName = teamNameTxt.text.toString()
            val teamInfo = teamInfoTxt.text.toString()
            val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build()

            var service = retrofit.create(TeamService::class.java)
            var team = TeamDto(teamName, teamInfo)
            val call: Call<Int> = service.addTeam(team)
            call.enqueue(object : Callback<Int>{
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    Toast.makeText(applicationContext, "성공",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun addTeamMember(teamMember: HashSet<String>){
        val teamName = teamNameTxt.text.toString()
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        var service = retrofit.create(TeamService::class.java)
        val teamMemberArray = teamMember.toArray()
        Log.d("TAG", teamMemberArray.toString())
        val call: Call<Int> = service.addTeamMember(teamMemberArray, teamName)

        call.enqueue(object : Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(applicationContext, "성공",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
