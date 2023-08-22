package com.learner.funzo.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.learner.funzo.util.FirebaseUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.learner.funzo.R
import com.learner.funzo.viewModel.SubjectListActivityViewModel
import java.lang.Exception

class SubjectListActivity : AppCompatActivity() {
    private val viewModel: SubjectListActivityViewModel by viewModels()

    companion object {
        private const val TAG = "SubjectListActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)
        setSubjectListView()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onResume() {
        try {
            super.onResume()
            Log.i(TAG, "onResume")
        } catch (e: Exception) {
            Log.w(TAG, "Logged error")
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                FirebaseUtil.logout(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setSubjectListView() {
        viewModel.setSubjectListView(
            applicationContext = this,
            subjectListView = findViewById<ListView>(R.id.listView)
        )
    }
}
