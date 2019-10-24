package com.example.findmatch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.findmatch.R


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        // 보낸 데이터 받기
        val data = intent.getStringExtra("hi")
        Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()

    }
}
