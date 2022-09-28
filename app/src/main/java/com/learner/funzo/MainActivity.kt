package com.learner.funzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val goToMenu  = findViewById<Button>(R.id.playBtn)
        goToMenu.setOnClickListener{
            val startIntent = Intent(this, MenuActivity::class.java)
            startActivity(startIntent)
            finish()
        }
    }

}