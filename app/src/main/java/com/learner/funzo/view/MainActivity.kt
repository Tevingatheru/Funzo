package com.learner.funzo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.learner.funzo.R
import com.learner.funzo.viewModel.MainActivityViewModel
import com.learner.funzo.viewModel.nav.NavigationHandler

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        // Create a navigation handler instance
        val navigationHandler = object : NavigationHandler {
            override fun navigateToSubjectList() {
                val startIntent = Intent(this@MainActivity, SubjectListActivity::class.java)
                startActivity(startIntent)
                finish()
            }
        }

        findViewById<Button>(R.id.playBtn).setOnClickListener {
            viewModel.onPlayButtonClicked(navigationHandler)
        }
    }


}
