package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MYLogMAct","onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MYLogMAct","onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("MYLogMAct","onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("MYLogMAct","onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("MYLogMAct","onStop")

    }
}