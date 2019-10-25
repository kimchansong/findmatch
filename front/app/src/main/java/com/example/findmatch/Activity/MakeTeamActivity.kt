package com.example.findmatch.Activity

import com.example.findmatch.DTO.UserDto
import com.example.findmatch.Service.UserService

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.example.findmatch.R
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.Service.TeamService
import kotlinx.android.synthetic.main.activity_make_team.*
import kotlinx.android.synthetic.main.activity_team_manage.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.editText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.text.TextWatcher as TextWatcher

class MakeTeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_team)
        var checkDup = false

        var addMember = false
        var teamMember = HashSet<String>()
        DupBtn.setOnClickListener {
            checkDup = checkDuplication()
        }
        TeamNameTxt.addTextChangedListener(){
            checkDup = false
            dupCheckText.setText("팀 이름이 변경되었습니다")
        }
        addMemberBtn.setOnClickListener{
            addTeamMember(teamMember)

        }
        TeamMakeCommit.setOnClickListener {
            
        }
    }

    private fun checkDuplication():Boolean{
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val teamName = TeamNameTxt.text.toString()

        var service = retrofit.create(TeamService::class.java)
        var check = false
        val call: Call<TeamDto> = service.duplicationTeamName(teamName)
        call.enqueue(object : Callback<TeamDto>{
            override fun onFailure(call: Call<TeamDto>, t: Throwable) {
                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<TeamDto>, response: Response<TeamDto>) {
                if(response.body()!=null){
                    dupCheckText.setText("등록된 팀 이름이 있습니다. 다시 설정해주세요.")
                    dupCheckText.setTextColor(Color.RED)
                    check = false
                }else{
                    dupCheckText.setText("사용 가능한 팀 명입니다.")
                    dupCheckText.setTextColor(Color.BLUE)
                    check = true
                }
            }
        })
        return check
    }
    private fun addTeamMember(teamMember : HashSet<String>){
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
                    if(teamMember.contains(MemberEmail)){
                        EmailCheckText.setText("이미 등록된 맴버입니다.")
                        EmailCheckText.setTextColor(Color.RED)
                    }else{
                        EmailCheckText.setText("추가 성공!")
                        EmailCheckText.setTextColor(Color.BLUE)
                        teamMember.add(MemberEmail)
                        updateText(teamMember)
                    }

                }else{
                    EmailCheckText.setText("이메일이 없습니다.")
                    EmailCheckText.setTextColor(Color.RED)
                }
            }
        })
    }

    private fun updateText(teamMember: HashSet<String>){
        var member = teamMember.toArray()
        var text = ""
        for(i in member){
            text+=i
            text+=" "
        }
        MemberList.setText(text)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

//    private fun addTeam(checkDup){
//
//    }
}
