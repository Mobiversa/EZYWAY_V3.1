package com.mobi.ezyway_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        card_btn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        fpx_btn.setOnClickListener {
            startActivity(Intent(this,FPXActivity::class.java))
        }

        boost_btn.setOnClickListener {
            startActivity(Intent(this,BoostActivity::class.java))
        }
    }
}