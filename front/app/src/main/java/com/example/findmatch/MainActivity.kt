package com.example.findmatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        // 인텐트로 화면넘기기
        intentButton.setOnClickListener{

            // 보낼 데이터 저장
            saveData("hihi")

            val intent = Intent(this, ResultActivity::class.java)
            // 데이터 보내기
            intent.putExtra("hi", "hihi")
            startActivity(intent)
        }

        inputButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(nameEdit.text.isBlank()){
                    greetText.text = "이름이 없습니다."
                }else{
                    greetText.text = "${nameEdit.text}님 반갑습니다."
                }
            }
        })
    }

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
