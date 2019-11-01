package com.example.findmatch.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.findmatch.R
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.Service.TeamService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_footer.view.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.pager
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
   private lateinit var googleSignInClient: GoogleSignInClient

   private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
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
        //푸터

        top.person.setOnClickListener(){
            startActivity<MypageActivity>()
        }

        top.notification.setOnClickListener(){
            startActivity<NotificationActivity>()
        }

        //팀관리 임시
        button121.setOnClickListener(){
            startActivity<TeamManageActivity>()
        }

        // HTTP 통신
        setRetrofit()


        // 데이터 값 읽어오기
        loadData()


        // 로그인 페이지
        loginButton.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            auth.signOut()
            googleSignInClient.signOut()
            startActivity<LoginActivity>()
        }

        // 팀 생성 페이지
        MakeTeamBtn.setOnClickListener {
            startActivity<MakeTeamActivity>()
        }

        // 게시판 페이지로 이동
        boardButton.setOnClickListener {
            startActivity<BoardActivity>()
        }

        // 내 팀 목록보기
        myTeamViewButton.setOnClickListener{

            // 보낼 데이터 저장
            // saveData("hihi")

            // 데이터 보내기
            // intent.putExtra("hi", "hihi")
            startActivity<TeamListActivity>()
        }


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
