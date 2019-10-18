package com.example.findmatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}
