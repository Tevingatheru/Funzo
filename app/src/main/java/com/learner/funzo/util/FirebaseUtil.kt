package com.learner.funzo.util

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.learner.funzo.viewModel.nav.NavigationHandler

class FirebaseUtil {

    companion object {
        private var auth = FirebaseAuth.getInstance()

        fun logout(applicationContext: Context) {
            auth.signOut()
            NavigationHandler.navigateToLoginActivity(applicationContext)
        }

        fun isUserLoggedIn(applicationContext: Context) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                NavigationHandler.navigateToMainActivity(applicationContext = applicationContext)
            }
        }

        fun register(applicationContext: Context, email: String, password: String) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext, "Account created.",
                            Toast.LENGTH_SHORT
                        ).show()

                        NavigationHandler.navigateToLoginActivity(applicationContext = applicationContext)
                    } else {
                        Toast.makeText(
                            applicationContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        fun login(applicationContext: Context, email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext, "Login Successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        NavigationHandler.navigateToMainActivity(applicationContext = applicationContext)
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
