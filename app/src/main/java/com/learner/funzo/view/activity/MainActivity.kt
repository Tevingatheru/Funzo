package com.learner.funzo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.viewModels
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.R
import com.learner.funzo.viewModel.MainActivityViewModel
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainActivity = this
        findViewById<Button>(R.id.playBtn).setOnClickListener {
            runBlocking {
                viewModel.onPlayButtonClicked(applicationContext = mainActivity)

            }
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemLogout -> {
                FirebaseUtil.logout(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
