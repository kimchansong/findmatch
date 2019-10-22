package com.example.findmatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        // 보낸 데이터 받기
        val data = intent.getStringExtra("hi")
        Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()

    }
}
