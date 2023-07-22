package com.learner.funzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.widget.Button
import android.view.Menu
import android.view.MenuItem

import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        auth.signOut()
        // Implement any additional logout logic if required, such as clearing local session data.
        // After signing out, you may want to navigate the user to the login screen.
       // val intent = Intent(this, callingActivity::class.java)
       // startActivity(intent)
        // For simplicity, let's just finish the current activity.

        finish()
    }
}
//class MainActivity : AppCompatActivity() {
//  override fun onCreate(savedInstanceState: Bundle?) {
//       super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val goToMenu  = findViewById<Button>(R.id.playBtn)
//        goToMenu.setOnClickListener{
//            val startIntent = Intent(this, MenuActivity::class.java)
//            startActivity(startIntent)
//            finish()
//        }
//    }
//
//}