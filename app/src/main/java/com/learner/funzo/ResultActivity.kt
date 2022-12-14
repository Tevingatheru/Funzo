package com.learner.funzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() , OnClickListener {

    private var score: ScoreConstants? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        score =  intent.getParcelableExtra<ScoreConstants>("score")
        setView()
    }

    private fun setView() {
        val nextButton = findViewById<Button>(R.id.playButton)
        nextButton.setOnClickListener(this)
        val scoreText = findViewById<TextView>(R.id.score)
        scoreText.text = String.format("%d / %d", score!!.getScore(), score!!.getTotalNumberOfQuestions())
        val completionText = findViewById<TextView>(R.id.completionMessage)
        if (score!!.passed()) {
            completionText.text = "You Passed"
        }
        else {
            completionText.text = "You Failed"
        }
    }

    override fun onClick(view: View?) {
        Log.i("Results Activity",  "id: + ${view?.id.toString()} ")
        when(view?.id) {
            R.id.playButton -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }


}