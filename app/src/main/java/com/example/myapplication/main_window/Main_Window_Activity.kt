package com.example.myapplication.main_window

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
import com.example.myapplication.registration_user.SingActivity

class Main_Window_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)
        val BtnLogOut: Button = findViewById(R.id.btnLogOut)

        BtnLogOut.setOnClickListener{
            val intent = Intent(this,SingActivity::class.java)
            startActivity(intent)
        }
    }

}