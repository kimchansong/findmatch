package com.example.findmatch.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.findmatch.R
import com.example.findmatch.DTO.TeamDto
import com.example.findmatch.Service.TeamService
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
   // private val KEY_POSITION = "keyPosition"

   // private var navPosition: BottomNavigationPosition = BottomNavigationPosition.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  restoreSaveInstanceState(savedInstanceState)
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
        //바텀

/*
        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            // This is required in Support Library 27 or lower:
            // bottomNavigation.disableShiftMode()
            active(navPosition.position) // Extension function
            setOnNavigationItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                switchFragment(navPosition)
            }
        }

        initFragment(savedInstanceState)
*/
        // HTTP 통신
        setRetrofit()


        // 데이터 값 읽어오기
        loadData()

        //마이 페이지
 /*       mypagebutton.setOnClickListener {

            startActivity<MypageActivity>()
        }
*/
        // 로그인 페이지
        loginButton.setOnClickListener {
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

        connection.setOnClickListener {
            startActivity<MypageActivity>()
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
/*
    //바텀
    override fun onSaveInstanceState(outState: Bundle?) {
        // Store the current navigation position.
        outState?.putInt(KEY_POSITION, navPosition.id)
        super.onSaveInstanceState(outState)
    }
//바텀
    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        // Restore the current navigation position.
        savedInstanceState?.getInt(KEY_POSITION, BottomNavigationPosition.HOME.id)?.also {
            navPosition = findNavigationPositionById(it)
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: switchFragment(BottomNavigationPosition.HOME)
    }

    /**
     * Immediately execute transactions with FragmentManager#executePendingTransactions.
     */
    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        return findFragment(navPosition).let {
            if (it.isAdded) return false
            supportFragmentManager.detach() // Extension function
            supportFragmentManager.attach(it, navPosition.getTag()) // Extension function
            supportFragmentManager.executePendingTransactions()
        }
    }

    private fun findFragment(position: BottomNavigationPosition): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag()) ?: position.createFragment()
    }
*/

}
