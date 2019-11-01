package com.example.findmatch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findmatch.R
import kotlinx.android.synthetic.main.activity_notification.*
import org.jetbrains.anko.startActivity
import kotlinx.android.synthetic.main.activity_notification.top_notification
import kotlinx.android.synthetic.main.activity_footer.view.*

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        //푸터
        top_notification.home.setOnClickListener(){
            startActivity<MainActivity>()
        }
        top_notification.person.setOnClickListener(){
            startActivity<MypageActivity>()
        }

    }

}
