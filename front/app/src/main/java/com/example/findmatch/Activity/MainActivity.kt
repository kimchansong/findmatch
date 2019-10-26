package com.example.findmatch.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findmatch.R
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.Service.TeamService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.frame
import kotlinx.android.synthetic.main.activity_main.pager
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //이미지 슬라이드
        pager.adapter = MainPagerAdapter()
        pager.offscreenPageLimit = 3

        timer(period = 3000) {
            runOnUiThread {
                if (pager.currentItem ==0) {
                    pager.currentItem = 1
                } else if(pager.currentItem ==1){
                    pager.currentItem = 2
                }
                else if(pager.currentItem ==2){
                    pager.currentItem = 3
                }
                else if(pager.currentItem ==3){
                    pager.currentItem = 0
                }
            }
        }
        // HTTP 통신
        setRetrofit()


        // 데이터 값 읽어오기
        loadData()

        // 로그인 페이지
        loginButton.setOnClickListener {
            startActivity<LoginActivity>()
        }
        MakeTeamBtn.setOnClickListener {
            startActivity<MakeTeamActivity>()
        }

        // 게시판 페이지로 이동
        boardButton.setOnClickListener {
            startActivity<BoardActivity>()
        }

        // 인텐트로 화면넘기기
        intentButton.setOnClickListener{

            // 보낼 데이터 저장
            saveData("hihi")

            val intent = Intent(this, TeamManageActivity::class.java)
            // 데이터 보내기
            intent.putExtra("hi", "hihi")
            startActivity(intent)
        }


    }
    //

    // 데이터 저장하기
    private fun saveData(data: String){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putString("DATA", data).apply()
    }

    // 데이터 불러오기
    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val data = pref.getString("DATA", "없음") // 저장된 값이 없을때 기본값 '없음'

        Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()
    }

    // HTTP 통신
    private fun setRetrofit(){

    val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    var service = retrofit.create(TeamService::class.java)

    val call:Call<TeamDto> = service.requestTeam()

    call.enqueue(object : Callback<TeamDto>{
        override fun onFailure(call: Call<TeamDto>, t: Throwable) {
            Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call<TeamDto>, response: Response<TeamDto>) {
            if(response.body()!=null){
                Toast.makeText(applicationContext,response.body()!!.teamName + " " + response.body()!!.teamInfo,Toast.LENGTH_SHORT).show()
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
