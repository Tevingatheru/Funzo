package com.learner.funzo.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.learner.funzo.view.LoginActivity
import com.learner.funzo.view.MainActivity

class FirebaseUtil {

    companion object {
        private var auth = FirebaseAuth.getInstance()
        var mAuth = Firebase.auth

        fun logout(applicationContext: Context) {
            auth.signOut()
            val startIntent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(applicationContext ,startIntent, null)
        }

        fun isUserLoggedIn(applicationContext: Context) {
            val currentUser = mAuth.currentUser
            if (currentUser != null) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                applicationContext.startActivity(intent)
            }
        }

        fun register(applicationContext: Context, email: String, password: String) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext, "Account created.",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        applicationContext.startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        fun login(applicationContext: Context, email: String, password: String) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    Log.i(LoginActivity.TAG, "")
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext, "Login Successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        applicationContext.startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
